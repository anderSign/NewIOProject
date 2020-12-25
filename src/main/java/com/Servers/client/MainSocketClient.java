package com.Servers.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainSocketClient extends Socket{
    private InputStream in;
    private OutputStream out;
    private int port;
    private String host;


    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public synchronized void starter() throws IOException {

        System.out.println("LocalAddress"+this.getLocalSocketAddress());
        //创建两个线程执行读写分离,否则会阻塞（但是经过实测这样比较难以处理内部阻塞）
//        CurrentThreads[] threads=new CurrentThreads[2];
//        for (int i = 0; i < threads.length; i++) {
//            threads[i]=new CurrentThreads(this,i);
//            if (i==0){
//                threads[i].setName("Write-Thread");
//            }else {
//                threads[i].setName("Read-Thread");
//            }
//        }
//        //读
//        threads[0].start();
//        //写 - 未执行
//        threads[1].start();
        /*
        尝试使用最简单的单一线程来处理读写问题
         */
        CurrentThreads thread=new CurrentThreads(this,0);
        thread.start();
    }
    public MainSocketClient(String host , Integer port) throws IOException {
        super(host,port);
        this.host=host;
        this.port=port;
    }
    public void close(){
        try {
            if (in!=null)in.close();
            if (out!=null)out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
