package cn.edu.suda.othello.util.pojo;

import java.io.Serializable;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/20 16:31.
 * 棋盘坐标对象
 */
public class Coordinate implements Serializable {
    private static final long serialVersionUID = 4896210274414787459L;
    private Integer x; // X坐标
    private Integer y; // Y坐标

    public Coordinate() {
    }

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
