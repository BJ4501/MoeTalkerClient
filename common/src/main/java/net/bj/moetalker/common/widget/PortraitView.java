package net.bj.moetalker.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;

import net.bj.moetalker.common.R;
import net.bj.moetalker.factory.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 头像控件
 * Created by Neko-T4 on 2017/12/1.
 */

public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager, Author author){
        if (author == null)
            return;
        //进行显示
        setup(manager,author.getPortrait());
    }

    public void setup(RequestManager manager,String url){
        this.setup(manager, R.drawable.default_portrait, url);
    }

    public void setup(RequestManager manager,int resourceId,String url){
        if(url == null)
            url = "";

        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate() //CircleImageView 布局情况下不能使用渐变动画，使用会导致显示延迟异常
                .into(this);
    }
}
