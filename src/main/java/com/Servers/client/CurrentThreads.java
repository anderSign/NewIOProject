package com.Servers.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class CurrentThreads extends Thread{
    private MainSocketClient mainSocketClient;
    private final String threadName;

    public MainSocketClient getMainSocketClient() {
        return mainSocketClient;
    }

    public void setMainSocketClient(MainSocketClient mainSocketClient) {
        this.mainSocketClient = mainSocketClient;
    }



    public CurrentThreads(MainSocketClient mainSocketClient){
        //早就链接到服务器了
        this.mainSocketClient=mainSocketClient;
        threadName= this.mainSocketClient.getHost()+"["+this.mainSocketClient.getPort()+"]"+this.mainSocketClient.getLocalPort();
    }

    @Override
    public void start() {
        run();
    }

    /**
     * 多线程
     */
    @Override
    public void run() {
        Scanner input =new Scanner(System.in);
        //如果i==0执行写操作,否则执行读操作
        try {
            //直接开启线程
            new Thread(new ClientRead(this.mainSocketClient,threadName)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                byte[] data = new byte[65536];
                int len;
                OutputStream outputStream=mainSocketClient.getOutputStream();
                //手动输入的对象一定要转换
                String sendData=input.nextLine();
                outputStream.write(('\n'+threadName+"\tsay:"+sendData).getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 单线程存在问题
     */
//    @Override
//    public void run() {
//        Scanner input =new Scanner(System.in);
//        //如果i==0执行写操作,否则执行读操作
//        while (true){
//            try {
//
//                    InputStream inputStream=mainSocketClient.getInputStream();
//                    byte[] data = new byte[65536];
//                    int len;
//                    //写
//                    OutputStream outputStream=mainSocketClient.getOutputStream();
//                    //手动输入的对象一定要转换
//                    String sendData=input.nextLine();
//                    outputStream.write(('\n'+threadName+"\tsay:"+sendData).getBytes());
//                    outputStream.flush();
//                    //只要不是文件或者缓冲流读一次即可，否则永远无法返回-1
////                    while ((len = inputStream.read(data)) != -1) {
////                        System.out.println(new String(data, 0, len));
////                    }
//                    len = inputStream.read(data);
//                    System.out.println(new String(data, 0, len));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
