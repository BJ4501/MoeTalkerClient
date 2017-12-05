package net.bj.moetalker.push.frags.account;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更新信息的界面
 */
public class UpdateInfoFragment extends Fragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        
    }

}
