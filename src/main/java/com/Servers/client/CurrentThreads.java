package com.Servers.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class CurrentThreads extends Thread{
    private MainSocketClient mainSocketClient;
    private int index;
    private final String threadName;

    public MainSocketClient getMainSocketClient() {
        return mainSocketClient;
    }

    public void setMainSocketClient(MainSocketClient mainSocketClient) {
        this.mainSocketClient = mainSocketClient;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CurrentThreads(MainSocketClient mainSocketClient, int index){
        this.mainSocketClient=mainSocketClient;
        this.index =index;
        threadName= this.mainSocketClient.getHost()+"["+this.mainSocketClient.getPort()+"]"+this.getIndex();
    }

    @Override
    public void start() {
        run();
    }

    @Override
    public void run() {
        Scanner input =new Scanner(System.in);
        //如果i==0执行写操作,否则执行读操作
        while (true){
            try {
                if (this.index==0){
                    InputStream inputStream=mainSocketClient.getInputStream();
                    byte[] data = new byte[65536];
                    int len;
                    //写
                    OutputStream outputStream=mainSocketClient.getOutputStream();
                    String sendData=input.nextLine();
                    outputStream.write((threadName+"\tsay:"+sendData).getBytes());
                    //读
                    while ((len = inputStream.read(data)) != -1) {
                        System.out.println(new String(data, 0, len));
                    }
                }else {
                    //读
                    System.out.println("读数据中");
                    try {
                        //休息2秒
                        CurrentThreads.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
