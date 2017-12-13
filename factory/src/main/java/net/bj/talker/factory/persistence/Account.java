package net.bj.talker.factory.persistence;

/**
 * Created by Neko-T4 on 2017/12/13.
 */

public class Account {
    //设备的推送Id
    private static String pushId = "test";

    public static void setPushId(String pushId){
        Account.pushId = pushId;
    }

    public static String getPushId(){
        return pushId;
    }

}
