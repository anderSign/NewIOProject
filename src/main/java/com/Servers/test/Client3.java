package com.Servers.test;

import com.Servers.client.MainSocketClient;

import java.io.IOException;
import java.util.Scanner;

public class Client3 {
    public static void main(String[] args) throws IOException {
        Scanner input =new Scanner(System.in);
        System.out.println("请输入一个端口号：开始链接");
//        int port=input.nextInt();
        int port=8091;
        MainSocketClient client=new MainSocketClient("localhost",port);
        client.starter();
    }
}
