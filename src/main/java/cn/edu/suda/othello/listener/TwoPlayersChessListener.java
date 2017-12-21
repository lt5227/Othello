package cn.edu.suda.othello.listener;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.util.GameUtil;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

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
    @Override
    public void mouseClicked(MouseEvent event) {
        // 获取鼠标点击的坐标
        x1 = event.getX();
        y1 = event.getY();
        Coordinate coordinate = GameUtil.getChessCoordinate(x1, y1);
        // 检查游戏状态
        super.checkGameState(coordinate, GameParameter.chess, logger);
        // 设置下棋位置
        ChessPanel.setPosition(coordinate);
        chess.update(g);
    }

}
