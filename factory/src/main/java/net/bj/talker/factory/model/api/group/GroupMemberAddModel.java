package net.bj.talker.factory.model.api.group;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neko-T4 on 2018/1/14.
 */

public class GroupMemberAddModel {

    private Set<String> users = new HashSet<>();

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public static boolean check(GroupMemberAddModel model){
        return !(model.users == null || model.users.size() == 0);
    }
}
