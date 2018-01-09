package net.bj.moetalker.push.frags.message;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.push.R;

import butterknife.BindView;

/**
 * 用户聊天界面
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;


    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    //头像随状态栏高度缩放方法
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mPortrait;

        if (verticalOffset == 0){
            //完全展开--头像可见
            view.setVisibility(View.VISIBLE);
            //缩放效果
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
        }else {
            //abs 运算
            verticalOffset = Math.abs(verticalOffset);
            //最大滚动高度
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset >= totalScrollRange){
                //头像不可见
                view.setVisibility(View.INVISIBLE);
                //缩放效果
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);
            }else {
                //处于中间状态时
                //计算进度值(1->0)
                float progress = 1 - verticalOffset/(float)totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
            }
        }
    }
}
