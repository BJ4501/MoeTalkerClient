package net.bj.talker.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.bj.talker.factory.Factory;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.User_Table;

/**
 * Created by Neko-T4 on 2017/12/13.
 */

public class Account {
    public static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    public static final String KEY_IS_BIND = "KEY_IS_BIND";
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";


    //设备的推送Id
    private static String pushId;
    //设备Id是否已经绑定到了服务器
    private static boolean isBind;
    //登录状态的Token，用来接口请求
    private static String token;
    //登录用户的Id
    private static String userId;
    //登录的账户
    private static String account;

    /**
     * 存储数据到XML文件持久化
     */
    private static void save(Context context){
        //获取数据持久化
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        //存储数据  apply为异步操作，commit为同步操作
        sp.edit().putString(KEY_PUSH_ID,pushId)
                .putBoolean(KEY_IS_BIND,isBind)
                .putString(KEY_TOKEN,token)
                .putString(KEY_USER_ID,userId)
                .putString(KEY_ACCOUNT,account)
                .apply();
    }

    /**
     * 进行数据加载
     */
    public static void load(Context context){
        //获取数据持久化
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID,"");
        isBind = sp.getBoolean(KEY_IS_BIND,false);
        token = sp.getString(KEY_TOKEN,"");
        userId = sp.getString(KEY_USER_ID,"");
        account = sp.getString(KEY_ACCOUNT,"");
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
        //用户ID和TOKEN都不能为空
        return !TextUtils.isEmpty(userId)&& !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经完善了用户信息
     * @return True 完成
     */
    public static boolean isComplete(){
        //TODO
        return isLogin();
    }

    /**
     * 是否已经绑定到了服务器
     * @return True已绑定
     */
    public static boolean isBind(){
        return isBind;
    }

    /**
     * 设置绑定状态
     * @param isBind
     */
    public static void setBind(boolean isBind){
        Account.isBind = isBind;
        Account.save(Factory.app());
    }

    /**
     * 保存自己的信息到持久化XML中
     *
     */
    public static void login(AccountRspModel model){
        //存储用户的账户，Token，用户Id，方便从数据库中查询用户信息
        Account.token = model.getToken();
        Account.account = model.getAccount();
        Account.userId = model.getUser().getId();
        save(Factory.app());
    }

    /**
     * 获取当前登录的用户信息
     * @return User
     */
    public static User getUser(){
        //如果为null返回一个new的User，其次从数据库查询
        return TextUtils.isEmpty(userId)? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(userId))
                .querySingle();
    }

    /**
     * 获取当前Token
     * @return
     */
    public static String getToken(){
        return token;
    }

}
