package net.bj.talker.factory.presenter.message;

import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.data.message.MessageDataSource;
import net.bj.talker.factory.data.message.MessageRepository;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.model.db.User;

/**
 * Created by Neko-T4 on 2018/1/10.
 */

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter{

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        //数据源，View，接收者，接收者类型
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);


    }

    @Override
    public void start() {
        super.start();
        //从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}
