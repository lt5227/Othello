package cn.edu.suda;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.LoginPanel;
import cn.edu.suda.othello.listener.StartGameActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 关于Spring boot + Swing 开发的简单配置参考：
 * http://zetcode.com/articles/springbootswing/
 */
@SpringBootApplication
public class OthelloApplication extends JFrame {
    @Autowired
    private LoginPanel loginPanel;
    @Autowired
    private ChessPanel chessPanel;
    @Autowired
    private StartGameActionListener startGameActionListener;

    @PostConstruct
    private void initUI() {
        // 给登录面板的开始游戏按钮添加动作监听
        loginPanel.addStartButtonActionListener(startGameActionListener);
        this.setSize(1000, 650); // 设置窗口大小
        this.getContentPane().add(chessPanel, BorderLayout.CENTER); // 将棋盘面板添加到容器中
        this.setTitle("黑白棋"); // 设置标题
        this.setGlassPane(loginPanel); // 设置登录面板为玻璃面板
        loginPanel.setVisible(true); // 显示登录面板

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 窗口基于屏幕中央显示 参考：http://blog.csdn.net/hanshileiai/article/details/6684500
        this.setLocationRelativeTo(null);
    }

    private void createLayout(JComponent... arg) {
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        gl.setAutoCreateContainerGaps(true);
        gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));
        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[0]));
    }

    private void startServer() {
        /*
         * 创建ServerSocket的同时需要申请服务端口
		 * 这个端口不能与其他使用TCP协议的应用程序
		 * 冲突，否则会抛出异常。
		 */
        try {
            final ServerSocket serverSocket = new ServerSocket(9527);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "本程序禁止重复运行，只能同时存在一个实例。"
                    , "你敢重复运行？", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(OthelloApplication.class)
                .headless(false).run(args);
        EventQueue.invokeLater(() -> {
            OthelloApplication ex = ctx.getBean(OthelloApplication.class);
            ex.setVisible(true);
        });
    }
}
