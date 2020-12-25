package com.Servers.start;

import com.Servers.server.MainSocketServer;
import com.Servers.server.ServerChannel;

import java.util.Iterator;
import java.util.List;

/**
 * 功能:开启多个服务器
 */
public class WebAppHandler {
    /**
     * 开启一个服务器
     */
    public static void openSocketServerInstance() throws ClassNotFoundException {
        Thread var = new Thread(new MainSocketServer());
        var.start();
    };

    /**
     * 开启多个服务器
     * @param count 开启的数量
     */
    public static Thread[] openSocketServerInstance(int count) throws ClassNotFoundException {
        //多线程对象
        Thread[] vars=new Thread[count];
        //服务器管道
        ServerChannel serverChannel=new ServerChannel();
        //基本对象
        List<MainSocketServer> serverSocketList = null;
        //标记
        int index=0;
        //断言count>0;
        assert count>0;
        //创建对象
        for (int i = 0; i < count; i++) {
            serverSocketList = serverChannel.getServerSocketList();
            serverSocketList.add(new MainSocketServer());
        }
        //开启线程
        Iterator<MainSocketServer> iterator = serverSocketList.iterator();
        while (iterator.hasNext()){
            vars[index]=new Thread(iterator.next());
            //启动线程
            vars[index].start();
            //移动索引
            index++;
        }
        return vars;
    };
}
