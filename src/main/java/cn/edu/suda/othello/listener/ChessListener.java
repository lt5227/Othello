package cn.edu.suda.othello.listener;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.util.GameUtil;
import cn.edu.suda.othello.util.pojo.ChessChange;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/15 16:51.
 */
public abstract class ChessListener extends MouseAdapter {
    protected Graphics g;             // 将画布对象传过来
    protected int dismount;           // 记录当前棋子的下法
    protected static int state = 1;    // 判断下黑棋还是白棋（黑棋阿狸 1，白棋桃子 -1）
    protected int x1, y1;             // 记录点击处的横坐标和纵坐标
    protected JLabel record;          // 当前下棋子的人
    protected JLabel blackCountLabel; // 阿狸棋子数
    protected JLabel whiteCountLabel; // 桃子棋子数
    protected ChessPanel chess;       // 用来刷新棋盘
    protected int te = 0;             // 特殊情况
    protected int change;             // 记录改变的棋子数目

    public ChessListener(Graphics g, JLabel record, JLabel blackCountLabel, JLabel whiteCountLabel, ChessPanel chess) {
        this.g = g;
        this.record = record;
        this.blackCountLabel = blackCountLabel;
        this.whiteCountLabel = whiteCountLabel;
        this.chess = chess;
    }

    /**
     * 检查游戏状态
     *
     * @param x        棋盘横坐标
     * @param y        棋盘纵坐标
     * @param chessArr 棋盘数组
     * @param logger   日志记录对象
     * @return 下法坐标集合
     */
    public synchronized List<Coordinate> checkGameState(Coordinate coordinate, int[][] chessArr, Logger logger) {
        // 下法坐标集合
        List<Coordinate> dismountList = null;
        if (coordinate != null) {
            int row = coordinate.getX();
            int col = coordinate.getY();
            if (chessArr[row][col] == 3 || chessArr[row][col] == 0) {
                // 将棋子存入棋盘
                chessArr[row][col] = state;
                // 更改棋子状态
                state = -state;
                // 检查并替换棋子颜色
                ChessChange chessChange = GameUtil.getChessChange(row, col, chessArr);
                boolean isChanged = chessChange.isChanged();
                if (isChanged) {
                    // 获取改变棋子的数目
                    int change = chessChange.getChangeCount();
                    // 统计黑子与白子的总数
                    int[] blackAndWhiteSum = GameUtil.judge(chessArr);
                    // 改变棋子记录框里面的数字
                    blackCountLabel.setText(blackAndWhiteSum[0] + "");
                    whiteCountLabel.setText(blackAndWhiteSum[1] + "");
                    logger.info("改变了{}个棋子", change);
                    // 判断当前下棋的人
                    String s = null;
                    if (state == 1) {
                        record.setText(" 黑棋下（阿狸）");
                        s = "黑棋（阿狸）";
                    } else if (state == -1) {
                        record.setText(" 白棋下（桃子）");
                        s = "白棋（桃子）";
                    }
                    // 清空下法数组
                    GameParameter.clearDismount();
                    // 获取当前下棋的人还有几种下法
                    dismountList = GameUtil.check(state, chessArr);
                    int dismountCount = dismountList.size();
                    if (dismountCount == 0) {
                        logger.info("{}无子可下", s);
                    } else {
                        logger.info("{}有{}种下法", s, dismountCount);
                        // 更新下法数组
                        dismountList.forEach(c -> {
                            int i = c.getX();
                            int j = c.getY();
                            GameParameter.dismount[i][j] = 3;
                        });
                    }
                    // 刷新棋盘
                    chess.update(g);
                    boolean isFull = GameUtil.full(this, chessArr);
                    if (dismountCount == 0 && !isFull) {
                        // 如果不能下子并且棋盘未满
                        te++;
                        JOptionPane.showMessageDialog(null, "不能下子，跳过！");
                        if (state == 1) {
                            // 如果黑棋（阿狸）不能下子则跳过，白棋（桃子）下
                            state = -1;
                            record.setText(" 白棋下（桃子）");
                            s = "白棋（桃子）";
                        } else if (state == -1) {
                            // 如果白棋（桃子）不能下子则跳过，黑棋（阿狸）下
                            state = 1;
                            record.setText(" 黑棋下（阿狸）");
                            s = "黑棋（阿狸）";
                        }
                        // 重新检查下法
                        dismountList = GameUtil.check(state, chessArr);
                        dismountCount = dismountList.size();
                        if (dismountCount == 0) {
                            // 无子可下，特殊情况再加1
                            te++;
                        } else {
                            te = 0;
                            logger.info("{}有{}种下法", s, dismountCount);
                            // 更新下法数组
                            dismountList.forEach(c -> {
                                int i = c.getX();
                                int j = c.getY();
                                GameParameter.dismount[i][j] = 3;
                            });
                            // 刷新棋盘
                            chess.update(g);
                        }
                    }
                    // 判断胜负
                    if (blackAndWhiteSum[0] == 0) {
                        // 如果黑棋（阿狸）没子了，则白棋（桃子）获胜
                        JOptionPane.showMessageDialog(null, "游戏结束，白棋（桃子）获胜");
                    } else if (blackAndWhiteSum[1] == 0) {
                        // 如果白棋（桃子）没子了，则黑棋（阿狸）获胜
                        JOptionPane.showMessageDialog(null, "游戏结束，黑棋（阿狸）获胜");
                    }
                    if (isFull) {
                        if (blackAndWhiteSum[0] > blackAndWhiteSum[1]) {
                            // 如果黑棋（阿狸）子较多，则黑棋（阿狸）获胜
                            JOptionPane.showMessageDialog(null, "游戏结束，黑棋（阿狸）获胜");
                        } else if (blackAndWhiteSum[0] < blackAndWhiteSum[1]) {
                            // 如果白棋（桃子）子较多，则白棋（桃子）获胜
                            JOptionPane.showMessageDialog(null, "游戏结束，白棋（桃子）获胜");
                        } else if (blackAndWhiteSum[0] == blackAndWhiteSum[1]) {
                            JOptionPane.showMessageDialog(null, "平局");
                        }
                    }
                } else {
                    // 棋子无改变，恢复棋盘棋子以及棋子状态
                    chessArr[row][col] = 0;
                    state = -state;
                    // 刷新棋盘
                    chess.update(g);
                    JOptionPane.showMessageDialog(null, "不能放子");
                }
            }
        }
        return dismountList;
    }

    public Graphics getG() {
        return g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public int getDismount() {
        return dismount;
    }

    public void setDismount(int dismount) {
        this.dismount = dismount;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        ChessListener.state = state;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public JLabel getRecord() {
        return record;
    }

    public void setRecord(JLabel record) {
        this.record = record;
    }

    public JLabel getBlackCountLabel() {
        return blackCountLabel;
    }

    public void setBlackCountLabel(JLabel blackCountLabel) {
        this.blackCountLabel = blackCountLabel;
    }

    public JLabel getWhiteCountLabel() {
        return whiteCountLabel;
    }

    public void setWhiteCountLabel(JLabel whiteCountLabel) {
        this.whiteCountLabel = whiteCountLabel;
    }

    public ChessPanel getChess() {
        return chess;
    }

    public void setChess(ChessPanel chess) {
        this.chess = chess;
    }

    public int getTe() {
        return te;
    }

    public void setTe(int te) {
        this.te = te;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}
