package net.bj.talker.factory.presenter.group;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.User;

/**
 * 我的群列表契约
 * Created by Neko-T4 on 2017/12/25.
 */

public interface GroupsContract {
    //什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter{

    }

    //都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter,Group>{

    }

}
