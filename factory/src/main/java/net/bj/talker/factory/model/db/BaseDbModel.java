package net.bj.talker.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;

import net.bj.talker.factory.utils.DiffUiDataCallback;

/**
 * APP中的基础的一个BaseDbModel
 * 基础的数据库框架DbFlow中的基础类
 * 同时定义了需要的方法
 * Created by Neko-T4 on 2018/1/4.
 */

public abstract class BaseDbModel<Model> extends BaseModel implements DiffUiDataCallback.UiDataDiffer<Model>{

}
