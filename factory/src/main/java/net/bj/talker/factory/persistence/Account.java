package net.bj.talker.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import net.bj.talker.factory.Factory;

/**
 * Created by Neko-T4 on 2017/12/13.
 */

public class Account {
    public static final String KEY_PUSH_ID = "KEY_PUSH_ID";

    //设备的推送Id
    private static String pushId;

    /**
     * 存储数据到XML文件持久化
     */
    private static void save(Context context){
        //获取数据持久化
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        //存储数据  apply为异步操作，commit为同步操作
        sp.edit().putString(KEY_PUSH_ID,pushId).apply();
    }

    /**
     * 进行数据加载
     */
    public static void load(Context context){
        //获取数据持久化
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID,"");

    }

    /**
     * 设置并存储设备的Id
     * @param pushId 设备的推送Id
     */
    public static void setPushId(String pushId){
        Account.pushId = pushId;
        Account.save(Factory.app());
    }

    /**
     * 获取推送ID
     * @return 推送Id
     */
    public static String getPushId(){
        return pushId;
    }

    /**
     * 返回当前账户是否登录
     * @return True 已登录
     */
    public static Boolean isLogin(){
        return true;
    }

    /**
     * 是否已经绑定到了服务器
     * @return True已绑定
     */
    public static boolean isBind(){
        return false;
    }

}
