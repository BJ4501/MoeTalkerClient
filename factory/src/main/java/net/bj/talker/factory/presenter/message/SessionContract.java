package net.bj.talker.factory.presenter.message;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.Session;

/**
 * Created by Neko-T4 on 2018/1/11.
 */

public interface SessionContract {
    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter,Session>{

    }

}
