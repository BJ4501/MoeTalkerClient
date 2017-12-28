package net.bj.talker.factory.data.group;

import net.bj.talker.factory.model.card.GroupCard;
import net.bj.talker.factory.model.card.GroupMemberCard;

/**
 * 群中心的接口定义
 * Created by Neko-T4 on 2017/12/28.
 */

public interface GroupCenter {
    //群卡片的处理
    void dispatch(GroupCard... cards);
    //群成员的处理
    void dispatch(GroupMemberCard... cards);
}
