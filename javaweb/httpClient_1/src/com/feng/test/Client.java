package com.feng.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //1.创建socket对象
        Socket client=null;

        DataOutputStream dos = null;
        DataInputStream dis =null;
        try {
            client = new Socket("localhost", 8888);
            //2.获取输出流请求
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF("我是客服端：服务器你好!");
            //3.获取输出流响应
            dis = new DataInputStream(client.getInputStream());
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流
            IOClose.closeAll(dis,dos,client);
        }


    }
}
