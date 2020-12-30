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
     * 销毁当前的所有线程和已经存在的东西
     */
    public void destroy(){
        try {
            mainSocketClient.shutdownOutput();
            mainSocketClient.shutdownInput();
            mainSocketClient.close();
            //垃圾回收一次
            System.gc();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 多线程
     */
    @Override
    public void run() {
        Scanner input =new Scanner(System.in);
        //如果i==0执行写操作,否则执行读操作
        Thread var = null;
        try {
            //直接开启线程
            var=new Thread(new ClientRead(this.mainSocketClient,threadName));
            var.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("想要退出时输入:\n进入指令模式\n输入q回车表示关闭服务器,输入q!表示强制关闭服务器" +
                "\n输入n表示正常打印结果并退出编辑模式\n其他功能待开发中...");
        int i=0;
        while (i==0){
            try {
                byte[] data = new byte[65536];
                int len;
                OutputStream outputStream=mainSocketClient.getOutputStream();
                assert var != null;
                //判断线程是否存活,存活继续否则终止线程
                if (!var.isAlive()){
                    destroy();
                }
                //手动输入的对象一定要转换
                String sendData=input.nextLine();
                if (sendData.length()==1) {
                    if (sendData.getBytes()[0] == ':') {
                        System.out.println("===============你已经进入指令模式请确认是不是只是输入字符':'?===============");
                        //用于判断的值
                        String var1;
                        while (true) {
                            //输入一个值
                            var1 = input.nextLine();
                            if (var1 == null || var1.length() < 1 || var1.length() > 2) {
                                System.out.println("必须输入合法值");
                            } else {
                                if (var1.getBytes()[0] == 'q') {
                                    if (var1.length() == 1) {
                                        i++;
                                        break;
                                    } else if (var1.getBytes()[1] == '!') {
                                        destroy();
                                        //强制退出
                                        System.exit(0);
                                    }
                                } else if (var1.length() == 1) {
                                    if (var1.getBytes()[0] == 'n') {
                                        break;
                                    }
                                } else {
                                    System.out.println("必须输入合法值");
                                }
                            }

                        }
                    }
                }
                if (i == 0) {
                    outputStream.write((threadName + "\tsay:" + sendData + "\n").getBytes());
                    outputStream.flush();
                } else {
                    var.interrupt();
                }
            } catch (IOException e) {
                System.err.println("java.com.Server.client.CurrentThreads.run()"+"发生了一个严重错误");
                e.printStackTrace();
            }
        }
        destroy();
    }
    /**
     * 单线程存在问题
     */
//    @Override
//    public void run() {
//        Scanner input =new Scanner(System.in);
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
