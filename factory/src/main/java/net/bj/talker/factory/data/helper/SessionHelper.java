package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.bj.talker.factory.model.db.Session;
import net.bj.talker.factory.model.db.Session_Table;

/**
 * 会话辅助工具类
 * Created by Neko-T4 on 2017/12/29.
 */

public class SessionHelper {
    //从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
