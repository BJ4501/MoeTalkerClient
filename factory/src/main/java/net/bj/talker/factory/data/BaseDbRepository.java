package net.bj.talker.factory.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.factory.data.DbDataSource;
import net.bj.moetalker.utils.CollectionUtil;
import net.bj.talker.factory.data.helper.DbHelper;
import net.bj.talker.factory.model.db.BaseDbModel;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.persistence.Account;

import java.util.LinkedList;
import java.util.List;

/**
 * 基础的数据库仓库
 * 实现对数据库的基本的监听操作
 * Created by Neko-T4 on 2018/1/4.
 */
public abstract class BaseDbRepository<Data extends BaseDbModel<Data>> implements DbDataSource<Data>,
        DbHelper.ChangedListener<Data>,
        QueryTransaction.QueryResultListCallback<Data>{

    //和Presenter交互的回调
    private SucceedCallback<List<Data>> callback;
    //当前缓存的List
    private final List<Data> dataList = new LinkedList<>();
    //当前泛型对应的真实的class信息
    private Class<Data> dataClass;

    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
    }

    @Override
    public void dispose() {
        this.callback = null;
    }

    //数据库统一通知的地方:增加，更改
    @Override
    public void onDataSave(Data[] list) {
        boolean isChanged = false;
        //当数据库数据变更的操作
        for (Data data : list) {
            //是关注的人，同时不是我自己
            if (isRequired(data)){
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        //有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    //数据库统一通知的地方:删除
    @Override
    public void onDataDelete(Data[] list) {
        //在删除情况下，不用进行过滤判断
        //当数据库数据删除的操作
        boolean isChanged = false;
        for (Data data : list) {
            if (dataList.remove(data))
                isChanged = true;
        }
        //有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    //DbFlow框架通知的回调
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        //数据库加载数据成功
        if (tResult.size() == 0){
            dataList.clear();
            notifyDataChange();
            return;
        }
        //转变为数组
        Data[] data = CollectionUtil.toArray(tResult,dataClass);
        //回到数据集更新的操作中
        onDataSave(data);
    }

    /**
     * 是否是需要关注的数据
     * @param data Data
     * @return
     */
    protected abstract boolean isRequired(Data data);

    //通知界面刷新的方法
    private void notifyDataChange(){
        SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null)
            callback.onDataLoaded(dataList);
    }

    //插入或者更新
    private void insertOrUpdate(Data data){
        int index = indexOf(data);
        if (index >= 0){
            replace(index,data);
        }else {
            insert(data);
        }
    }

    //更新操作，更新某个坐标下的数据
    protected void replace(int index,Data data){
        dataList.remove(index);
        dataList.add(index,data);
    }

    //添加方法
    protected void insert(Data data){
        dataList.add(data);
    }

    //查询一个数据是否在当前的缓存数据中，如果在则返回坐标
    protected int indexOf(Data newData){
        //如果有这个user的情况
        int index = -1;
        for (Data data : dataList) {
            index++;
            if(data.isSame(newData)){
                return index;
            }
        }
        return -1;
    }

}
