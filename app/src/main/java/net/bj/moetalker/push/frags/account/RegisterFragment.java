package net.bj.moetalker.push.frags.account;




import android.content.Context;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.push.R;

/**
 * 注册界面
 */
public class RegisterFragment extends Fragment {
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
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }
}
