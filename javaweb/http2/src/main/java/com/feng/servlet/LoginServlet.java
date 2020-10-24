package com.feng.servlet;

import com.feng.server.Request;
import com.feng.server.Response;

public class LoginServlet extends Servlet {
    @Override
    public void deGet(Request req, Response rep) throws Exception {
        //获取请求参数
        String name = req.getParamter("username");
        String pwd = req.getParamter("pwd");

        if (this.login(name,pwd)) {
            //调用响应中的构建内容的方法
            rep.printLn(name+"登入成功");
        }else {
            rep.printLn(name+"登录失败,对不起，账号和密码不准确");
        }
    }
    private boolean login(String name,String pwd){
        if ("admin".equals(name)&&"123".equals(pwd)) {
            return true;
        }
        return false;
    }

    @Override
    public void doPost(Request req, Response rep) throws Exception {

    }
}
