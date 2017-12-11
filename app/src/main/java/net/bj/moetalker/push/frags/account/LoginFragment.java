package net.bj.moetalker.push.frags.account;



import android.content.Context;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.push.R;

/**
 * 登录界面
 */
public class LoginFragment extends Fragment {
    private AccountTrigger mAccountTrigger;

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
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    public void onResume() {
        super.onResume();
        //进行一次切换，默认切换为注册界面
        mAccountTrigger.triggerView();
    }
}
