package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.moetalker.utils.CollectionUtil;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.api.user.UserUpdateModel;
import net.bj.talker.factory.model.card.UserCard;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.User_Table;
import net.bj.talker.factory.model.db.view.UserSampleModel;
import net.bj.talker.factory.net.Network;
import net.bj.talker.factory.net.RemoteService;
import net.bj.talker.factory.persistence.Account;
import net.bj.talker.factory.presenter.contact.FollowPresenter;

import java.io.IOException;
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
                    //唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
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
                    //唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
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

    // 刷新联系人的操作，不需要Callback，
    // 直接存储到数据库，并通过数据库观察者进行通知界面更新，
    // 界面更新的时候进行对比，差异更新
    public static void refreshContacts(){
        RemoteService service = Network.remote();
        service.userContacts().enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()){
                    //拿到集合
                    List<UserCard> cards = rspModel.getResult();
                    if (cards==null||cards.size()==0)
                        return;
                    //唤起进行保存的操作
                    //方法1
                    //Factory.getUserCenter().dispatch(cards.toArray(new UserCard[0]));
                    //方法2
                    UserCard[] cards1 = CollectionUtil.toArray(cards,UserCard.class);
                    Factory.getUserCenter().dispatch(cards1);

                }else {
                    Factory.decodeRspCode(rspModel,null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
               //nothing
            }
        });
    }

    //从本地查询一个用户的信息
    public static User findFromLocal(String id){
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    //从网络查询一个用户的信息
    public static User findFromNet(String id){

        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null){
                User user = card.build();
                //数据库的存储，并通知
                Factory.getUserCenter().dispatch(card);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 搜索一个用户，优先本地缓存，
     * 没有再从网络拉取
     */
    public static User search(String id){
        User user = findFromLocal(id);
        if (user == null)
            return findFromNet(id);
        return user;
    }

    /**
     * 搜索一个用户，优先网络查询，
     * 没有再从本地缓存拉取
     */
    public static User searchFirstOfNet(String id){
        User user = findFromNet(id);
        if (user == null)
            return findFromLocal(id);
        return user;
    }

    /**
     * 获取联系人
     */
    public static List<User> getContact(){
        return SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .queryList();
    }

    /**
     * 获取一个联系人列表，
     * 但是是一个简单的数据表
     */
    public static List<UserSampleModel> getSampleContact(){
        return SQLite.select(User_Table.id.withTable().as("id"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .queryCustomList(UserSampleModel.class);//是查询符合这个类UserSampleModel结构
    }
}
