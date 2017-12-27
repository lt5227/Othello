package cn.edu.suda.othello.util;

import cn.edu.suda.othello.UserBean;
import cn.edu.suda.othello.util.pojo.Coordinate;
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
        ObjectOutputStream oos = null;
        try {
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(coordinate);
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 接收UserBean对象信息
     *
     * @return 用户信息对象
     */
    public Coordinate receiveUserBean() {
        ObjectInputStream ois = null;
        try {
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ois = new ObjectInputStream(bis);
            Coordinate coordinate = (Coordinate) ois.readObject();
            socket.shutdownInput();
            if (coordinate != null) {
                return coordinate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
