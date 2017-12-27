package cn.edu.suda.othello.util;

import cn.edu.suda.othello.UserBean;
import cn.edu.suda.othello.util.pojo.Coordinate;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/12/27 10:01.
 */
@Component
public class SocketUtil {
    private Socket socket;

    /**
     * 发送UserBean对象信息
     *
     * @param coordinate 用户信息对象
     */
    public void sendUserBean(Coordinate coordinate) {
        try {
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            PrintWriter pw = new PrintWriter(osw, true);
            String message = JSONObject.toJSONString(coordinate);
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收UserBean对象信息
     *
     * @return 用户信息对象
     */
    public Coordinate receiveUserBean() {
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String message = null;
            while((message = br.readLine())!=null){
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
