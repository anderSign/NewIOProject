package com.Servers.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerExecute extends Thread{
    //服务器端对象
    private volatile List<Socket> servers;
    private Socket socket;
    private StringBuilder s = new StringBuilder();
    //输入输出流
    private InputStream in;
    private OutputStream out;
    //服务器名
    private String serverName;
    //客户端口号
    private int clientPort;
    public List<Socket> getServers() {
        return servers;
    }


    public Socket getSocket() {
        return socket;
    }

    public ServerExecute(Socket socket, List<Socket> servers,String serverName,int clientPort){
        this.servers=servers;
        this.socket=socket;
        this.serverName=serverName;
        this.clientPort=clientPort;
        s.append("日志:\n");
    }

    @Override
    public void run() {

        try {
            while (true){
                in = socket.getInputStream();
                if (in != null) {
                    //获取数据
                    byte[] data = new byte[1024];
                    int len;
                    //重新返回read方法时直接阻塞,所以只要不是文件调用一次即可,否则永远读不到-1
//                    while ((len = in.read(data)) != -1) {
//                        s.append("\n").append(new String(data, 0, len)).append("\n");
//                    }
                    len =in.read(data);
                    s.append(new String(data, 0, len));
                    System.out.println("服务器" + serverName + "接收到客户端="+clientPort+"的信息:" + s);
                    //群发
                    for (Socket outServer : servers) {
                        out = outServer.getOutputStream();
                        out.write(data);
                    }
                    //关闭掉流
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void destroy() {
        closeIO();
    }
    public void closeIO(){
        try {
            if (in!=null)in.close();
            if (out!=null)out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*

        while (true)
            try {
                for (Socket server : servers) {
                    in = server.getInputStream();
                    if (in != null) {
                        //获取数据
                        byte[] data = new byte[65536];
                        int len;
                        //重新返回read方法时直接阻塞
                        while ((len = in.read(data)) >= 0) {
                            s.append(new String(data, 0, len));
                        }
                        System.out.println("服务器" + serverName + "接收并发送:" + s);
                        //群发
                        for (Socket outServer : servers) {
                            out = outServer.getOutputStream();
                            out.write(data);
                        }
                        //关闭掉流
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                destroy();
            }




            BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String msg;//服务器发过来的信息
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("##服务器：" + msg);
            }
        } catch (IOException e) {
            System.out.println("警告：断开连接！");
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e1) {
                System.out.println("读取线程：关闭socket出现错误");
            }
        }
 */