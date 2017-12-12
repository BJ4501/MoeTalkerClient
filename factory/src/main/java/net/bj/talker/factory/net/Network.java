package net.bj.talker.factory.net;

import net.bj.moetalker.common.Common;
import net.bj.talker.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 * Created by Neko-T4 on 2017/12/12.
 */

public class Network {

    //构建一个Retrofit
    public static Retrofit getRetrofit(){

        //得到一个OKClient
        OkHttpClient client = new OkHttpClient.Builder().build();


        Retrofit.Builder builder = new Retrofit.Builder();

        //设置与服务端链接,
        return builder.baseUrl(Common.Constance.API_URL)
                .client(client) //设置client
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))//设置json解析器
                .build();
    }
}
