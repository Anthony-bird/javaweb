package com.feng.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) {
        //1.创建ServerSocket对象
        ServerSocket server = null;
        Socket client =null;

        BufferedReader br = null;
        try {
            server = new ServerSocket(8888);
            //2.监听是否有客服端发送请求
            client = server.accept();
            //获取来自浏览器的请求信息
            br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
            String str=null;
            while ((str=br.readLine()).length()>0) {
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOClose.closeAll(br,client,server);
        }





        //6.关闭流
    }
}
