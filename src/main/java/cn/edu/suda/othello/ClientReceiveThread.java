package cn.edu.suda.othello;

import cn.edu.suda.othello.listener.OnlineChessListener;
import cn.edu.suda.othello.util.SocketUtil;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseListener;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/27 15:12.
 */
public class ClientReceiveThread implements Runnable {

    private final Socket socket;
    private final LoginPanel loginPanel;
    private final ChessPanel chessPanel;

    private static final Logger logger = LoggerFactory.getLogger(ClientReceiveThread.class);

    public ClientReceiveThread(Socket socket, LoginPanel loginPanel, ChessPanel chessPanel) {
        this.socket = socket;
        this.loginPanel = loginPanel;
        this.chessPanel = chessPanel;
    }

    @Override
    public void run() {
        logger.info("后台监听服务线程启动");
        OnlineChessListener chessListener;
        MouseListener[] mouseListeners = chessPanel.getMouseListeners();
        chessListener = (OnlineChessListener) mouseListeners[0];
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
