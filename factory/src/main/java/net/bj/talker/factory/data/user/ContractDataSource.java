package net.bj.talker.factory.data.user;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.model.db.User;

import java.util.List;

/**
 * 联系人数据源
 * Created by Neko-T4 on 2018/1/2.
 */

public interface ContractDataSource {

    /**
     * 对数据进行加载的职责
     * @param callback 加载成功后返回的Callback
     */
    void load(DataSource.SucceedCallback<List<User>> callback);

    /**
     * 对数据进行销毁
     */
    void dispose();


}
