package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.presenter.BaseRecyclerPresenter;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.data.helper.GroupHelper;
import net.bj.talker.factory.model.db.view.MemberUserModel;

import java.util.List;

/**
 * Created by Neko-T4 on 2018/1/15.
 */

public class GroupMembersPresenter extends BaseRecyclerPresenter<MemberUserModel, GroupMembersContract.View>
        implements GroupMembersContract.Presenter{

    public GroupMembersPresenter(GroupMembersContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        //显示Loading
        start();
        //异步加载
        Factory.runOnAsync(loader);
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMembersContract.View view = getView();
            if (view == null)
                return;
            String groupId = view.getmGroupId();
            //传递-1代表查询所有
            List<MemberUserModel> models = GroupHelper.getMemberUsers(groupId,-1);

            refreshData(models);
        }
    };
}
