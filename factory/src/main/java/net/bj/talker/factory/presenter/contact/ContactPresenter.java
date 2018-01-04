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

import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.moetalker.factory.presenter.BaseRecyclerPresenter;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.data.user.ContractDataSource;
import net.bj.talker.factory.data.user.ContractRepository;
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

public class ContactPresenter extends BaseRecyclerPresenter<User,ContactContract.View>
        implements ContactContract.Presenter,DataSource.SucceedCallback<List<User>>{

    private ContractDataSource mSource;
    public ContactPresenter(ContactContract.View view) {
        super(view);
        mSource = new ContractRepository();
    }

    @Override
    public void start() {
        super.start();

        //进行本地的数据加载，并添加监听
        mSource.load(this);

        //加载网络数据
        UserHelper.refreshContacts();
    }


    /*//转换为User
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
    diff(old,users);*/

    //运行到这里是子线程
    @Override
    public void onDataLoaded(List<User> users) {
        //无论如何操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        //进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old,users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //调用基类方法进行界面刷新
        refreshData(result,users);
    }

    @Override
    public void destory() {
        super.destory();
        //当界面销毁的时候，我们应该把数据监听进行销毁
        mSource.dispose();
    }
}
