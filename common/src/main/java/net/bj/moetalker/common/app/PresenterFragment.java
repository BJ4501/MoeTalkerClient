package net.bj.moetalker.common.app;

import android.content.Context;

import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * Created by Neko-T4 on 2017/12/11.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>
        extends Fragment implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        //显示错误，优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }
}
