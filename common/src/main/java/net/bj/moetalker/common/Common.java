package net.bj.moetalker.common;

/**
 * Created by Neko-T4 on 2017/11/24.
 */

public class Common {

    /**
     * 一些不可变的参数，通常用于一些配置
     */
    public interface Constance{
        //手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";
    }



}
