package net.bj.talker.factory.presenter.contact;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.model.card.UserCard;
import net.bj.talker.factory.model.db.AppDatabase;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.User_Table;
import net.bj.talker.factory.persistence.Account;
import net.bj.talker.factory.utils.DiffUiDataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人的Presenter实现
 * Created by Neko-T4 on 2017/12/25.
 */

public class ContactPresenter extends BasePresenter<ContactContract.View>
        implements ContactContract.Presenter{
    public ContactPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        //加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<User>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<User> tResult) {

                        getView().getRecyclerAdapter().replace(tResult);
                        getView().onAdapterDataChanged();
                    }
                })
                .execute();

        //加载网络数据
        UserHelper.refreshContacts(new DataSource.Callback<List<UserCard>>() {
            @Override
            public void onDataNotAvailable(int strRes) {
                //网络失败，因为本地有数据，不管错误
            }

            @Override
            public void onDataLoaded(final List<UserCard> userCards) {
                //转换为User
                final List<User> users = new ArrayList<>();
                for (UserCard userCard : userCards) {
                    users.add(userCard.build());
                }

                //放入事务中，保存到数据库
                DatabaseDefinition df = FlowManager.getDatabase(AppDatabase.class);
                df.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        FlowManager.getModelAdapter(User.class).saveAll(users);
                    }
                }).build().execute();

                //网络的数据往往是新的，我们需要直接刷新到界面
                List<User> old = getView().getRecyclerAdapter().getItems();
                //会导致数据顺序全部为新的数据集合
                //getView().getRecyclerAdapter().replace(users);
                diff(old,users);
            }
        });
    }


    private void diff(List<User> oldList, List<User> newList){
        //进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList,newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //在对比完成后进行数据的赋值
        getView().getRecyclerAdapter().replace(newList);

        //尝试刷新界面
        result.dispatchUpdatesTo(getView().getRecyclerAdapter());
        getView().onAdapterDataChanged();
    }

}
