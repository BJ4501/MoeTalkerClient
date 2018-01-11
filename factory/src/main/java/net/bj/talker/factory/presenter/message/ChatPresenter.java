package net.bj.talker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import net.bj.talker.factory.data.helper.MessageHelper;
import net.bj.talker.factory.data.message.MessageDataSource;
import net.bj.talker.factory.model.api.message.MsgCreateModel;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.persistence.Account;
import net.bj.talker.factory.presenter.BaseSourcePresenter;
import net.bj.talker.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 聊天基础类Presenter
 * Created by Neko-T4 on 2018/1/10.
 */

public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Message,Message,MessageDataSource,View>
        implements ChatContract.Presenter{

    //接收者id，可能是群或人的id
    protected String mReceiverId;
    //区分是人或群的id
    protected int mReceiverType;

    public ChatPresenter(MessageDataSource source, View view,
                         String receiverId, int receiverType) {
        super(source, view);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void pushText(String content) {
        //构建一个新的消息
        MsgCreateModel model = new MsgCreateModel.Builder()
                .receiver(mReceiverId,mReceiverType)
                .content(content,Message.TYPE_STR)
                .build();

        //进行网络发送
        MessageHelper.push(model);
    }

    @Override
    public void pushAudio(String path) {
        //发送语音

    }

    @Override
    public void pushImages(String[] paths) {
        //发送图片

    }

    @Override
    public boolean rePush(Message message) {
        //确定消息是可以重复发送的
        if (Account.getUserId().equalsIgnoreCase(message.getSender().getId())
                && message.getStatus() == Message.STATUS_FAILED){
            //更改状态
            message.setStatus(Message.STATUS_CREATED);
            //构建发送Model
            MsgCreateModel model = MsgCreateModel.buildWithMessage(message);
            MessageHelper.push(model);
            return true;
        }
        return false;
    }

    @Override
    public void onDataLoaded(List<Message> messages) {
        ChatContract.View view = getView();
        if (view == null)
            return;
        //先拿到老数据
        @SuppressWarnings("unchecked")
        List<Message> old = view.getRecyclerAdapter().getItems();
        //计算差异
        DiffUiDataCallback<Message> callback = new DiffUiDataCallback<>(old,messages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //进行界面刷新
        refreshData(result,messages);
    }
}
