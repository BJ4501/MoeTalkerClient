package net.bj.talker.factory.data.message;

import net.bj.talker.factory.model.card.MessageCard;

/**
 * 消息中心，进行消息Card的消费
 * Created by Neko-T4 on 2017/12/28.
 */

public interface MessageCenter {

    void dispatch(MessageCard... cards);
}
