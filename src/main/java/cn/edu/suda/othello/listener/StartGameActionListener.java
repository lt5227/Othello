package cn.edu.suda.othello.listener;


import cn.edu.suda.OthelloApplication;
import cn.edu.suda.othello.*;
import cn.edu.suda.othello.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 14:23.
 */

public class StartGameActionListener implements ActionListener {
    @Autowired
    private UserBean userBean;

    private final LoginPanel loginPanel;
    private final ChessPanel chessPanel;

    private static Logger logger = LoggerFactory.getLogger(StartGameActionListener.class);

    @Autowired
    public StartGameActionListener(LoginPanel loginPanel, ChessPanel chessPanel) {
        this.loginPanel = loginPanel;
        this.chessPanel = chessPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (GameParameter.type) {
            case 1: {
                // 单人模式
                logger.info("单人模式游戏开始");
                loginPanel.setVisible(false); // 开始游戏隐藏登录面板
            }
            break;
            case 2: {
                // 双人模式
                logger.info("双人模式游戏开始");
                loginPanel.setVisible(false); // 开始游戏隐藏登录面板
            }
            break;
            case 3: {
                // 联机模式
                logger.info("联机模式游戏开始");
                // 昵称和IP输入面板
                NameAndIpPanel nameAndIpPanel = loginPanel.getNameAndIpPanel();
                // 获取昵称输入框和IP输入框
                JTextField nameTextField = nameAndIpPanel.getNameTextField();
                JTextField ipTextField = nameAndIpPanel.getIpTextField();
                String name = nameTextField.getText();
                String ip = ipTextField.getText();
                // 判断昵称和IP是否输入
                if (name == null || "".equals(name)) {
                    JOptionPane.showMessageDialog(loginPanel, "请输入昵称");
                    return;
                }
                if (ip == null || "".equals(ip)) {
                    JOptionPane.showMessageDialog(loginPanel, "请输入对家IP地址");
                    return;
                }
                // 判断对家IP是否输入正确 https://www.cnblogs.com/helloshrek/p/6018902.html
                String reg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."

                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
                if (!ip.matches(reg)) {
                    JOptionPane.showMessageDialog(loginPanel, "输入的IP地址不正确，请重新输入！");
                    return;
                }
                // 输入昵称和对方IP
                logger.info("昵称：{},对方IP：{}", name, ip);
                // 获取主窗体实例对象
                OthelloApplication othelloApplication = (OthelloApplication) loginPanel.getParent().getParent();
                // 创建Socket连接对家主机
                try {
                    Socket socket = new Socket(ip, 9530);
                    if (socket.isConnected()) {
                        // 如果连接成功，接收UserBean信息
                        chessPanel.setSocket(socket);
                        loginPanel.setVisible(false); // 开始游戏隐藏登录面板
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(loginPanel, "对方主机无法连接");
                }
            }
            break;
            default: {
                JOptionPane.showMessageDialog(loginPanel, "请选择游戏模式");
            }
        }
        // 添加棋盘鼠标监听
        chessPanel.addChessListen();
    }
}
