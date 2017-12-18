package net.bj.talker.factory.presenter.account;

import android.text.TextUtils;

import net.bj.moetalker.common.Common;
import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.R;
import net.bj.talker.factory.data.helper.AccountHelper;
import net.bj.talker.factory.model.api.account.RegisterModel;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.persistence.Account;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //调用开始方法，在start中默认启动了Loading
        start();

        //得到View接口
        RegisterContract.View view = getView();

        //校验
        if(!checkMobile(phone)){
            //提示手机号不合法
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        }else if (name.length() < 2){
            //姓名需要大于两位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        }else if (password.length() < 6){
            //密码要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        }else {
            //进行网络请求
            //构造Model，进行请求调用
            RegisterModel model = new RegisterModel(phone,password,name, Account.getPushId());
            //进行网络请求，并设置回送接口为自己
            AccountHelper.register(model,this);
        }

    }

    /**
     * 检查手机号码是否合法
     * @param phone 手机号码
     * @return 合法True
     */
    @Override
    public boolean checkMobile(String phone) {
        //手机号码不为空，并且满足相应格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE,phone);
    }

    @Override
    public void onDataLoaded(User user) {
        //当网络强求成功，注册完毕回送一个用户信息
        //告知界面。注册成功
        final RegisterContract.View view = getView();
        if (view == null)
            return;
        //此时是从网路回送回来的，并不保证处于主线程状态
        //进行线程切换--强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面注册成功
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        //当网络强求告知，注册失败
        final RegisterContract.View view = getView();
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
