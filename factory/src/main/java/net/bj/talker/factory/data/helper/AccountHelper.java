package net.bj.talker.factory.data.helper;

import net.bj.moetalker.factory.data.DataSource;
import net.bj.talker.factory.R;
import net.bj.talker.factory.model.api.account.RegisterModel;
import net.bj.talker.factory.model.db.User;

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

        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                callback.onDataNotAvailable(R.string.data_rsp_error_parameters);
            }
        }.start();

    }
}
