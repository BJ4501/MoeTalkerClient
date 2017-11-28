package net.bj.moetalker.push;

/**
 * Created by Neko-T4 on 2017/11/28.
 */

public class UserService implements IUserService {
    @Override
    public String search(int hashCode){
        return "User:"+hashCode;
    }
}
