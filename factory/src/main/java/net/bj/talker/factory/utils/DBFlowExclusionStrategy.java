package net.bj.talker.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * DBFlow的数据库过滤字段 Gson
 * Created by Neko-T4 on 2017/12/14.
 */

public class DBFlowExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        //被跳过的字段
        //只要是属于DBFlow数据的
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        //被跳过的Class
        return false;
    }
}
