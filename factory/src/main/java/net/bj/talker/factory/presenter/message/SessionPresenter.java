package net.bj.talker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import net.bj.talker.factory.data.message.SessionDataSource;
import net.bj.talker.factory.data.message.SessionRepository;
import net.bj.talker.factory.model.db.Session;
import net.bj.talker.factory.presenter.BaseSourcePresenter;
import net.bj.talker.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 最近聊天列表
 * Created by Neko-T4 on 2018/1/11.
 */

public class SessionPresenter extends BaseSourcePresenter<Session,Session,SessionDataSource,SessionContract.View>
        implements SessionContract.Presenter{

    //初始化
    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view == null)
            return;
        //差异对比
        List<Session> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(old,sessions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //刷新界面
        refreshData(result,sessions);
    }
}
