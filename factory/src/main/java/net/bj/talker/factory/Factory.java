package net.bj.talker.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.bj.moetalker.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Neko-T4 on 2017/12/7.
 */

public class Factory {
    //单例模式
    public static final Factory instance;
    //全局的线程池
    private final Executor executor;
    //全局的Gson
    private final Gson gson;

    static {
        instance = new Factory();
    }

    private Factory(){
        //新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                //TODO 设置一个过滤器，数据库界别的Model不进行Json转换
                //setExclusionStrategies()
                .create();
    }

    /**
     * 返回全局的Application
     * @return Application
     */
    public static Application app(){
        return Application.getInstance();
    }

    /**
     * 异步运行的方法
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable){
        //拿到单例，拿到线程池，异步执行
        instance.executor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson，在这可以进行Gson的一些全局的初始化
     * @return Gson
     */
    public static Gson getGson(){
        return instance.gson;
    }

}
