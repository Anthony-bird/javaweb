package com.feng.test;

import java.io.Closeable;
import java.io.IOException;

public class IOClose {
    //关闭全部的工具类
    public static void closeAll(Closeable...c){
        for (Closeable closeable : c) {
            if (closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
