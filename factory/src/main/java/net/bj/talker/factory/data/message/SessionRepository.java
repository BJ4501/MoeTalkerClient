package net.bj.talker.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.data.BaseDbRepository;
import net.bj.talker.factory.model.db.Session;
import net.bj.talker.factory.model.db.Session_Table;

import java.util.Collections;
import java.util.List;

/**
 * 最近聊天列表仓库，是对SessionDataSource的实现
 * Created by Neko-T4 on 2018/1/11.
 */

public class SessionRepository extends BaseDbRepository<Session> implements SessionDataSource{

    @Override
    public void load(SucceedCallback<List<Session>> callback) {
        super.load(callback);
        //数据库查询
        SQLite.select()
                .from(Session.class)
                .orderBy(Session_Table.modifyAt,false) //倒序
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();

    }

    @Override
    protected boolean isRequired(Session session) {
        //所有的会话都需要，不需要过滤
        return true;
    }

    @Override
    protected void insert(Session session) {
        //复写方法，让新的数据加到头部
        dataList.addFirst(session);
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Session> tResult) {
        // 复写数据库回来的方法，进行一次反转
        // 不进行复写会导致，显示的顺序是反方向，虽然查询出来的顺序是
        // 10 9 8 但是每一条都会依次插入 所以插入后显示的顺序是 8 9 10
        // 所以复写此方法，在插入之前，先进行反转，使其变为 8 9 10
        // 这样插入后显示的顺序就是 10 9 8 了
        Collections.reverse(tResult);

        super.onListQueryResult(transaction, tResult);
    }
}
