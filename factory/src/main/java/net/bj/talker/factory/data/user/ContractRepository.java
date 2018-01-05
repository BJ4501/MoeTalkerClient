package net.bj.talker.factory.data.user;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.data.BaseDbRepository;
import net.bj.talker.factory.data.helper.DbHelper;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.User_Table;
import net.bj.talker.factory.persistence.Account;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.util.Set;

/**
 * 联系人仓库
 * Created by Neko-T4 on 2018/1/2.
 */

public class ContractRepository extends BaseDbRepository<User> implements ContractDataSource{

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);


        //加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    /**
     * 检查一个User是我需要关注的数据
     * @param user User
     * @return True：是我关注的数据
     */
    @Override
    protected boolean isRequired(User user) {
        return user.isFollow()&& !user.getId().equals(Account.getUserId());
    }

}
