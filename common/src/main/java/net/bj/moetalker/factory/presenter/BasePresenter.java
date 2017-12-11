package net.bj.moetalker.factory.presenter;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private T mView;

    public BasePresenter(T view){
       setView(view);
    }

    /**
     * 设置一个View，子类可以复写
     */
    @SuppressWarnings("unchecked")
    protected void setView(T view){
        this.mView = view;
        this.mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许被复写
     * @return View
     */
    protected final T getView(){
        return mView;
    }

    @Override
    public void start() {
        //开始的时候进行Loading
        T view = mView;
        if(view != null){
            view.showLoading();
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void destory() {
        //结束的时候
        T view = mView;
        mView = null;
        if(view != null){
            //把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}
