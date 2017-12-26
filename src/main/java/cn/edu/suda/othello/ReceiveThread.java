package cn.edu.suda.othello;

import java.net.ServerSocket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/25 13:37.
 */
public class ReceiveThread extends Thread {
    private final ServerSocket serverSocket;
    private final LoginPanel loginPanel;
    private final ChessPanel chessPanel;

    public ReceiveThread(ServerSocket serverSocket, LoginPanel loginPanel, ChessPanel chessPanel) {
        this.serverSocket = serverSocket;
        this.loginPanel = loginPanel;
        this.chessPanel = chessPanel;
    }
}
