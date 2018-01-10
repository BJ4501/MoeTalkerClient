package net.bj.talker.factory.data.message;

import net.bj.moetalker.factory.data.DbDataSource;
import net.bj.talker.factory.model.db.Message;

/**
 * 消息的数据源定义，他的实现是：MessageRepository
 * 关注的对象是Message表
 * Created by Neko-T4 on 2018/1/10.
 */

public interface MessageDataSource extends DbDataSource<Message> {

}
