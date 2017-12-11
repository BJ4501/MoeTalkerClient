package net.bj.talker.factory.presenter.account;

import net.bj.moetalker.factory.presenter.BasePresenter;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {

    }

    @Override
    public boolean checkMobile(String phone) {
        return false;
    }
}
