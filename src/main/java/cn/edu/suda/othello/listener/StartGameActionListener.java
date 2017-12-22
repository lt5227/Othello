package cn.edu.suda.othello.listener;


import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.LoginPanel;
import cn.edu.suda.othello.NameAndIpPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 14:23.
 */

public class StartGameActionListener implements ActionListener {
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
                System.out.println(name);
                System.out.println(ip);

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
