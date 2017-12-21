package net.bj.moetalker.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.bj.moetalker.common.R;

/**
 * Created by Neko-T4 on 2017/12/21.
 */

public abstract class ToolbarActivity extends Activity{
    protected Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    /**
     * 初始化Toolbar
     * @param toolbar Toolbar
     */
    public void initToolbar(Toolbar toolbar){
        mToolbar = toolbar;
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack(){
        //设置左上角返回按钮为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

}
