package net.bj.talker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.Factory;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.group.GroupCreateModel;
import net.bj.talker.factory.model.card.GroupCard;
import net.bj.talker.factory.model.card.GroupMemberCard;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.GroupMember;
import net.bj.talker.factory.model.db.GroupMember_Table;
import net.bj.talker.factory.model.db.Group_Table;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.model.db.User_Table;
import net.bj.talker.factory.model.db.view.MemberUserModel;
import net.bj.talker.factory.net.Network;
import net.bj.talker.factory.net.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 对群的一个简单的辅助工具类
 * Created by Neko-T4 on 2017/12/29.
 */

public class GroupHelper {
    //搜索信息--异步
    public static Call search(String content,final DataSource.Callback<List<GroupCard>> callback){
        RemoteService service = Network.remote();
        Call<RspModel<List<GroupCard>>> call = service.groupSearch(content);

        call.enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel = response.body();
                if (rspModel.success()){
                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                }else {
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        //把当前的调度者返回
        return call;
    }

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

    //刷新我的群组列表
    public static void refreshGroups() {
        RemoteService service = Network.remote();
        service.groups("").enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel = response.body();
                if (rspModel.success()){
                    List<GroupCard> groupCards = rspModel.getResult();
                    if (groupCards != null&&groupCards.size()>0){
                        //进行调度显示
                        Factory.getGroupCenter().dispatch(groupCards.toArray(new GroupCard[0]));
                    }

                }else {
                    Factory.decodeRspCode(rspModel,null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {
               //do nothing
            }
        });

    }

    //获取一个群的成员数量
    public static long getMemberCount(String id) {
        return SQLite.selectCountOf()
                .from(GroupMember.class)
                .where(GroupMember_Table.group_id.eq(id))
                .count();
    }

    //从网络去刷新一个群的成员信息
    public static void refreshGroupMember(Group group) {
        RemoteService service = Network.remote();
        service.groupMembers(group.getId()).enqueue(new Callback<RspModel<List<GroupMemberCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupMemberCard>>> call, Response<RspModel<List<GroupMemberCard>>> response) {
                RspModel<List<GroupMemberCard>> rspModel = response.body();
                if (rspModel.success()){
                    List<GroupMemberCard> memberCards = rspModel.getResult();
                    if (memberCards != null&&memberCards.size()>0){
                        //进行调度显示
                        Factory.getGroupCenter().dispatch(memberCards.toArray(new GroupMemberCard[0]));
                    }
                }else {
                    Factory.decodeRspCode(rspModel,null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupMemberCard>>> call, Throwable t) {
                //do nothing
            }
        });
    }

    //重要:关联查询一个用户和群成员的表，返回一个MemberUserModel表的集合
    public static List<MemberUserModel> getMemberUsers(String groupId, int size) {
        return SQLite.select(GroupMember_Table.alias.withTable().as("alias"), //查询这4个列
                User_Table.id.withTable().as("userId"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(GroupMember.class) //从GroupMember表中查询
                .join(User.class, Join.JoinType.INNER) //关联查询，关联User表
                .on(GroupMember_Table.user_id.withTable().eq(User_Table.id.withTable())) //当这两个Id相同的时候，就认为是一条数据
                .where(GroupMember_Table.group_id.withTable().eq(groupId))
                .orderBy(GroupMember_Table.user_id, true) //顺序查询
                .limit(size) //查询数量
                .queryCustomList(MemberUserModel.class); //自定义查询列表返回
    }
}
