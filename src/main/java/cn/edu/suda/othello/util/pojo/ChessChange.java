package cn.edu.suda.othello.util.pojo;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/20 9:35.
 * 棋子变化状态对象
 */
public class ChessChange {
    private int changeCount;    // 变化数目
    private boolean isChanged;  // 是否变化

    public ChessChange() {
    }

    public ChessChange(int changeCount, boolean isChanged) {
        this.changeCount = changeCount;
        this.isChanged = isChanged;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
