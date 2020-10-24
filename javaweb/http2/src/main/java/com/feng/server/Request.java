package com.feng.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.*;

public class Request { //请求

    //输入流
    private InputStream is;
    private String requestInfo; //请求字符串，请求方式，请求路径，参数，协议，协议版本，请求正文。。
    private String method;//请求的方式
    private String url;//请求的路径

    public String getUrl() {
        return url;
    }

    /*
    * 输入框的name为key，值为value
    * */
    private Map<String, List<String>> parameterMapValues;//参数
    private static final String CRLF="\r\n";//换行
    private static final String BLANK =" ";//空格
    //构造方法，初始化属性
    public Request(){
        parameterMapValues=new HashMap<String, List<String>>();
        method="";
        url="";
        requestInfo="";
    }
    public Request(InputStream is){
        this();//调用本类无参构造方法
        this.is=is;

        try {
            byte[] buf = new byte[20480];
            int len = this.is.read(buf);
            requestInfo = new String(buf, 0, len);
        } catch (IOException e) {
            return;
        }
        //调用本类中的分解信息的方法
        this.parseRequestInfo();
    }
    //分解请求信息的方法
    /**
    * 请求方式
    * 请求路径
    * 请求的参数
    * */
    public void parseRequestInfo() {
        String paraString="";//用于存储请求参数
        //获取参数的第一行
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF)).trim();//从0开始到第一个换行的位置
        //分解出请求方式
        int index = firstLine.indexOf("/");
        this.method = firstLine.substring(0, index).trim();
        //分解url ，get可能包含的参数，也可能不包含的参数post
        String urlString = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();
        //判断请求方式是GET或POST
        if ("get".equalsIgnoreCase(this.method)) {   //包含请求参数
            if (urlString.contains("?")){
                String[] urlArray = urlString.split("\\?");
                this.url=urlArray[0];
                paraString=urlArray[1];
            }else {
                this.url=urlString;
            }
        }else {//post不包含请求参数
            this.url=urlString;
            paraString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
         }
        if (paraString.equals("")){
            return;
        }
        //请求参数
//        System.out.println(paraString);
        //调用本类中请求参数的方法
        this.parseParam(paraString);
    }
    //用于测试
   /* public void show(){
        System.out.println(this.url);
        System.out.println(this.method);
    }*/

   /**
   *username = admin
    * pwd = 123
    * hobby =ball
    * hobby = paint
    * */
   public void parseParam(String prarString){
       String[] token = prarString.split("&");
       for (int i=0;i<token.length;i++){
           String keyValues = token[i];
           String[] keyValue = keyValues.split("=");//把=分割掉,得到K和V
           if (keyValue.length==1){   //username=
               keyValue = Arrays.copyOf(keyValue, 2);
               keyValue[1] = null;
           }
           //将表单中的元素的name与name对应的值储存到Map集合
           String key = keyValue[0].trim();
           String value = keyValue[1]==null?null:decode(keyValue[1].trim(),"utf-8");
           //放到集合中存储
           if (!parameterMapValues.containsKey(key)) {  //键不存在就创建
               parameterMapValues.put(key,new ArrayList<String>());
           }
           List<String> values = parameterMapValues.get(key);
           values.add(value); //创建一个集合添加值
       }

   }
    //根剧表单元素的name获取多个值
    public String [] getParamterValues(String name) {
       //根据key获取value
        List<String> values = parameterMapValues.get(name);
        if (values == null ){
            return null;
        } else {
            return values.toArray(new String[0] );
        }
    }
    public String getParamter(String name){
       //调用本类中根据name获取多个值的方法
        String[] values = this.getParamterValues(name);
        if (values ==null){
            return null;
        } else {
            return values[0];
        }
    }

    //处理中文,因浏览器对中文进行了编码，进行解码
    private String decode(String value,String code){
        try {
            return URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Request req = new Request();
        //调用分解参数的方法
        req.parseParam("username=中文加密&pwd=123&hobby=ball&hobby=read");
        System.out.println(req.parameterMapValues);

        //调用获取多个值的方法
        String[] str = req.getParamterValues("hobby");
        for (String string : str) {
            System.out.println(string);
        }
        //调用单个获取值的方法
        System.out.println(req.getParamter("pwd"));
    }
}
