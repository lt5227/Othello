package cn.edu.suda.othello.listener;

import cn.edu.suda.othello.ChessPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/15 16:51.
 */
public abstract class ChessListener extends MouseAdapter {
    protected Graphics g;             // 将画布对象传过来
    protected int dismount;           // 记录当前棋子的下法
    protected static int state = 1;    // 判断下黑棋还是白棋（黑棋阿狸 1，白棋桃子 -1）
    protected int x1, y1;             // 记录点击处的横坐标和纵坐标
    protected JLabel record;          // 当前下棋子的人
    protected JLabel blackCountLabel; // 阿狸棋子数
    protected JLabel whiteCountLabel; // 桃子棋子数
    protected ChessPanel chess;       // 用来刷新棋盘
    protected int te = 0;             // 特殊情况
    protected int change;             // 记录改变的棋子数目

    public ChessListener(Graphics g, JLabel record, JLabel blackCountLabel, JLabel whiteCountLabel, ChessPanel chess) {
        this.g = g;
        this.record = record;
        this.blackCountLabel = blackCountLabel;
        this.whiteCountLabel = whiteCountLabel;
        this.chess = chess;
    }

    public Graphics getG() {
        return g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public int getDismount() {
        return dismount;
    }

    public void setDismount(int dismount) {
        this.dismount = dismount;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        ChessListener.state = state;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public JLabel getRecord() {
        return record;
    }

    public void setRecord(JLabel record) {
        this.record = record;
    }

    public JLabel getBlackCountLabel() {
        return blackCountLabel;
    }

    public void setBlackCountLabel(JLabel blackCountLabel) {
        this.blackCountLabel = blackCountLabel;
    }

    public JLabel getWhiteCountLabel() {
        return whiteCountLabel;
    }

    public void setWhiteCountLabel(JLabel whiteCountLabel) {
        this.whiteCountLabel = whiteCountLabel;
    }

    public ChessPanel getChess() {
        return chess;
    }

    public void setChess(ChessPanel chess) {
        this.chess = chess;
    }

    public int getTe() {
        return te;
    }

    public void setTe(int te) {
        this.te = te;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}
