package net.bj.talker.factory.presenter.contact;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.User;

/**
 * Created by Neko-T4 on 2017/12/26.
 */

public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter{
        //获取用户信息
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter>{
        String getUserId();
        //加载数据完成
        void onLoadDone(User user);
        //是否发起聊天
        void allowSayHello(boolean isAllow);
        //设置关注状态
        void setFollowStatus(boolean isFollow);
    }

}
