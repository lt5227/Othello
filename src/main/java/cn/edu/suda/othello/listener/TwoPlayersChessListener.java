package cn.edu.suda.othello.listener;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.util.GameUtil;
import cn.edu.suda.othello.util.pojo.ChessChange;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/18 10:30.
 */
public class TwoPlayersChessListener extends ChessListener {
    private static final Logger logger = LoggerFactory.getLogger(TwoPlayersChessListener.class);

    public TwoPlayersChessListener(Graphics g, JLabel record, JLabel blackCountLabel, JLabel whiteCountLabel, ChessPanel chess) {
        super(g, record, blackCountLabel, whiteCountLabel, chess);
    }

    // 点击下棋棋子
    public void mouseClicked(MouseEvent event) {
        // 获取鼠标点击的坐标
        x1 = event.getX();
        y1 = event.getY();
        Coordinate coordinate = GameUtil.getChessCoordinate(x1, y1);
        if (coordinate != null) {
            int row = coordinate.getX();
            int col = coordinate.getY();
            if (GameParameter.chess[row][col] == 3 || GameParameter.chess[row][col] == 0) {
                // 将棋子存入棋盘
                GameParameter.chess[row][col] = state;
                // 更改棋子状态
                state = -state;
                // 检查并替换棋子颜色
                ChessChange chessChange = GameUtil.getChessChange(row, col);
                boolean isChanged = chessChange.isChanged();
                if (isChanged) {
                    // 获取改变棋子的数目
                    int change = chessChange.getChangeCount();
                    // 统计黑子与白子的总数
                    int[] blackAndWhiteSum = GameUtil.judge();
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
                    List<Coordinate> dismountList = GameUtil.check(state);
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
                    boolean isFull = GameUtil.full(this);
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
                        dismountList = GameUtil.check(state);
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
                    GameParameter.chess[row][col] = 0;
                    state = -state;
                    // 刷新棋盘
                    chess.update(g);
                    JOptionPane.showMessageDialog(null, "不能放子");
                }
            }
        }
    }
}
