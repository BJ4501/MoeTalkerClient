package net.bj.talker.factory.presenter.user;

import android.text.TextUtils;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.model.api.user.UserUpdateModel;
import net.bj.talker.factory.model.card.UserCard;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.net.UploadHelper;
import net.bj.talker.factory.presenter.account.LoginContract;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by Neko-T4 on 2017/12/19.
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter,DataSource.Callback<UserCard>{
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath,final String desc,final Boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();
        if(TextUtils.isEmpty(photoFilePath)||TextUtils.isEmpty(desc)){
            view.showError(R.string.data_account_update_invalid_parameter);
        }else {
            //上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if(TextUtils.isEmpty(url)){
                        //上传失败
                        view.showError(R.string.data_upload_error);
                    }else {
                        //构建Model
                        UserUpdateModel model = new UserUpdateModel("",url,desc,isMan? User.SEX_MAN : User.SEX_WOMAN);
                        //进行网络请求，上传
                        UserHelper.update(model,UpdateInfoPresenter.this);

                    }
                }
            });
        }
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        //进行线程切换--强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        //进行线程切换--强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
