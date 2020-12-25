package com.Servers.server;

import com.Servers.config.BaseConfiguration;
import com.Servers.constant.EnableAutoConfiguration;
import com.Servers.constant.ServerConstant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 功能:创建多个服务器,监听多个端口
 */
public class MainSocketServer implements Runnable,SocketServer{
    //为了防止中间其他线程干扰配置信息只能赋值一次
    BaseConfiguration baseConfiguration=new EnableAutoConfiguration().getBaseConfiguration();
    //服务器基本名称,只能被赋值一次
    private final String BaseName =baseConfiguration.getBaseName();
    //真正的服务器名称
    private String serverName;
    //用于多线程的一个服务端
    private ServerSocket socket;
    //深度
    private int deep= ServerConstant.DEEP;
    //默认的最大线程数
    private int maxThread=baseConfiguration.getMaxThread();
    //初始端口号
    private int BaseHost=baseConfiguration.getBaseHost();
    //输入输出流
    private InputStream in;
    private OutputStream out;
    //服务器端对象
    private List<Socket> servers;
    //当前对象需要执行的线程
    private List<Thread> threads;

    public MainSocketServer() throws ClassNotFoundException {
        threads=new ArrayList<>();
    }

    public String getBaseName() {
        return BaseName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getBaseHost() {
        return BaseHost;
    }

    public void setBaseHost(int baseHost) {
        BaseHost = baseHost;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    /**
     * 将要有序被开启的方法
     */
    @Override
    public synchronized void run() {
        try {
            init();
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
        serverName=BaseName+"["+this.deep+"]";
        System.out.println("服务器:"+serverName+"被开启!\t端口号:"+(--BaseHost));
        isMain();
        destroy();
    }

    private void destroy() {
        closeIO();
    }

    private void isMain() {
        Scanner input = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        String var1;
        int i=1;
        //解决阻塞问题的办法
        try {
            while (true){
                //这里应当注意一下弄不好会直接阻塞
                Socket var2=socket.accept();
                //过滤非法参数和重复参数
                if (var2!=null&&!servers.contains(var2)) {
                    servers.add(var2);
                    System.out.println("服务器:" + serverName + "被连接!->端口号:"+var2.getPort());
                    Thread thread=new Thread(() -> {
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
                                        //暴力跳出循环
                                        break;
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
                        }
                    }
                    );
                    //添加并启动线程
                    thread.setName("CoreThread-"+(i++));
                    threads.add(thread);
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开启创建一个符合条件的实例的方法
     */
    private void getServerSocketInstance() throws InvalidObjectException {
        getServerSocketInstance(this.deep);
    }

    /**
     * 创建一个符合条件的实例
     * @param deep 最大深度
     */
    private synchronized void getServerSocketInstance(int deep) throws InvalidObjectException {
        int InitHost=BaseHost;
        this.BaseHost++;
        try {
            this.socket=new ServerSocket(InitHost);
            this.deep=deep;
        } catch (IOException e) {
            if (deep<=maxThread) {
                getServerSocketInstance(deep+1);
//                System.out.println("一个实例已被创建！！！将要选择下一个端口号\n的进行创建深度：" + this.deep + "\n");
            }else {
                throw new InvalidObjectException("非法的对象创建!");
            }
        }
//        System.out.println("当前深度:"+this.deep);
    }

    public void closeIO(){
        try {
            if (in!=null)in.close();
            if (out!=null)out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init() throws InvalidObjectException {
        //初始化服务器端存储对象
        servers=new ArrayList<>();
        getServerSocketInstance();
    }
}
