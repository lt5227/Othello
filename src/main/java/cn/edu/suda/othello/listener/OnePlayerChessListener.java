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
 * Created by LiLei on 2017/12/21 14:42.
 */
public class OnePlayerChessListener extends ChessListener {

    private static final Logger logger = LoggerFactory.getLogger(OnePlayerChessListener.class);

    public OnePlayerChessListener(Graphics g, JLabel record, JLabel blackCountLabel, JLabel whiteCountLabel, ChessPanel chess) {
        super(g, record, blackCountLabel, whiteCountLabel, chess);
    }

    // 点击下棋棋子
    @Override
    public void mouseClicked(MouseEvent event) {
        try {
            // 获取鼠标点击的坐标
            x1 = event.getX();
            y1 = event.getY();
            Coordinate coordinate = GameUtil.getChessCoordinate(x1, y1);
            // 检查游戏状态获取下子方法
            List<Coordinate> coordinateList = super.checkGameState(coordinate, GameParameter.chess, logger);
            // 延迟0.3秒
            Thread.sleep(300);
            if (coordinateList != null && coordinateList.size() > 0) {
                Random random = new Random();
                int index = coordinateList.size() - 1;
                if (index > 0) {
                    index = random.nextInt(index);
                }
                coordinate = coordinateList.get(index);
                int x = coordinate.getX() + 56 / 2 + 56 * coordinate.getX();
                int y = coordinate.getY() + 56 / 2 + 56 * coordinate.getY();
                Graphics2D g2D = (Graphics2D) g;
                g2D.setColor(Color.BLUE);
                g2D.setStroke(new BasicStroke(3.0f));
                g.drawRect(x - 56 / 2, y - 56 / 2, 56, 56);
                super.checkGameState(coordinate, GameParameter.chess, logger);
                ChessPanel.setPosition(coordinate);
                chess.update(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
