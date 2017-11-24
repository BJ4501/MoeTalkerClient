package net.bj.moetalker.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Neko-T4 on 2017/11/24.
 */

public abstract class Activity extends AppCompatActivity {
    //onCreate常规初始化界面 在此类中
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用的初始化窗口
        initWindows();

        if(initArgs(getIntent().getExtras())){
            getContentLayoutId();
            initWidget();
            initData();
        }else {
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWindows(){

    }

    /**
     * 是否初始化成功  初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs(Bundle bundle){
        //TODO Bundle是什么？
        //Bundle:用于Activity之间传递
        return true;
    }

    //abstract 子类必须实现，自己可不必实现
    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(){

    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {



        //当手机返回按钮被点击时
        super.onBackPressed();
        finish();
    }
}
