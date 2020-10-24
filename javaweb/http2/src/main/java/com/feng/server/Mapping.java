package com.feng.server;

import java.util.ArrayList;
import java.util.List;

public class Mapping { //映射关系，多个路径可以访问资源
    private String name; //servlet-name
    private List<String> urlPattern;//url-pattern

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Mapping() {
        this.urlPattern = new ArrayList<String>();
    }

    public Mapping(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }
}
