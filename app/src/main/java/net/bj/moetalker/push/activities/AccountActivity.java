package net.bj.moetalker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.bj.moetalker.common.app.Activity;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.frags.account.UpdateInfoFragment;

public class AccountActivity extends Activity {
    /**
     * 账户Activity显示的入口
     * @param context Context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,new UpdateInfoFragment())
                .commit();

    }
}
