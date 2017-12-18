package net.bj.talker.factory.presenter.account;

import android.text.TextUtils;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.R;
import net.bj.talker.factory.data.helper.AccountHelper;
import net.bj.talker.factory.model.api.account.LoginModel;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.persistence.Account;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 登录的逻辑实现
 * Created by Neko-T4 on 2017/12/13.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter, DataSource.Callback<User> {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();
        final LoginContract.View view = getView();
        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){
            view.showError(R.string.data_account_login_invalid_parameter);
        }else {
            //尝试传递PushId，可能有可能没有
            LoginModel model = new LoginModel(phone,password,Account.getPushId());
            AccountHelper.login(model,this);
        }
    }

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        //进行线程切换--强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        //当网络强求告知，注册失败
        final LoginContract.View view = getView();
        if (view == null)
            return;
        //此时是从网路回送回来的，并不保证处于主线程状态
        //进行线程切换--强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面注册失败，显示错误
                view.showError(strRes);
            }
        });
    }
}
