package net.bj.moetalker.push;

import android.os.Bundle;
import net.bj.moetalker.common.app.Activity;
import net.bj.moetalker.push.activities.MainActivity;
import net.bj.moetalker.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PermissionsFragment.haveAll(this,getSupportFragmentManager())){
            MainActivity.show(this);
        }
    }
}
