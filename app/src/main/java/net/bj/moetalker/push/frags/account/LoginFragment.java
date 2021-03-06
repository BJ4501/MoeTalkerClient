package net.bj.moetalker.push.frags.account;



import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.app.PresenterFragment;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.MainActivity;
import net.bj.talker.factory.presenter.account.LoginContract;
import net.bj.talker.factory.presenter.account.LoginPresenter;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
        implements LoginContract.View {
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        //调用P层进行注册
        mPresenter.login(phone,password);
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegistClick(){
        //让Account进行界面切换
        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //当需要显示错误的时候触发，一定是结束了
        //停止Loading
        mLoading.stop();
        //让控件可以输入
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);
        //提交按钮可以继续点击
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //正在进行时，界面不可操作
        //开始Loading
        mLoading.start();
        //让控件不可以输入
        mPhone.setEnabled(false);
        mPassword.setEnabled(false);
        //提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }

    @Override
    public void loginSuccess() {
        //跳转到MainActivity界面
        MainActivity.show(this.getContext());
        //关闭当前界面
        getActivity().finish();
    }
}
