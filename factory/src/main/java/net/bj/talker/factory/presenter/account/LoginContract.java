package net.bj.talker.factory.presenter.account;

import android.support.annotation.StringRes;

import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public interface LoginContract {

    interface View extends BaseContract.View<Presenter>{
        //登录成功
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        //发起一个登录
        void login(String phone, String password);
    }

}
