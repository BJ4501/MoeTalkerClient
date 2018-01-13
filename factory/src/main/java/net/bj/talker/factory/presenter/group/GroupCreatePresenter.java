package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.presenter.BaseRecyclerPresenter;

/**
 * 群创建界面的Presenter
 * Created by Neko-T4 on 2018/1/13.
 */

public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,GroupCreateContract.View>
        implements GroupCreateContract.Presenter{

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void create(String name, String desc, String picture) {

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {

    }
}
