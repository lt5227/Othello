package cn.edu.suda.othello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/25 11:46.
 */
public class GameServer {
    private LoginPanel loginPanel;
    private ChessPanel chessPanel;

    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);

    public GameServer(LoginPanel loginPanel, ChessPanel chessPanel) {
        this.loginPanel = loginPanel;
        this.chessPanel = chessPanel;
        // 启动服务监听程序
        this.startServer();
    }

    private void startServer() {
        /*
         * 创建ServerSocket的同时需要申请服务端口
		 * 这个端口不能与其他使用TCP协议的应用程序
		 * 冲突，否则会抛出异常。
		 */
        try {
            final ServerSocket serverSocket = new ServerSocket(9527);
            logger.info("启动服务监听，监听端口9527");
            // 创建接收信息的线程
            new ReceiveThread(serverSocket, loginPanel, chessPanel).start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginPanel, "本程序禁止重复运行，只能同时存在一个实例。"
                    , "你敢重复运行？", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
