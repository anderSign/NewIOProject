package com.Servers.copy;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器已经启动:");
        Socket socket= server.accept();
        System.out.println("链接成功"+"\n"+socket.getRemoteSocketAddress());
        // 接受数据获取输入流
        InputStream in;
        OutputStream out;
        boolean index=true;
        Scanner input = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        String speak;
        while (index) {
// 读取数据
            in=socket.getInputStream();
            out=socket.getOutputStream();
            byte[] data = new byte[1024];
            int len;
            while ((len = in.read(data)) != -1) {
                s.append(new String(data, 0, len));
                System.out.println("正在检索...");
                break;
            }
            System.out.println("客户端发来的数据:" + s);
// 写入数据
// System.out.println(" 请输入消息 ");
            System.out.println("请输入对话:");
            out.write(input.nextLine().getBytes());
            in.close();
            out.close();
            System.out.println("输入结束请接收");
            socket.shutdownOutput();
            System.out.println("是否离开:(输入除了 yes 之外的是不离开)");
            String s1=input.nextLine();
            index=!s1.equals("yes");
        }
        System.out.println("信息接收结束");
        socket.close();
        System.out.println("main 执行完成!");//netstat -an 查询
    }

}