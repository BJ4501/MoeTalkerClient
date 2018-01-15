package net.bj.talker.factory.presenter.message;

import net.bj.talker.factory.data.helper.GroupHelper;
import net.bj.talker.factory.data.helper.UserHelper;
import net.bj.talker.factory.data.message.MessageGroupRepository;
import net.bj.talker.factory.data.message.MessageRepository;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.view.MemberUserModel;
import net.bj.talker.factory.persistence.Account;

import java.util.List;

/**
 * 群聊天的逻辑
 * Created by Neko-T4 on 2018/1/10.
 */

public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter{

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        //数据源，View，接收者，接收者类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();
        //从本地拿这个群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null){
            //初始化操作
            ChatContract.GroupView view = getView();
            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);
            //基础信息初始化
            view.onInit(group);
            //初始化成员
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            //没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models,moreCount);
        }
    }
}