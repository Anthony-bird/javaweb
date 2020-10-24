package com.feng.server;


import com.feng.util.IOCloseUtil;

import java.io.*;

public class Response { //响应
    private StringBuilder headInfo;  //响应头
    private StringBuilder content; //响应内容
    private int length; //响应内容的长度

    //流
    private BufferedWriter bw;

    //两个常量,换行和空格
    private static final String CRLF="\r\n";  //换行
    private static final String BLANK=" ";  //空格

    //构造方法
    public Response() {
        headInfo= new StringBuilder();
        content = new StringBuilder();
    }
    //带构造方法

    public Response(OutputStream os) {
        this();//调用本类的无参构造方法
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    //构造正文部分
    public Response print(String info){
        content.append(info);
        try {
            length+= info.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
    public Response printLn(String info){
        content.append(info).append(CRLF);
        try {
            length+=(info+CRLF).getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    //构造响应头
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code){
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
            default:
                headInfo.append("NOT FOUND");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:"+length).append(CRLF);
        headInfo.append(CRLF);
    }

    /**
     * 推送到客户机的浏览器
     * */
    public void pushToClient(int code){
        if (headInfo==null){
            code=500;
        }
        //调用本类中的构造响应头
        this.createHeadInfo(code);
        try {
            bw.write(headInfo.toString());
            bw.write(content.toString());
            bw.flush();
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        IOCloseUtil.closeAll(bw);
    }
}
