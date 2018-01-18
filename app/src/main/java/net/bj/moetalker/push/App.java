package net.bj.moetalker.push;

import android.content.Context;

import com.igexin.sdk.PushManager;

import net.bj.moetalker.common.app.Application;
import net.bj.talker.factory.Factory;

/**
 * Created by Neko-T4 on 2017/12/6.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //调用Factory进行初始化
        Factory.setup();
        //推送进行初始化
        PushManager.getInstance().initialize(this);

    }

    @Override
    protected void showAccountView(Context context) {
        //TODO 登录界面的显示

    }
}
