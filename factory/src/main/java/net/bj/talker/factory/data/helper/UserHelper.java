package net.bj.talker.factory.data.helper;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.api.user.UserUpdateModel;
import net.bj.talker.factory.model.card.UserCard;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.net.Network;
import net.bj.talker.factory.net.RemoteService;
import net.bj.talker.factory.presenter.contact.FollowPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Neko-T4 on 2017/12/19.
 */

public class UserHelper {
    //更新用户信息--异步
    public static void update(UserUpdateModel model,final DataSource.Callback<UserCard> callback){
        //调用Retrofit对网络请求接口做代理
        RemoteService service = Network.remote();
        //得到Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        //网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    //数据库的存储操作，需要把UserCard转换为User
                    User user = userCard.build();
                    user.save();
                    //返回成功
                    callback.onDataLoaded(userCard);
                }else {
                    //错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    //搜索用户信息--异步
    public static Call search(String name,final DataSource.Callback<List<UserCard>> callback){
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);

        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()){
                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                }else {
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        //把当前的调度者返回
        return call;
    }

    //关注的网络请求
    public static void follow(String id,final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    //保存到本地数据库
                    User user = userCard.build();
                    user.save();
                    //TODO 通知联系人列表刷新


                    //返回数据
                    callback.onDataLoaded(userCard);
                }else {
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
