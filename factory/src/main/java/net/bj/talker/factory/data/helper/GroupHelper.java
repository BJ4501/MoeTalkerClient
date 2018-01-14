package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.group.GroupCreateModel;
import net.bj.talker.factory.model.card.GroupCard;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.Group_Table;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.net.Network;
import net.bj.talker.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 对群的一个简单的辅助工具类
 * Created by Neko-T4 on 2017/12/29.
 */

public class GroupHelper {
    public static Group find(String groupId) {
        Group group = findFromLocal(groupId);
        if (group == null)
            group = findFromNet(groupId);
        return group;
    }

    //从本地找Group
    public static Group findFromLocal(String groupId) {
        return SQLite.select()
                .from(Group.class)
                .where(Group_Table.id.eq(groupId))
                .querySingle();
    }

    //从网络找Group
    public static Group findFromNet(String id){
        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<GroupCard>> response = remoteService.groupFind(id).execute();
            GroupCard card = response.body().getResult();
            if (card != null){
                //数据库的存储，并通知
                Factory.getGroupCenter().dispatch(card);
                //获取自己信息
                User user = UserHelper.search(card.getOwnerId());
                if (user != null){
                    return card.build(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //群的创建 网络请求
    public static void create(GroupCreateModel model, final DataSource.Callback<GroupCard> callback) {
        RemoteService service = Network.remote();
        service.groupCreate(model)
                .enqueue(new Callback<RspModel<GroupCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                        RspModel<GroupCard> rspModel = response.body();
                        if (rspModel.success()){
                            GroupCard groupCard = rspModel.getResult();
                            //唤起进行保存的操作
                            Factory.getGroupCenter().dispatch(groupCard);
                            //返回数据
                            callback.onDataLoaded(groupCard);
                        }else {
                            Factory.decodeRspCode(rspModel,callback);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {
                        callback.onDataNotAvailable(R.string.data_network_error);
                    }
                });
    }
}
