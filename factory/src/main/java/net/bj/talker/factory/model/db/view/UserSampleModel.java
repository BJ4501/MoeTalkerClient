package net.bj.talker.factory.model.db.view;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

import net.bj.moetalker.factory.model.Author;
import net.bj.talker.factory.model.db.AppDatabase;

/**
 * 用户基础信息的Model，可以和数据库进行查询
 * Created by Neko-T4 on 2018/1/14.
 */
@QueryModel(database = AppDatabase.class)
public class UserSampleModel implements Author{

    @Column
    public String id;
    @Column
    public String name;
    @Column
    public String portrait;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPortrait() {
        return portrait;
    }

    @Override
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
