package cn.edu.suda.othello;


import java.util.Arrays;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 11:29.
 * 游戏参数
 */
public class GameParameter {
    /*
     * 游戏类型：
     * 1：单人模式
     * 2：双人模式
     * 3：联机模式
     */
    public static int type = 0;


    public final static int rows = 9; // 棋盘行数
    public final static int cols = 9; // 棋盘列数
    public final static int[][] chess = new int[rows - 1][cols - 1]; // 定义一个8*8的数组，用来保存棋子
    public final static int[][] dismount = new int[rows - 1][cols - 1];  // 定义一个8*8的数组，用来保存下法

    public static void setType(int type) {
        GameParameter.type = type;
    }

    public static void clearDismount() {
        for (int[] d : dismount){
            Arrays.fill(d, 0);
        }
    }
}
