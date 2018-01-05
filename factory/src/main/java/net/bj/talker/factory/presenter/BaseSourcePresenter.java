package net.bj.talker.factory.presenter;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.data.DbDataSource;
import net.bj.moetalker.factory.presenter.BaseContract;
import net.bj.moetalker.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * 基础的仓库源的Presenter定义
 * Created by Neko-T4 on 2018/1/5.
 */

public abstract class BaseSourcePresenter<Data,ViewModel,Source extends DbDataSource<Data>,View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel,View> implements DataSource.SucceedCallback<List<Data>>{

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void destory() {
        super.destory();
        mSource.dispose();
        mSource = null;
    }
}
