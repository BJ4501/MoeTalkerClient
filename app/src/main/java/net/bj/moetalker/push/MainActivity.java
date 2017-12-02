package net.bj.moetalker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.bj.moetalker.common.Common;
import net.bj.moetalker.common.app.Activity;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.push.frags.main.ActiveFragment;
import net.bj.moetalker.push.frags.main.GroupFragment;
import net.bj.moetalker.push.helper.NavHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.appbar)
    View mLayAppbar;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_title)
    TextView mTitle;
    @BindView(R.id.lay_container)
    FrameLayout mContainer;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private NavHelper mNavHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化底部辅助工具类
        mNavHelper = new NavHelper();

        //添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);


        Glide.with(this).load(R.drawable.bg_src_morning).centerCrop().into(new ViewTarget<View,GlideDrawable>(mLayAppbar) {
            //当资源准备好的时候，将图片设置为背景
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                //这里的this是ViewTarget，不是MainActivity
                this.view.setBackground(resource.getCurrent());
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick(){

    }

    @OnClick(R.id.btn_action)
    void onActionClick(){

    }


    /**
     * 当我们的底部导航被点击的时候触发
     * @param item MenuItem
     * @return True 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //转接事件流到工具类中
        return mNavHelper.performClickMenu(item.getItemId());
    }
}
