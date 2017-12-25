package net.bj.talker.factory.presenter.contact;

import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.User;

import java.util.List;

/**
 * Created by Neko-T4 on 2017/12/25.
 */

public interface ContactContract {
    //什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter{

    }

    //都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter,User>{

    }

}
