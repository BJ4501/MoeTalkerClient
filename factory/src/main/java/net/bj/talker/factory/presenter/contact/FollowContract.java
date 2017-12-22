package net.bj.talker.factory.presenter.contact;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.card.UserCard;

/**
 * 关注的接口定义
 * Created by Neko-T4 on 2017/12/22.
 */

public interface FollowContract {
    //任务调度者
    interface Presenter extends BaseContract.Presenter{
        //关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter>{
        //成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
