package net.bj.talker.factory.presenter.account;

import android.support.annotation.StringRes;

import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public interface RegisterContract {

    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        //发起一个注册
        void register(String phone,String name,String password);

        //检查手机号是否正确
        boolean checkMobile(String phone);
    }

}
