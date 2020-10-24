package com.feng.server;

import com.feng.servlet.Servlet;
import com.feng.util.IOCloseUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server { //服务器，用于启动或停止服务
    private ServerSocket server;
    private boolean isShutDown=false; //默认没有出错

    public static void main(String[] args) {
        Server server = new Server();//创建服务器
        server.start();
    }

    private void start() {
        this.start(8888);
    }

    public void start(int port){
        try {
            server = new ServerSocket(port);
            this.receive();//调用接受请求的方式
        } catch (IOException e) {
            isShutDown=true;
        }
    }

    private void receive() {

        try {
            while (!isShutDown){
                //1监听
                Socket client = server.accept();
                //创建线程类
                Dispatcher dis = new Dispatcher(client);
                //创建代理线程
                new Thread(dis).start();
            }
            //获取用户的请求
           /*  InputStream is = client.getInputStream();
            byte[] buf = new byte[20480];
            int len = is.read(buf);
            System.out.println(new String(buf, 0, len));*/
           //封装请求信息
//            Request req = new Request(client.getInputStream());
            //req.show();
            /**
             * 做出响应
             * */
            /*StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1").append(" ").append(200).append(" ").append("OK").append("\r\n");
            sb.append("Content-Type:text/html;charset=utf-8").append("\r\n");
            //内容
            String str = "<html><head><title>响应结果</title></head><body>成功</body></html>";
            sb.append("Content-Length:"+str.getBytes("utf-8").length).append("\r\n");
            sb.append("\r\n");
            sb.append(str);

            //通过输出流发送出去
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
            bw.write(sb.toString());
            bw.flush();
            bw.close();*/
//            Response rep = new Response(client.getOutputStream());
//            Servlet servlet = WebApp.getServlet(req.getUrl());
//            int code=200;
//            if (servlet ==null){
//                code=404;
//            }
//            //调用Servlet中的服务方法
//            try {
//                servlet.service(req,rep);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            rep.pushToClient(code);
//            IOCloseUtil.closeAll(client);  //单线程已被删除
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void stop(){

    }
}
