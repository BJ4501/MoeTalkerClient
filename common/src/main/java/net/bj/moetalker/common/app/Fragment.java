package net.bj.moetalker.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.bj.moetalker.common.widget.convention.PlaceHolderView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Neko-T4 on 2017/11/24.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {

    protected View mRoot;
    protected Unbinder mRootUnBinder;
    protected PlaceHolderView mPlaceHolderView;
    //是否第一次初始化数据
    protected boolean mIsFirstInitData = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRoot == null){
            int layId = getContentLayoutId();
            //初始化当前的根布局，但是不在创建时就添加到container里面--->false
            View root = inflater.inflate(layId,container,false);
            initWidget(root);
            mRoot = root;
        }else {
            if(mRoot.getParent()!=null){
                //把当前Root从其父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    /**
     * 当界面初始化完成之后
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData){
            //触发一次之后就不会触发
            mIsFirstInitData = false;
            //触发
            onFirstInit();
        }
        //当View创建完成之后初始化数据
        initData();
    }

    /**
     * 是否初始化成功  初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs(Bundle bundle){
        //Bundle:用于Activity之间传递
        return true;
    }

    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this,root);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit(){

    }

    /**
     * 返回按键触发时调用
     * @return  返回True代表我已处理返回逻辑，Activity不用自己finish
     * 返回false代表我没有处理,Activity自己走自己的逻辑
     */
    public boolean onBackPrecessed(){
        return false;
    }

    /**
     * 设置占位布局
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView){
        this.mPlaceHolderView = placeHolderView;
    }

}
