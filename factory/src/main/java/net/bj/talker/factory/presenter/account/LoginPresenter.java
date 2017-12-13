package net.bj.talker.factory.presenter.account;

import net.bj.moetalker.factory.presenter.BasePresenter;

/**
 * 登录的逻辑实现
 * Created by Neko-T4 on 2017/12/13.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }
}
