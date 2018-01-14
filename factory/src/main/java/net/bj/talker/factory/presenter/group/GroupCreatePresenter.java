package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.presenter.BaseRecyclerPresenter;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.view.UserSampleModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 群创建界面的Presenter
 * Created by Neko-T4 on 2018/1/13.
 */

public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,GroupCreateContract.View>
        implements GroupCreateContract.Presenter{

    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        Factory.runOnAsync(loader);
    }

    @Override
    public void create(String name, String desc, String picture) {

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected)
            users.add(model.author.getId());
        else
            users.remove(model.author.getId());
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {

            //添加方法1
            //List<User> contact = UserHelper.getContact();
            /*List<UserSampleModel> sampleModels = new ArrayList<>();
            for (User user : contact) {

            }*/
            //方法2 特殊数据库查询
            List<UserSampleModel> sampleModels = UserHelper.getSampleContact();
            List<GroupCreateContract.ViewModel> models = new ArrayList<>();
            for (UserSampleModel sampleModel : sampleModels) {
                GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                viewModel.author = sampleModel;
                models.add(viewModel);
            }
            refreshData(models);
        }
    };
}
