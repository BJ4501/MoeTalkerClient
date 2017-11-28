package net.bj.moetalker.push;

import android.text.TextUtils;

/**
 * Created by Neko-T4 on 2017/11/28.
 */

public class Presenter implements IPresenter{

    private IView mView;
    public Presenter (IView view){
        mView = view;
    }

    @Override
    public void Search() {
        //开启界面Loading
        String inputString = mView.getInputString();
        if(TextUtils.isEmpty(inputString)){
            //为空直接返回
            return;
        }

        int hashCode = inputString.hashCode();
        IUserService service = new UserService();

        String serviceResult = service.search(hashCode);

        String result = "Result: "+inputString+"--"+serviceResult;
        //关闭界面Loading
        mView.setResultString(result);
    }


}
