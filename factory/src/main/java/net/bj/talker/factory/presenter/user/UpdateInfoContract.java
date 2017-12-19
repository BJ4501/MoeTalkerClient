package net.bj.talker.factory.presenter.user;

import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * 更新用户信息的基本的契约
 * Created by Neko-T4 on 2017/12/19.
 */

public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter{
        //更新
        void update(String photoFilePath, String desc, Boolean isMan);
    }

    interface View extends BaseContract.View<Presenter>{
        //回调成功
        void updateSucceed();
    }
}
