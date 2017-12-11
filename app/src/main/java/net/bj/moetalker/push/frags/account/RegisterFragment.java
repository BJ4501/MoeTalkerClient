package net.bj.moetalker.push.frags.account;


import android.content.Context;

import net.bj.moetalker.common.app.PresenterFragment;
import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.moetalker.push.R;
import net.bj.talker.factory.presenter.account.RegisterContract;
import net.bj.talker.factory.presenter.account.RegisterPresenter;

/**
 * 注册界面
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {
    private AccountTrigger mAccountTrigger;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void registerSuccess() {

    }
}
