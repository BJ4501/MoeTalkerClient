package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.view.MemberUserModel;

/**
 * 群成员的契约
 * Created by Neko-T4 on 2018/1/15.
 */

public interface GroupMembersContract {
    interface Presenter extends BaseContract.Presenter{
        //刷新
        void refresh();
    }

    //界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel>{
        //获取群的Id
        String getmGroupId();
    }
}
