package net.bj.talker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import net.bj.talker.factory.data.message.MessageDataSource;
import net.bj.talker.factory.model.db.Message;
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

    protected String mReceiverId;
    protected int mReceiverType;

    public ChatPresenter(MessageDataSource source, View view,
                         String receiverId, int receiverType) {
        super(source, view);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void pushText(String content) {

    }

    @Override
    public void pushAudio(String path) {

    }

    @Override
    public void pushImages(String[] paths) {

    }

    @Override
    public boolean rePush(Message message) {
        return false;
    }

    @Override
    public void onDataLoaded(List<Message> messages) {
        ChatContract.View view = getView();
        if (view == null)
            return;
        //先拿到老数据
        List<Message> old = view.getRecyclerAdapter().getItems();
        //计算差异
        DiffUiDataCallback<Message> callback = new DiffUiDataCallback<>(old,messages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //进行界面刷新
        refreshData(result,messages);
    }
}
