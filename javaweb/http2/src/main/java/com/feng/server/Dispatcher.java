package com.feng.server;

import com.feng.servlet.Servlet;
import com.feng.util.IOCloseUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * 一个请求与响应就是一个Dispatcher
 *
 * @author asus
 * */
public class Dispatcher implements Runnable {
    private Request req;
    private Response rep;
    private Socket client;
    private int code =200;//状态码

    //构造方法初始化属性

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            req=new Request(this.client.getInputStream());
            rep=new Response(this.client.getOutputStream());
        } catch (IOException e) {
            code=500;
            return;
        }

    }

    @Override
    public void run() {
        //根据不同的url创建指定的servlet对象
        Servlet servlet = WebApp.getServlet(req.getUrl());
        if (servlet==null){
            this.code=404;
        }else {
            //调用响应的servlet中service方法
            try {
                servlet.service(req,rep);
            } catch (Exception e) {
                this.code=500;
            }
        }
        //将有响应的结果推送到客户端的浏览器
        rep.pushToClient(code);
        IOCloseUtil.closeAll(client);
    }
}
