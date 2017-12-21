package cn.edu.suda.othello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 9:46.
 */
public class LoginPanel extends JPanel {
    private JLabel gameNameLabel; // 游戏名称标签
    private JRadioButton radioButton1; // 单人模式单选
    private JRadioButton radioButton2; // 双人模式单选
    private JRadioButton radioButton3; // 联机模式单选
    private NameAndIpPanel nameAndIpPanel; // 昵称及IP面板
    private JButton startButton; // 开始按钮

    private static final Logger logger = LoggerFactory.getLogger(LoginPanel.class);

    /**
     * 构造方法
     */
    public LoginPanel(NameAndIpPanel nameAndIpPanel) {
        this.nameAndIpPanel = nameAndIpPanel;
        initComponents();
    }

    /**
     * 初始化登录界面方法
     */
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        // 设置布局
        this.setLayout(new GridBagLayout());
        // 游戏标题定义及布局
        gameNameLabel = new JLabel("黑白棋");
        gameNameLabel.setFont(new Font("隶书", Font.ITALIC, 50));
        gameNameLabel.setForeground(Color.BLACK);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(gameNameLabel, gridBagConstraints);
        // 单人模式 选项定义及布局
        Font font = new Font("宋体", Font.BOLD, 30);
        radioButton1 = new JRadioButton("单人模式");
        radioButton1.setFont(font);
        radioButton1.setForeground(Color.BLACK);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(radioButton1, gridBagConstraints);
        // 双人模式 选项定义及布局
        radioButton2 = new JRadioButton("双人模式");
        radioButton2.setFont(font);
        radioButton2.setForeground(Color.BLACK);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(radioButton2, gridBagConstraints);
        // 联机模式 选项定义及布局
        radioButton3 = new JRadioButton("联机模式");
        radioButton3.setFont(font);
        radioButton3.setForeground(Color.BLACK);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(radioButton3, gridBagConstraints);
        // 将3个单选按钮添加到按钮组里（设置单选）
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        // 设置 昵称—IP面板不可见
        nameAndIpPanel.setVisible(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(nameAndIpPanel, gridBagConstraints);

        // 添加单选的动作监听器
        radioButton1.addActionListener(e -> {
            GameParameter.setType(1);
            nameAndIpPanel.setVisible(false);
            logger.info("选择单人模式，type = {}", GameParameter.type);
        });
        radioButton2.addActionListener(e -> {
            GameParameter.setType(2);
            nameAndIpPanel.setVisible(false);
            logger.info("选择双人模式，type = {}", GameParameter.type);
        });
        radioButton3.addActionListener(e -> {
            GameParameter.setType(3);
            nameAndIpPanel.setVisible(true);
            logger.info("选择联机模式，type = {}", GameParameter.type);
        });

        // 定义开始按钮
        startButton = new JButton("开始游戏");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(startButton, gridBagConstraints);
    }

    public void addStartButtonActionListener(ActionListener actionListener){
        startButton.addActionListener(actionListener);
    }
}
