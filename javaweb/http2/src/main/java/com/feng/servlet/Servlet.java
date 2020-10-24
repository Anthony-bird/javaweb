package com.feng.servlet;

import com.feng.server.Request;
import com.feng.server.Response;

public abstract class Servlet { //是所有请求servlet的父类
    public void service(Request req, Response rep) throws Exception {
        this.deGet(req,rep);
        this.doPost(req,rep);
    }

    public abstract void deGet(Request req, Response rep) throws Exception;
    public abstract void doPost(Request req, Response rep) throws Exception;

}
