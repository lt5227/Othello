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
    // 棋子位置估值表
    public final static int[][] valuationTable = {
            {90, -60, 10, 10, 10, 10, -60, 90},
            {-60, -80, 5, 5, 5, 5, -80, -60},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {90, -60, 10, 10, 10, 10, -60, 90},
            {-60, -80, 5, 5, 5, 5, -80, -60}
    };

    public static void setType(int type) {
        GameParameter.type = type;
    }

    public static void clearDismount() {
        for (int[] d : dismount) {
            Arrays.fill(d, 0);
        }
    }

    public static int[][] getValuationTable() {
        return valuationTable;
    }

    public static int[] getValuationScore(int[][] chessArr) {
        int[] result = new int[2];
        int blackScore = 0;
        int whiteScore = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] == 1) {
                    // 黑棋
                    blackScore += valuationTable[i][j];
                } else if (chessArr[i][j] == -1) {
                    // 白棋
                    whiteScore += valuationTable[i][j];
                }
            }
        }
        result[0] = blackScore;
        result[1] = whiteScore;
        return result;
    }
}
