package com.Servers.copy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        InetAddress Address=InetAddress.getLocalHost();
        System.out.println(Address);
        Address=InetAddress.getByName("www.HerbSchildt.com");
        System.out.println(Address);
        InetAddress[] Sw=InetAddress.getAllByName("www.nba.com");
        Socket client=new Socket("localHost",8888);
        System.out.println("链接本机");
        OutputStream out;
        InputStream in;
        Scanner input=new Scanner(System.in);
        boolean index=true;
        while (index) {
            out=client.getOutputStream();
            in=client.getInputStream();
            out.write(input.nextLine().getBytes());
            System.out.println("上传结束,等待接收...");
            Object obj=new Object();
// try {
// obj.wait();
// } catch (InterruptedException e) {
// System.out.println(" 线程异常 ");
// }
            byte[] data = new byte[1024];
            int len;
            while ((len = in.read(data)) != -1) {
                System.out.println(new String(data, 0, len));
            }
            out.close();
            in.close();
            System.out.println("是否离开");
            index= !input.nextLine().equals("yes");
        }
// 写入到流中发送到服务端
        client.shutdownOutput();
        System.out.println("关闭接口");
    }

}

