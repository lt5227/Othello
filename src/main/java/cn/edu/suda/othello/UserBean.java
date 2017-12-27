package cn.edu.suda.othello;

import cn.edu.suda.othello.util.pojo.Coordinate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/27 9:53.
 */
@Component
public class UserBean implements Serializable {
    private Coordinate coordinate; // 用户点击坐标
    private String host; // 用户主机
    private String name; // 用户名称
    private int type; // 当前下棋状态
    private boolean isSure;

    public void update(UserBean userBean) {
        this.coordinate = userBean.getCoordinate();
        this.host = userBean.getHost();
        this.name = userBean.getName();
        this.type = userBean.getType();
        this.isSure = userBean.isSure();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSure() {
        return isSure;
    }

    public void setSure(boolean sure) {
        isSure = sure;
    }
}
