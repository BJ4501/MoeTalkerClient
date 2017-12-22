package net.bj.moetalker.factory.model;

import java.util.Date;

/**
 * 基础用户接口
 * Created by Neko-T4 on 2017/12/22.
 */
public interface Author {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getPortrait();

    void setPortrait(String portrait);

}
