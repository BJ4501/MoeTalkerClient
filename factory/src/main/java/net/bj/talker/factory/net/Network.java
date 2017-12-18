package net.bj.talker.factory.net;

import android.text.TextUtils;

import net.bj.moetalker.common.Common;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 * Created by Neko-T4 on 2017/12/12.
 */

public class Network {
    private static Network instance;
    private Retrofit retrofit;

    static {
        instance = new Network();
    }

    private Network(){
    }

    //构建一个Retrofit
    public static Retrofit getRetrofit(){
        if(instance.retrofit != null)
            return instance.retrofit;

        //得到一个OKClient
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有的请求添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求，重新进行Build
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        if(!TextUtils.isEmpty(Account.getToken())){
                            //注入一个Token
                            builder.addHeader("token",Account.getToken());
                        }
                        builder.addHeader("Content-Type","application/json");
                        Request newRequest = builder.build();
                        // 返回
                        return chain.proceed(newRequest);
                    }
                })
                .build();


        Retrofit.Builder builder = new Retrofit.Builder();

        //设置与服务端链接,
        instance.retrofit = builder.baseUrl(Common.Constance.API_URL)
                .client(client) //设置client
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))//设置json解析器
                .build();

        return instance.retrofit;
    }

    /**
     * 返回一个请求代理
     * @return RemoteService
     */
    public static RemoteService remote(){
        return Network.getRetrofit().create(RemoteService.class);
    }
}
