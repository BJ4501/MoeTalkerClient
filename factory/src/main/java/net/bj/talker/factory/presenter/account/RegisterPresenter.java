package net.bj.talker.factory.presenter.account;

import android.text.TextUtils;

import net.bj.moetalker.common.Common;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.data.helper.AccountHelper;
import net.bj.talker.factory.model.api.RegisterModel;

import java.util.regex.Pattern;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //调用开始方法，在start中默认启动了Loading
        start();

        if(!checkMobile(phone)){
            //提示手机号不合法

        }else if (name.length() < 2){
            //姓名需要大于两位
        }else if (password.length() < 6){
            //密码要大于6位
        }else {
            //进行网络请求

            //构造Model，进行请求调用
            RegisterModel model = new RegisterModel(phone,password,name);
            AccountHelper.register(model);
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
}
