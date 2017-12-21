package cn.edu.suda.othello;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 11:49.
 */
public class NameAndIpPanel extends JPanel {
    private JTextField nameTextField = new JTextField();
    private JTextField ipTextField = new JTextField();

    public NameAndIpPanel() {
        this.setLayout(new GridLayout(2, 2, 4, 4));

        JLabel jLabel1 = new JLabel("昵   称：");
        jLabel1.setFont(new Font("宋体", Font.ITALIC, 24));
        jLabel1.setForeground(Color.BLACK);
        this.add(jLabel1);
        this.add(nameTextField);

        JLabel jLabel2 = new JLabel("对方 IP：");
        jLabel2.setFont(new Font("宋体", Font.ITALIC, 24));
        jLabel2.setForeground(Color.BLACK);
        this.add(jLabel2);
        this.add(ipTextField);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getIpTextField() {
        return ipTextField;
    }
}
