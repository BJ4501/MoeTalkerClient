package net.bj.moetalker.factory.presenter;

import android.support.annotation.StringRes;

import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;

/**
 * MVP模式中公共基本契约
 * Created by Neko-T4 on 2017/12/11.
 */

public interface BaseContract {
    //基本的界面职责
    //T 必须要继承下面的Presenter
    interface View<T extends Presenter>{
        //显示一个字符串错误--公共
        void showError(@StringRes int str);

        //显示进度条--公共
        void showLoading();

        //支持设置一个Presenter
        void setPresenter(T presenter);
    }
    //基本的Presenter职责
    interface Presenter{
        //公用的开始方法
        void start();

        //公用的销毁触发
        void destory();
    }

    //基本的一个列表的View的职责
    interface RecyclerView<T extends Presenter,ViewModel> extends View<T>{
        //界面端只能刷新整个数据集合，不能精确到每一条数据更新
        //void onDone(List<User> users);

        //拿到一个适配器，然后自己自主进行刷新
        RecyclerAdapter<ViewModel> getRecyclerAdapter();

        //当适配器数据更改了的时候触发
        void onAdapterDataChanged();


    }


}
