package net.bj.moetalker.push.frags.account;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.frags.media.GalleryFragment;

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
        new GalleryFragment().setmListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {

            }
        }).show(getChildFragmentManager(),GalleryFragment.class.getName());
        //show的时候建议使用getChildFragmentManager
        //tag GalleryFragment class name
    }

}
