package com.feng.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
    public static void main(String[] args) {
        //1.创建ServerSocket对象
        ServerSocket server = null;
        Socket client =null;

        InputStream is = null;
        try {
            server = new ServerSocket(8888);
            //2.监听是否有客服端发送请求
            client = server.accept();
            //获取来自浏览器的请求信息
            is = client.getInputStream();
            byte[] buf = new byte[20480];
            int len = is.read(buf);
            System.out.println(new String(buf,0,len));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOClose.closeAll(is,client,server);
        }





        //6.关闭流
    }
}
