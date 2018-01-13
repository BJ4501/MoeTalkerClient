package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.model.Author;
import net.bj.moetalker.factory.presenter.BaseContract;

/**
 * 群创建的契约
 * Created by Neko-T4 on 2018/1/13.
 */

public interface GroupCreateContract {
    interface Presenter extends BaseContract.Presenter{
        //创建
        void create(String name, String desc, String picture);
        //更改一个Model的选中状态
        void changeSelect(ViewModel model,boolean isSelected);
    }
    interface View extends BaseContract.RecyclerView<Presenter,ViewModel>{
        //创建成功
        void onCreateSucceed();

    }

    class ViewModel{
        //用户信息
        Author author;
        //是否选中
        boolean isSelected;
    }
}
