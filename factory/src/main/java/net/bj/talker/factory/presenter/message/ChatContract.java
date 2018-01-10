package net.bj.talker.factory.presenter.message;

import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.model.db.User;

/**
 * 聊天契约
 * Created by Neko-T4 on 2018/1/10.
 */

public interface ChatContract {
    interface Presenter extends BaseContract.Presenter{
        //发送文字
        void pushText(String content);
        //发送语音
        void pushAudio(String path);
        //发送图片
        void pushImages(String[] paths);
        //重新发送一个消息，返回是否调度成功
        boolean rePush(Message message);
    }

    //界面基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter,Message>{
        //初始化的Model
        void onInit(InitModel model);
    }

    //人聊天的界面
    interface UserView extends View<User>{

    }

    //群聊天的界面
    interface GroupView extends  View<Group>{

    }


}
