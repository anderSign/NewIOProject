package com.Servers.test;

import com.Servers.constant.BasePath;
import com.Servers.constant.EnableAutoConfiguration;
import com.Servers.start.WebAppHandler;

import java.io.IOException;
import java.net.Socket;
/*
服务器测试类
 */
public class WebApp {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("修改,第二次");
        WebAppHandler.openSocketServerInstance(5);
    }
}