package cn.edu.suda.othello;

import cn.edu.suda.othello.listener.ChessListener;
import cn.edu.suda.othello.listener.OnePlayerChessListener;
import cn.edu.suda.othello.listener.OnlineChessListener;
import cn.edu.suda.othello.listener.TwoPlayersChessListener;
import cn.edu.suda.othello.util.SocketUtil;
import cn.edu.suda.othello.util.pojo.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 14:42.
 * 棋盘面板
 */
public class ChessPanel extends JPanel {
    @Autowired
    private UserBean userBean;
    @Autowired
    private SocketUtil socketUtil;

    public final static int x = 79; // 棋盘初始点横坐标
    public final static int y = 83; // 棋盘初始点纵坐标
    public final static int size = 61;//棋盘格子大小
    private final static int chess_size = 56;//棋子大小

    private static final Image blackChess; // 黑棋 阿狸
    private static final Image whiteChess; // 白棋 桃子
    private static final Image dismountImage; // 下法
    private static final JLabel blackCountLabel = new JLabel("2"); // 黑棋个数
    private static final JLabel whiteCountLabel = new JLabel("2"); // 白棋个数
    private static final JLabel record = new JLabel("阿狸下"); // 当前下棋的人 默认黑棋先下
    private static Coordinate position;
    private Socket socket;
    private ServerSocket serverSocket;

    private static final Logger logger = LoggerFactory.getLogger(ChessPanel.class);

    static {
        blackChess = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/ali.jpg")).getImage();
        whiteChess = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/taozi.jpg")).getImage();
        dismountImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/x.png")).getImage();
    }

    public ChessPanel() {
        // 初始化棋子
        GameParameter.chess[3][3] = 1;
        GameParameter.chess[4][4] = 1;
        GameParameter.chess[3][4] = -1;
        GameParameter.chess[4][3] = -1;
        // 初始化下法
        GameParameter.dismount[2][4] = 3;
        GameParameter.dismount[4][2] = 3;
        GameParameter.dismount[5][3] = 3;
        GameParameter.dismount[3][5] = 3;
        // 组件界面
        JPanel componentPanel = new JPanel();
        // 游戏界面 如果不添加此面板 布局会错乱
        JPanel gamePanel = new JPanel();
        // 按钮图标
        ImageIcon ali_0 = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/阿狸.jpg"));
        ImageIcon ali_1 = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/阿狸1.jpg"));
        ImageIcon taoZi_0 = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/桃子.jpg"));
        ImageIcon taoZi_1 = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/桃子1.jpg"));
        // 定义按钮
        JButton aliButton = new JButton(ali_0);
        JButton taoZiButton = new JButton(taoZi_0);
        // 设置按钮翻转图标
        aliButton.setRolloverIcon(ali_1);
        taoZiButton.setRolloverIcon(taoZi_1);
        // 定义按钮尺寸
        Dimension dimension = new Dimension(100, 100);
        // 设置按钮首选大小
        aliButton.setPreferredSize(dimension);
        taoZiButton.setPreferredSize(dimension);
        // 设置字体
        Font recordFont = new Font("黑体", Font.BOLD, 30);
        record.setFont(recordFont);
        //
        Font font = new Font("宋体", Font.BOLD, 42);
        blackCountLabel.setFont(font);
        whiteCountLabel.setFont(font);
        // ---重新开局的方法---
        ImageIcon img = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/restart.jpg"));
        JButton button = new JButton(img);
        button.setPreferredSize(new Dimension(100, 40));
        // 将定义的组件添加到组件面板中
        componentPanel.add(record);
        componentPanel.add(aliButton);
        componentPanel.add(blackCountLabel);
        componentPanel.add(taoZiButton);
        componentPanel.add(whiteCountLabel);
        componentPanel.add(button);
        // 设置布局
        this.setLayout(new GridLayout(1, 2, 600, 0));
        this.add(gamePanel);
        this.add(componentPanel);
        // 如果点击黑棋则黑棋下 阿狸
        ActionListener blackActionListener = e -> {
            ChessListener.setState(1);
            record.setText("阿狸下（BLACK）");
        };
        // 如果点击白棋则白棋下 桃子
        ActionListener whiteActionListener = e -> {
            ChessListener.setState(-1);
            record.setText("桃子下（WHITE）");
        };
        // 添加事件
        aliButton.addActionListener(blackActionListener);
        taoZiButton.addActionListener(whiteActionListener);
    }

    /**
     * 添加鼠标监听
     */
    public void addChessListen() {
        // 获取画布权限
        Graphics g = this.getGraphics();
        ChessListener listener = null;
        switch (GameParameter.type) {
            case 1: {
                logger.info("实例化单人模式监听器");
                listener = new OnePlayerChessListener(g, record, blackCountLabel, whiteCountLabel, this);
            }
            break;
            case 2: {
                logger.info("实例化双人模式监听器");
                listener = new TwoPlayersChessListener(g, record, blackCountLabel, whiteCountLabel, this);
            }
            break;
            case 3: {
                logger.info("实例化联机模式监听器");
                listener = new OnlineChessListener(g, record, blackCountLabel, whiteCountLabel, this, socket, serverSocket, userBean, socketUtil);
            }
            break;
        }
        this.addMouseListener(listener);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 获取棋子数组
        int[][] chess = GameParameter.chess;
        int[][] dismount = GameParameter.dismount;
        // 关于ImageIcon路径问题 参考：http://blog.csdn.net/bonjean/article/details/38929727
        // 如果使用 ClassLoader.getSystemResource("filename"); 方法，打包程序后会报错
        Image back = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("image/棋盘.jpg")).getImage();
        g.drawImage(back, 10, 30, 800, 600, null);
//        // 绘制棋盘
//        // 画横线
//        for (int i = 0; i < GameParameter.rows; i++) {
//            g.setColor(Color.BLUE);
//            g.drawLine(x, y + i * size, x + size * (GameParameter.rows - 1), y + i * size);
//        }
//        // 画竖线
//        for (int j = 0; j < GameParameter.cols; j++) {
//            g.setColor(Color.BLUE);
//            g.drawLine(x + j * size, y, x + j * size, y + size * (GameParameter.cols - 1));
//        }
        // 绘制棋子
        for (int i = 0; i < GameParameter.rows - 1; i++) {
            for (int j = 0; j < GameParameter.cols - 1; j++) {
                int X = x + size / 2 + size * i;
                int Y = y + size / 2 + size * j;
                if (chess[i][j] == 1) {
                    // 画黑棋
                    g.drawImage(blackChess, X - chess_size / 2, Y - chess_size / 2, chess_size, chess_size, null);
                } else if (chess[i][j] == -1) {
                    // 画白棋
                    g.drawImage(whiteChess, X - chess_size / 2, Y - chess_size / 2, chess_size, chess_size, null);
                }
                if (dismount[i][j] == 3) {
                    // 画下法
                    g.setColor(Color.RED);
                    g.fillOval(X - chess_size / 2, Y - chess_size / 2, chess_size, chess_size);
//                    g.drawImage(dismountImage, X - chess_size / 2, Y - chess_size / 2, chess_size, chess_size, null);
                }
            }
        }

        if (position != null) {
            int i = position.getX();
            int j = position.getY();
            int X = x + size / 2 + size * i;
            int Y = y + size / 2 + size * j;
            Graphics2D g2D = (Graphics2D) g;
            g2D.setStroke(new BasicStroke(5.0f));
            g.setColor(Color.BLUE);
            g.drawRect(X - chess_size / 2, Y - chess_size / 2, chess_size, chess_size);
        }
    }

    public static Coordinate getPosition() {
        return position;
    }

    public static void setPosition(Coordinate position) {
        ChessPanel.position = position;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
