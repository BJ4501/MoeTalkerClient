package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.api.account.RegisterModel;
import net.bj.talker.factory.model.db.AppDatabase;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.net.Network;
import net.bj.talker.factory.net.RemoteService;
import net.bj.talker.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求
 * Created by Neko-T4 on 2017/12/11.
 */

public class AccountHelper {

    /**
     * 注册的接口--异步调用
     * @param model 传递一个注册的Model进来
     * @param callback 成功与失败的接口回送
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){
        //调用Retrofit对网络请求接口做代理
        RemoteService service = Network.getRetrofit().create(RemoteService.class);
        //得到Call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        //异步请求
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求成功返回
                //从返回中得到全局Model，内部是使用的Gson进行解析
                RspModel<AccountRspModel> rspModel = response.body();
                if (rspModel.success()){
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //获取信息
                    User user = accountRspModel.getUser();
                    //TODO 进行数据库写入和缓存绑定
                    //第一种：直接保存
                    user.save();
                    //第二种 通过ModelAdapter保存--可以通过saveAll存储集合
                        /*FlowManager.getModelAdapter(User.class).save(user);*/
                    //第三种：事务中
                        /*DatabaseDefinition df = FlowManager.getDatabase(AppDatabase.class);
                        df.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                FlowManager.getModelAdapter(User.class).save(user);
                            }
                        }).build().execute();*/
                    // 同步到XML持久化当中
                    Account.login(accountRspModel);

                    //判断绑定状态，是否绑定设备
                    if (accountRspModel.isBind()) {
                        //然后返回
                        callback.onDataLoaded(user);
                    }else {
                        //进行绑定
                        bindPush(callback);
                    }
                }else {
                    //错误解析
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 对设备id进行绑定操作
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback){
        //先抛出一个错误，其实是绑定没有进行
        //callback.onDataNotAvailable(R.string.app_name);
        Account.setBind(true);

    }
}
