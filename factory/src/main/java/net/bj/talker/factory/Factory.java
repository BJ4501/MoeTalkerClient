package net.bj.talker.factory;

import net.bj.moetalker.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Neko-T4 on 2017/12/7.
 */

public class Factory {
    //单例模式
    public static final Factory instance;
    private final Executor executor;

    static {
        instance = new Factory();
    }

    private Factory(){
        //新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
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


}
