package net.bj.talker.factory.net;

import net.bj.talker.factory.model.api.RspModel;
import net.bj.talker.factory.model.api.account.AccountRspModel;
import net.bj.talker.factory.model.api.account.RegisterModel;
import net.bj.talker.factory.model.db.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 网络请求的所有的接口
 * Created by Neko-T4 on 2017/12/12.
 */

public interface RemoteService {

    /**
     * 网络请求一个注册接口
     * @param model 传入的是RegisterModel
     * @return RspModel<AccountRspModel>
     */
    @POST
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

}
