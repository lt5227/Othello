package cn.edu.suda.othello;

import cn.edu.suda.othello.listener.ChessListener;
import cn.edu.suda.othello.listener.OnlineChessListener;
import cn.edu.suda.othello.util.SocketUtil;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/25 13:37.
 */
public class ReceiveThread extends Thread {
    private final ServerSocket serverSocket;
    private final LoginPanel loginPanel;
    private final ChessPanel chessPanel;

    private static final Logger logger = LoggerFactory.getLogger(ReceiveThread.class);

    public ReceiveThread(ServerSocket serverSocket, LoginPanel loginPanel, ChessPanel chessPanel) {
        this.serverSocket = serverSocket;
        this.loginPanel = loginPanel;
        this.chessPanel = chessPanel;
    }

    @Override
    public void run() {
        logger.info("后台监听服务线程启动");
        OnlineChessListener chessListener;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    // 获取对方主机信息
                    String host = socket.getInetAddress().getHostName();
                    // 获取对方IP地址
                    String ip = serverSocket.getInetAddress().getHostAddress();
                    // 询问是否接受联机
                    int link = JOptionPane.showConfirmDialog(loginPanel, "收到" + host + "的联机请求，是否接受？");
                    if (link == JOptionPane.YES_OPTION) {
                        // 如果接受联机
                        GameParameter.setType(3); // 设置游戏模式为联机模式
                        loginPanel.setVisible(false);
                        GameParameter.setIsServer(true); // 设置游戏服务端
                        chessPanel.setServerSocket(serverSocket);
                        chessPanel.setSocket(socket);
                        ChessListener.setState(-1); // 设置游戏棋子状态
                        chessPanel.addChessListen(); // 添加棋盘监听程序
                        MouseListener[] mouseListeners = chessPanel.getMouseListeners();
                        chessListener = (OnlineChessListener) mouseListeners[0];
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            // 接收下棋状态
            SocketUtil socketUtil = chessPanel.getSocketUtil();
            Coordinate coordinate = socketUtil.receiveUserBean();
            if (coordinate != null) {
                // 更新下棋状态
                chessListener.checkGameState(coordinate,GameParameter.chess, logger);
                // 设置下棋位置
                ChessPanel.setPosition(coordinate);
                chessListener.getChess().update(chessListener.getG());
            }
        }
    }
}
