package com.feng.server;

public class Entity {  //servlet-name或每一个servlet-name所对应得实体类
    private String name;
    private String clazz;

    public Entity() {
    }

    public Entity(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
