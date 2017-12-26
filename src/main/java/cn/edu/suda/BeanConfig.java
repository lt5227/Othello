package cn.edu.suda;

import cn.edu.suda.othello.ChessPanel;
import cn.edu.suda.othello.GameServer;
import cn.edu.suda.othello.LoginPanel;
import cn.edu.suda.othello.NameAndIpPanel;
import cn.edu.suda.othello.listener.StartGameActionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/12 10:19.
 * Java Bean 配置类
 */
@Configuration
public class BeanConfig {

    @Bean
    public NameAndIpPanel nameAndIpPanel() {
        return new NameAndIpPanel();
    }

    @Bean
    public LoginPanel loginPanel() {
        return new LoginPanel(nameAndIpPanel());
    }

    @Bean
    public ChessPanel chessPanel() {
        return new ChessPanel();
    }

    @Bean
    public StartGameActionListener startGameActionListener(LoginPanel loginPanel, ChessPanel chessPanel) {
        return new StartGameActionListener(loginPanel, chessPanel);
    }

    @Bean
    @Scope(value = "singleton")
    public GameServer gameServer(LoginPanel loginPanel, ChessPanel chessPanel) {
        return new GameServer(loginPanel, chessPanel);
    }
}
