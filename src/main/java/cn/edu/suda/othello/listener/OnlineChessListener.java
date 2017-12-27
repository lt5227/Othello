package cn.edu.suda.othello.listener;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.UserBean;
import cn.edu.suda.othello.util.GameUtil;
import cn.edu.suda.othello.util.SocketUtil;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/18 10:30.
 */
public class OnlineChessListener extends ChessListener {
    private static final Logger logger = LoggerFactory.getLogger(OnlineChessListener.class);
    private Socket socket;
    private ServerSocket serverSocket;
    private UserBean userBean;
    private SocketUtil socketUtil;
    private int count = 0;

    public OnlineChessListener(Graphics g, JLabel record, JLabel blackCountLabel, JLabel whiteCountLabel,
                               ChessPanel chess, Socket socket, ServerSocket serverSocket, UserBean userBean, SocketUtil socketUtil) {
        super(g, record, blackCountLabel, whiteCountLabel, chess);
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.userBean = userBean;
        this.serverSocket = serverSocket;
        this.socketUtil = socketUtil;
        this.socketUtil.setSocket(socket);
    }

    // 点击下棋棋子
    @Override
    public void mouseClicked(MouseEvent event) {
        // 获取鼠标点击的坐标
        x1 = event.getX();
        y1 = event.getY();
        Coordinate coordinate = GameUtil.getChessCoordinate(x1, y1);
        if (coordinate != null) {
            if (GameParameter.dismount[coordinate.getX()][coordinate.getY()] == 0) {
                JOptionPane.showMessageDialog(null, "不能放子");
            } else {
                if (GameParameter.isServer) {
                    // 服务端 服务端为白棋
                    if (ChessListener.state == -1) {
                        // 发送下棋信息
                        socketUtil.sendUserBean(coordinate);
                    } else {
                        JOptionPane.showMessageDialog(null, "请等待对方下棋~");
                        return;
                    }
                } else {
                    // 客户端 客户端为黑棋
                    if (ChessListener.state == 1) {
                        // 发送下棋信息
                        socketUtil.sendUserBean(coordinate);
                    } else {
                        JOptionPane.showMessageDialog(null, "请等待对方下棋~");
                        return;
                    }
                }
                // 检查游戏状态
                super.checkGameState(coordinate, GameParameter.chess, logger);
                // 设置下棋位置
                ChessPanel.setPosition(coordinate);
                chess.update(g);
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
