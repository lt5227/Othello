package cn.edu.suda.othello.util;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameParameter;
import cn.edu.suda.othello.listener.ChessListener;
import cn.edu.suda.othello.util.pojo.ChessChange;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/18 10:49.
 * 游戏工具类
 */
public class GameUtil {

    private static final Logger logger = LoggerFactory.getLogger(GameUtil.class);

    /**
     * 判断输赢的方法，如果黑棋多则黑棋获胜，否则白棋获胜
     *
     * @return 黑棋和白棋的数目 数组0：黑棋数目；数组1：白棋数目
     */
    public static int[] judge(int[][] chessArr) {
        int count[] = new int[2];
        for (int i = 0; i < GameParameter.rows - 1; i++) {
            for (int j = 0; j < GameParameter.cols - 1; j++) {
                if (chessArr[i][j] == 1) {
                    // 统计黑棋数目
                    count[0]++;
                } else if (chessArr[i][j] == -1) {
                    // 统计白棋数目
                    count[1]++;
                }
            }
        }
        return count;
    }


    /**
     * 获取下棋的方法
     *
     * @param state 要检查的棋的颜色（黑棋：1，白棋：-1）
     * @return 棋盘坐标集合
     */
    public static List<Coordinate> check(int state, int[][] chessArr) {
        List<Coordinate> list = new ArrayList<>();
        for (int i = 0; i < GameParameter.rows - 1; i++) {
            for (int j = 0; j < GameParameter.cols - 1; j++) {
                if (chessArr[i][j] == 0) {
                    // 设置当前棋盘位置棋子颜色
                    chessArr[i][j] = state;
                    if (checkTheRight(i, j, chessArr) != null
                            || checkTheLeft(i, j, chessArr) != null
                            || checkTheUp(i, j, chessArr) != null
                            || checkTheDown(i, j, chessArr) != null
                            || checkTheBottomLeft(i, j, chessArr) != null
                            || checkTheBottomRight(i, j, chessArr) != null
                            || checkTheUpperLeft(i, j, chessArr) != null
                            || checkTheUpperRight(i, j, chessArr) != null) {
                        // 当前位置可以下子
                        Coordinate coordinate = new Coordinate(i, j);
                        list.add(coordinate);
                    }
                    // 恢复当前位置状态
                    GameParameter.chess[i][j] = 0;
                }
            }
        }
        return list;
    }

    /**
     * 判断棋子是否已满的方法
     */
    public static boolean full(ChessListener chessListener, int[][] chessArr) {
        int te = chessListener.getTe();
        if (te == 2) {
            // 如果双方都不能下子，则游戏结束
            return true;
        } else {
            for (int i = 0; i < GameParameter.rows - 1; i++) {
                for (int j = 0; j < GameParameter.cols - 1; j++) {
                    // 如果有一个地方是空的则返回false
                    if (chessArr[i][j] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 向右检测是否有相同颜色的棋子，如果有且不相邻
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向右终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheRight(int x, int y, int[][] chessArr) {
        try {
            int r = -2;
            int i;
            // 向右检测
            for (i = x + 1; i < GameParameter.rows - 1; i++) {
                if (chessArr[i][y] != 1 && chessArr[i][y] != -1) {
                    break;
                }
                if (chessArr[i][y] == chessArr[x][y]) {
                    r = i;
                    break;
                }
            }
            if (r != -2 && chessArr[x + 1][y] != chessArr[i][y]) {
                return new int[]{r, y};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向左检测是否有相同颜色的棋子，如果有且不相邻
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向左终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheLeft(int x, int y, int[][] chessArr) {
        try {
            int r = -2;
            int i;
            // 向左检测
            for (i = x - 1; i >= 0; i--) {
                if (chessArr[i][y] != 1 && chessArr[i][y] != -1) {
                    break;
                }
                if (chessArr[i][y] == chessArr[x][y]) {
                    r = i;
                    break;
                }
            }
            if (r != -2 && chessArr[x - 1][y] != chessArr[i][y]) {
                return new int[]{r, y};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向上检测是否有相同颜色棋子，如果有且不相邻
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向上终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheUp(int x, int y, int[][] chessArr) {
        try {
            int r = -2;
            int i;
            // 向上检测
            for (i = y - 1; i >= 0; i--) {
                if (chessArr[x][i] == 0) {
                    break;
                }
                if (chessArr[x][i] == chessArr[x][y]) {
                    r = i;
                    break;
                }
            }
            if (r != -2 && chessArr[x][y - 1] != chessArr[x][i]) {
                return new int[]{x, r};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向下检测是否有相同颜色棋子，如果有且不相邻
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向下终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheDown(int x, int y, int[][] chessArr) {
        try {
            int r = -2;
            int i;
            // 向下检测
            for (i = y + 1; i < GameParameter.rows - 1; i++) {
                if (chessArr[x][i] == 0) {
                    break;
                }
                if (chessArr[x][i] == chessArr[x][y]) {
                    r = i;
                    break;
                }
            }
            if (r != -2 && chessArr[x][y + 1] != chessArr[x][i]) {
                return new int[]{x, r};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向右上方检测是否有相同颜色的棋子
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向右上终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheUpperRight(int x, int y, int[][] chessArr) {
        try {
            int r = -2, s = -2;
            int i, j;
            for (i = x + 1, j = y - 1; i < GameParameter.rows - 1 && j >= 0; i++, j--) {
                if (chessArr[i][j] == 0) {
                    break;
                }
                if (chessArr[i][j] == chessArr[x][y]) {
                    r = i;
                    s = j;
                    break;
                }
            }
            if (r != -2 && s != -2 && chessArr[x + 1][y - 1] != chessArr[i][j]) {
                return new int[]{r, s};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向右下方检测是否有相同颜色的棋子
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向右下终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheBottomRight(int x, int y, int[][] chessArr) {
        try {
            int r = -2, s = -2;
            int i, j;
            for (i = x + 1, j = y + 1; i < GameParameter.rows - 1 && j < GameParameter.cols - 1; i++, j++) {
                if (chessArr[i][j] == 0) {
                    break;
                }
                if (chessArr[i][j] == chessArr[x][y]) {
                    r = i;
                    s = j;
                }
            }
            if (r != -2 && s != -2 && chessArr[x + 1][y + 1] != chessArr[i][j]) {
                return new int[]{r, s};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向左上方检测是否有相同颜色的棋子
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向左上终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheUpperLeft(int x, int y, int[][] chessArr) {
        try {
            int r = -2, s = -2;
            int i, j;
            for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                if (chessArr[i][j] == 0) {
                    break;
                }
                if (chessArr[i][j] == chessArr[x][y]) {
                    r = i;
                    s = j;
                    break;
                }
            }
            if (r != -2 && s != -2 && chessArr[x - 1][y - 1] != chessArr[i][j]) {
                return new int[]{r, s};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向左下方检测是否有相同颜色的棋子
     *
     * @param x x坐标
     * @param y y坐标
     * @return 向左下终点的行列坐标，如果没有则返回null
     */
    public static int[] checkTheBottomLeft(int x, int y, int[][] chessArr) {
        try {
            int r = -2, s = -2;
            int i, j;
            for (i = x - 1, j = y + 1; i >= 0 && j < GameParameter.cols - 1; i--, j++) {
                if (chessArr[i][j] == 0) {
                    break;
                }
                if (chessArr[i][j] == chessArr[x][y]) {
                    r = i;
                    s = j;
                    break;
                }
            }
            if (r != -2 && s != -2 && chessArr[x - 1][y + 1] != chessArr[i][j]) {
                return new int[]{r, s};
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 改变两棋子之间的棋子的颜色——直线
     *
     * @param r1 开始横坐标
     * @param c1 开始纵坐标
     * @param r2 结束横坐标
     * @param c2 结束纵坐标
     * @return 改变的个数
     */
    public static int paintChessLine(int r1, int c1, int r2, int c2, int[][] chessArr) {
        int change = 0;
        // 横向
        if (c1 == c2) {
            for (int k = Math.min(r1, r2) + 1; k < Math.max(r1, r2); k++) {
                chessArr[k][c1] = chessArr[r1][c1];
                change++;
            }
        }
        // 纵向
        if (r1 == r2) {
            for (int k = Math.min(c1, c2) + 1; k < Math.max(c1, c2); k++) {
                chessArr[r1][k] = chessArr[r1][c1];
                change++;
            }
        }
        return change;
    }

    /**
     * 改变两棋子之间的棋子的颜色——斜线（右上）
     *
     * @param r1 开始横坐标
     * @param c1 开始纵坐标
     * @param r2 结束横坐标
     * @param c2 结束纵坐标
     * @return 改变的个数
     */
    public static int paintChessUpperRight(int r1, int c1, int r2, int c2, int[][] chessArr) {
        int change = 0;
        for (int k = Math.min(r1, r2) + 1, v = Math.max(c1, c2) - 1; k < Math.max(r1, r2); k++, v--) {
            chessArr[k][v] = chessArr[r1][c1];
            change++;
        }
        return change;
    }

    /**
     * 改变两棋子之间的棋子的颜色——斜线（右下）
     *
     * @param r1 开始横坐标
     * @param c1 开始纵坐标
     * @param r2 结束横坐标
     * @param c2 结束纵坐标
     * @return 改变的个数
     */
    public static int paintChessBottomRight(int r1, int c1, int r2, int c2, int[][] chessArr) {
        int change = 0;
        for (int k = Math.min(r1, r2) + 1, v = Math.min(c1, c2) + 1; k < Math.max(r1, r2); k++, v++) {
            chessArr[k][v] = chessArr[r1][c1];
            change++;
        }
        return change;
    }

    /**
     * 改变两棋子之间的棋子的颜色——斜线（左上）
     *
     * @param r1 开始横坐标
     * @param c1 开始纵坐标
     * @param r2 结束横坐标
     * @param c2 结束纵坐标
     * @return 改变的个数
     */
    public static int paintChessUpperLeft(int r1, int c1, int r2, int c2, int[][] chessArr) {
        int change = 0;
        for (int k = Math.max(r1, r2) - 1, v = Math.max(c1, c2) - 1; k > Math.min(r1, r2); k--, v--) {
            chessArr[k][v] = chessArr[r1][c1];
            change++;
        }
        return change;
    }

    /**
     * 改变两棋子之间的棋子的颜色——斜线（左下）
     *
     * @param r1 开始横坐标
     * @param c1 开始纵坐标
     * @param r2 结束横坐标
     * @param c2 结束纵坐标
     * @return 改变的个数
     */
    public static int paintChessBottomLeft(int r1, int c1, int r2, int c2, int[][] chessArr) {
        int change = 0;
        for (int k = Math.min(r1, r2) + 1, v = Math.max(c1, c2) - 1; k <= Math.max(r1, r2); k++, v--) {
            chessArr[k][v] = chessArr[r1][c1];
            change++;
        }
        return change;
    }

    /**
     * 获取棋子变化状态，如果有变化则改变棋盘状态
     *
     * @param row 棋子行坐标
     * @param col 棋子列坐标
     * @return 棋盘变化状态对象
     */
    public static ChessChange getChessChange(int row, int col, int[][] chessArr) {
        boolean isChangeState = false; // 判断是否改变棋子状态
        // 改变棋子数目
        int changeCount = 0;
        int[] arr = null;
        // 向上检测
        arr = checkTheUp(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessLine(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 向下检测
        arr = checkTheDown(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessLine(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 向左检测
        arr = checkTheLeft(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessLine(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 向右检测
        arr = checkTheRight(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessLine(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 左上检测
        arr = checkTheUpperLeft(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessUpperLeft(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 右上检测
        arr = checkTheUpperRight(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessUpperRight(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 左下检测
        arr = checkTheBottomLeft(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessBottomLeft(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        // 右下检测
        arr = checkTheBottomRight(row, col, chessArr);
        if (arr != null) {
            // 改变棋子颜色
            int count = paintChessBottomRight(row, col, arr[0], arr[1], chessArr);
            changeCount += count;
            isChangeState = true;
        }
        return new ChessChange(changeCount, isChangeState);
    }

    /**
     * 获取鼠标点击位置的棋盘坐标
     *
     * @param x 鼠标点击的X坐标
     * @param y 鼠标点击的Y坐标
     * @return 对应的棋盘坐标集合
     */
    public static Coordinate getChessCoordinate(int x, int y) {
        // 获取所下棋子属于第几行，第几列
        Integer row = null, col = null;
        // 获取列坐标
        for (int i = 1; i < GameParameter.rows; i++) {
            if (x >= ChessPanel.x + (i - 1) * ChessPanel.size && x <= ChessPanel.x + i * ChessPanel.size) {
                row = i - 1;
                break;
            }
        }
        for (int i = 1; i < GameParameter.cols; i++) {
            if (y >= ChessPanel.y + (i - 1) * ChessPanel.size && y <= ChessPanel.y + i * ChessPanel.size) {
                col = i - 1;
                break;
            }
        }
        if (row == null || col == null) {
            return null;
        } else {
            return new Coordinate(row, col);
        }
    }
}
