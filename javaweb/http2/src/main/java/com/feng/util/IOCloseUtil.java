package com.feng.util;

import java.io.Closeable;
import java.io.IOException;

public class IOCloseUtil {
    //关闭全部的工具类
    public static void closeAll(Closeable...close){
        for (Closeable closeable : close) {
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
