package net.bj.talker.factory.data.user;

import net.bj.talker.factory.model.card.UserCard;

/**
 * 用户中心的基本定义
 * Created by Neko-T4 on 2017/12/28.
 */

public interface UserCenter {
    //分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(UserCard... cards);

}
