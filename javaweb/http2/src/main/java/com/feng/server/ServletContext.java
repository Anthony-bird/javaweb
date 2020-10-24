package com.feng.server;

import java.util.HashMap;
import java.util.Map;

/**
*ServletContext 上下文 就是一个容器
*  */
public class ServletContext { //Entity与Mapping之间的映射关系
        private Map<String, String> servlet;//key是servlet-name(实体类中的name),值servlet-class，实体类中的值
        private Map<String, String> mapping;//key是url-pattern(Map中的每一个元素),值servlet-name，实体类中的name

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public ServletContext(){
        servlet = new HashMap<String, String>();
        mapping = new HashMap<String, String>();
    }
}
