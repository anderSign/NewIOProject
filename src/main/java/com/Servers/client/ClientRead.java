package com.Servers.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientRead implements Runnable{
    private Socket socket = null;
    private InputStream in;
    private String threadName;
    public ClientRead(Socket socket,String threadName) throws IOException {
        this.socket = socket;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        while (true){
            try {
                in=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = new byte[65536];
            int len=0;
                    //只要不是文件或者缓冲流读一次即可，否则永远无法返回-1
//                    while ((len = inputStream.read(data)) != -1) {
//                        System.out.println(new String(data, 0, len));
//                    }
            try {
                len = in.read(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(data, 0, len));

        }
    }
}
