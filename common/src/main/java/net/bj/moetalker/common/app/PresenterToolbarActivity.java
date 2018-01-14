package net.bj.moetalker.common.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.bj.moetalker.common.R;
import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * Created by Neko-T4 on 2017/12/21.
 */

public abstract class PresenterToolbarActivity<Presenter extends BaseContract.Presenter>
        extends ToolbarActivity implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;
    protected ProgressDialog mLoadingDialog;

    @Override
    protected void initBefore() {
        super.initBefore();
        //初始化Presenter
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭时进行销毁的操作
        if (mPresenter != null){
            mPresenter.destory();
        }
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        //不管怎么样，先隐藏Loading
        hideDialogLoading();
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
        }else {
            ProgressDialog dialog = mLoadingDialog;
            if (dialog == null){
                dialog = new ProgressDialog(this,R.style.AppTheme_Dialog_Alert_Light);
                //设置不可以触摸点击取消
                dialog.setCanceledOnTouchOutside(false);
                //可以取消(强制取消)，就关闭界面
                dialog.setCancelable(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                mLoadingDialog = dialog;
            }
            dialog.setMessage(getText(R.string.prompt_loading));
            dialog.show();
        }
    }

    //隐藏Loading
    protected void hideDialogLoading(){
        ProgressDialog dialog = mLoadingDialog;
        if (dialog != null){
            mLoadingDialog = null;
            dialog.dismiss();
        }
    }

    protected void hideLoading(){
        //不管怎么样，先隐藏Loading
        hideDialogLoading();
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerOk();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }


}
