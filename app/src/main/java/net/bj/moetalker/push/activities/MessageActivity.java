package net.bj.moetalker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;

import net.bj.moetalker.common.app.Activity;
import net.bj.moetalker.factory.model.Author;
import net.bj.moetalker.push.R;
import net.bj.talker.factory.model.db.Group;

public class MessageActivity extends Activity {
    //接收者Id，可以是群，也可以是人的Id
    private static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    //是否是群
    private static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private String mReceiverId;
    private String mIsGroup;

    /**
     * 显示人的聊天界面
     * @param context 上下文
     * @param author 人的信息
     */
    public static void show(Context context, Author author){
        if (author == null || context == null || TextUtils.isEmpty(author.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,author.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,false);
        context.startActivity(intent);
    }

    /**
     * 发起群聊天
     * @param context 上下文
     * @param group 群的Model
     */
    public static void show(Context context, Group group){
        if (group == null || context == null || TextUtils.isEmpty(group.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,group.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,true);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

}
