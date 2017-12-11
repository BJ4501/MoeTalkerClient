package net.bj.moetalker.factory.presenter;

import android.support.annotation.StringRes;

/**
 * MVP模式中公共基本契约
 * Created by Neko-T4 on 2017/12/11.
 */

public interface BaseContract {

    //T 必须要继承下面的Presenter
    interface View<T extends Presenter>{
        //显示一个字符串错误--公共
        void showError(@StringRes int str);

        //显示进度条--公共
        void showLoading();

        //支持设置一个Presenter
        void setPresenter(T presenter);
    }

    interface Presenter{
        //公用的开始方法
        void start();

        //公用的销毁触发
        void destory();
    }


}
