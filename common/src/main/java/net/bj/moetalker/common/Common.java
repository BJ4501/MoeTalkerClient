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

        //基础的网络请求地址
        String API_URL = "http://198.13.59.19:9090/moeTalker/api/"; //线上服务器地址
        //String API_URL = "http://192.168.1.200:8089/api/";
        //String API_URL = "http://192.168.2.144:8080/api/";

        //最大上传大小
        long MAX_UPLOAD_IMAGE_LENGTH = 860*1024;
    }



}
