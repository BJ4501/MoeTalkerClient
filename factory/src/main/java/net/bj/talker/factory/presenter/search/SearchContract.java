package net.bj.talker.factory.presenter.search;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.card.GroupCard;
import net.bj.talker.factory.model.card.UserCard;

import java.util.List;

/**
 * Created by Neko-T4 on 2017/12/21.
 */

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter{
        //搜索内容
        void search(String content);
    }

    //搜索人的返回界面
    interface UserView extends BaseContract.View<Presenter>{
        void onSearchDone(List<UserCard> userCards);


    }
    //搜索群的返回界面
    interface GroupView extends BaseContract.View<Presenter>{
        void onSearchDone(List<GroupCard> groupCards);


    }


}
