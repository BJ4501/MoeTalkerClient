package net.bj.moetalker.push.frags.message;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.PersonalActivity;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.presenter.message.ChatContract;
import net.bj.talker.factory.presenter.message.ChatUserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户聊天界面
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    private MenuItem mUserInfoMenuItem;


    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        Toolbar toolbar = mToolbar;
        toolbar.inflateMenu(R.menu.chat_user);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_person){
                    onPortraitClick();
                }
                return false;
            }
        });
        //拿到菜单Icon
        mUserInfoMenuItem = toolbar.getMenu().findItem(R.id.action_person);
    }

    //头像随状态栏高度缩放方法(高度的综合运算，透明头像与icon)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mPortrait;
        MenuItem menuItem = mUserInfoMenuItem;
        if(view == null || menuItem == null)
            return;

        if (verticalOffset == 0){
            //完全展开--头像可见
            view.setVisibility(View.VISIBLE);
            //缩放效果
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
            //隐藏菜单
            menuItem.setVisible(false);
            menuItem.getIcon().setAlpha(0);
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
                //显示菜单
                menuItem.setVisible(true);
                menuItem.getIcon().setAlpha(255);
            }else {
                //处于中间状态时
                //计算进度值(1->0)
                float progress = 1 - verticalOffset/(float)totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
                //菜单渐变 和头像计算相反
                menuItem.setVisible(true);
                menuItem.getIcon().setAlpha(255-(int)(255*progress));
            }
        }
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        PersonalActivity.show(getContext(),mReceiverId);
    }


    @Override
    protected ChatContract.Presenter initPresenter() {
        //初始化Presenter
        return new ChatUserPresenter(this, mReceiverId);
    }

    @Override
    public void onInit(User user) {
        //对和你聊天的朋友的信息初始化操作

    }
}
