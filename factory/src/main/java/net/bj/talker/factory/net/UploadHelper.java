package net.bj.talker.factory.net;

import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import net.bj.moetalker.utils.HashUtil;
import net.bj.talker.factory.Factory;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类，用于上传任意文件到阿里OSS存储
 * Created by Neko-T4 on 2017/12/7.
 */

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();
    //EndPoint 与存储区域有关系 此地址是华北地区
    public static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    //上传的仓库名
    private static final String BUCKET_NAME = "moetalker";

    private static OSS getClient(){

        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
        OSSCredentialProvider credentialProvider =
                new OSSPlainTextAKSKCredentialProvider("LTAIxxWjboWEUVvD","4BEfn0LKbZZGOl3sOL9FvufCdBDe3H");

        return new OSSClient(Factory.app(),ENDPOINT,credentialProvider);

        /*  OSSCredentialProvider credentialProvider1 = new OSSStsTokenCredentialProvider("<StsToken.AccessKeyId>", "<StsToken.SecretKeyId>", "<StsToken.SecurityToken>");
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);*/

    }

    /**
     * 上传最终方法，成功返回一个路径
     * @param objKey 上传上去后，在服务器上的独立的KEY
     * @param path 需要上传的文件的路径
     * @return 存储地址
     */
    private static String upload(String objKey,String path){
        //构造上传请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, objKey, path);
        try {
            //初始化上传的Client
            OSS client = getClient();
            //开始上传
            PutObjectResult result = client.putObject(request);
            //得到一个外网可访问的地址
            String url = client.presignPublicObjectURL(BUCKET_NAME,objKey);
            //打印格式
            Log.e(TAG,String.format("PublicObjectURL:%s",url));
            return url;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.toString());
            //如果有异常则返回空
            return null;
        }
    }

    /**
     * 上传普通图片
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path){
        String key = getImageObjKey(path);
        return upload(key,path);
    }

    /**
     * 上传头像
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String path){
        String key = getPortraitObjKey(path);
        return upload(key,path);
    }

    /**
     * 上传音频
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAudio(String path){
        String key = getAudioObjKey(path);
        return upload(key,path);
    }

    /**
     * 打印月份时间戳，避免每个文件夹文件太多
     * @return yyyyMM
     */
    private static String getDateString(){
        return DateFormat.format("yyyyMM",new Date()).toString();
    }

    // image/201712/fsfe23rewr2324fdfs55.jpg
    private static final String getImageObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("image/%s/%s.jpg",dateString,fileMd5);
    }

    // portrait/201712/fsfe23rewr2324fdfs55.jpg
    private static final String getPortraitObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait/%s/%s.jpg",dateString,fileMd5);
    }

    // audio/201712/fsfe23rewr2324fdfs55.mp3
    private static final String getAudioObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("audio/%s/%s.mp3",dateString,fileMd5);
    }

}
