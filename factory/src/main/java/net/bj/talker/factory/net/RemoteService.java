package net.bj.talker.factory.net;

import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.api.account.LoginModel;
import net.bj.talker.factory.model.api.account.RegisterModel;
import net.bj.talker.factory.model.api.user.UserUpdateModel;
import net.bj.talker.factory.model.card.UserCard;
import net.bj.talker.factory.model.db.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 * Created by Neko-T4 on 2017/12/12.
 */

public interface RemoteService {

    /**
     * 注册接口
     * @param model 传入的是RegisterModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId")String pushId);

    /**
     * 用户更新接口
     */
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    //用户搜索接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    //用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);

}
