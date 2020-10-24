package com.feng.server;

import com.feng.servlet.Servlet;

import java.util.List;
import java.util.Map;

public class WebApp {  //应用程序
    private static ServletContext context;
    static {
        context = new ServletContext();
        //分别获取对应关系的Map集合
        Map<String, String> servlet = context.getServlet();
        Map<String, String> mapping = context.getMapping();
        //创建解析XML文件对象
        WebDom4jTest web = new WebDom4jTest();
        //解析xml
        web.parse(web.getDocument());
        //获取解析xml之后的List集合
        List<Entity> entityList = web.getEntityList();
        List<Mapping> mappingList = web.getMappingList();

        //将List集合储存到map集合中
        for (Entity entity : entityList) {
            servlet.put(entity.getName(),entity.getClazz());
        }
        //System.out.println(servlet);
        for (Mapping map : mappingList) {
            //遍历url-pattern的集合
            List<String> urlPattern = map.getUrlPattern();
            for (String s : urlPattern) {
                mapping.put(s,map.getName());
            }
        }
        //System.out.println(mapping);
    }

    /*
    * 根据url创建不同的Servlet对象
    * */
    public static Servlet getServlet(String url){
        if (url ==null||url.trim().equals("")){
            return null;
        }
        try {
        //如果url正确
        //根据url的key获取servlet-name的值
        String servletName = context.getMapping().get(url);
        //根据servlet-name找到对应得servlet-class
        String servletClass = context.getServlet().get(servletName);
        //得到的是一个完整的包+类名的字符串

        //使用反射创建servlet对象
        Class<?> clazz = Class.forName(servletClass);
        Servlet servlet = (Servlet) clazz.newInstance();
        return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(getServlet("/log"));
        System.out.println(getServlet("/login"));
    }
}
