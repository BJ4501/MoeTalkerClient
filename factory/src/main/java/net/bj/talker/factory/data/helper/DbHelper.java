package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import net.bj.moetalker.utils.CollectionUtil;
import net.bj.talker.factory.model.db.AppDatabase;
import net.bj.talker.factory.model.db.User;

import java.util.Arrays;

/**
 * 数据库的辅助工具类
 * 辅助完成：增删改
 * 解决问题：将对第三方数据库存储的方法进行封装，
 * 将来在更换存储数据库时，只需要少量改动代码即可。
 * Created by Neko-T4 on 2017/12/28.
 */
public class DbHelper {

    //fixme 单例模式
    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper(){

    }

    /**
     * 新增或修改的统一方法
     * @param tClass 传递一个Class信息
     * @param models 这个Class对应的实例的数组
     * @param <Model> 这个实例的泛型，限定条件是BaseModel
     */
    public static<Model extends BaseModel> void save(final Class<Model> tClass, final Model... models){
        if (models==null||models.length==0)
            return;

        //当前数据库的一个管理者
        DatabaseDefinition df = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        df.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                //1.使用自己的工具类转换数组
                //adapter.saveAll(CollectionUtil.toArrayList(users));
                //2.使用系统提供的方法转换
                adapter.saveAll(Arrays.asList(models));
                //唤起通知(一定要在保存完成之后再进行通知)
                instance.notifySave(tClass,models);
            }
        }).build().execute();
    }

    /**
     * 进行删除数据库的统一封装方法
     * @param tClass 传递一个Class信息
     * @param models 这个Class对应的实例的数组
     * @param <Model> 这个实例的泛型，限定条件是BaseModel
     */
    public static<Model extends BaseModel> void delete(final Class<Model> tClass, final Model... models){
        if (models==null||models.length==0)
            return;

        //当前数据库的一个管理者
        DatabaseDefinition df = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        df.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                //删除
                adapter.deleteAll(Arrays.asList(models));
                //唤起通知(一定要在保存完成之后再进行通知)
                instance.notifyDelete(tClass,models);
            }
        }).build().execute();
    }

    /**
     * 进行通知的调用
     * @param tClass
     * @param models
     * @param <Model>
     */
    //final:方法不允许被复写
    private final <Model extends BaseModel> void notifySave(final Class<Model> tClass, final Model... models){
        //TODO
    }

    /**
     * 进行通知的删除
     * @param tClass
     * @param models
     * @param <Model>
     */
    //final:方法不允许被复写
    private final <Model extends BaseModel> void notifyDelete(final Class<Model> tClass, final Model... models){
        //TODO
    }

}
