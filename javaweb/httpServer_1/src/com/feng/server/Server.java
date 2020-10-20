package com.feng.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        //1.创建ServerSocket对象
        ServerSocket server = null;
        Socket client =null;
        DataInputStream dis =null;
        DataOutputStream dos =null;
        try {
            server = new ServerSocket(8888);
            //2.监听是否有客服端发送请求
            client = server.accept();
            //3.获取Socket对象
            //4.获取输入流 ->得到客服端的请求
            dis = new DataInputStream(client.getInputStream());
            System.out.println(dis.readUTF());
            //5.获取输出流 ->给客服端响应结果
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF("客服端你好，我是服务器，我接受到了你的信息");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOClose.closeAll(dos,dis,client,server);
        }





        //6.关闭流
    }
}
