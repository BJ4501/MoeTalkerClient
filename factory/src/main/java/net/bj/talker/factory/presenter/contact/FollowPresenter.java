package net.bj.talker.factory.presenter.contact;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.presenter.BasePresenter;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.model.card.UserCard;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by Neko-T4 on 2017/12/22.
 */

public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.Presenter, DataSource.Callback<UserCard>{
    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id,this);


    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        final FollowContract.View view = getView();
        //成功
        if(view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onFollowSucceed(userCard);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final FollowContract.View view = getView();

        if(view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }
}
