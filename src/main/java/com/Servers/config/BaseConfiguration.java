package com.Servers.config;

import com.Servers.constant.BaseConfigInfo;
import com.Servers.constant.ServerConstant;

/*
  表明该类是一个配置类
 */
@BaseConfigInfo
public class BaseConfiguration implements Configuration{
    private final BaseConfigInfo configInfo= this.getClass().getAnnotation(BaseConfigInfo.class);
    //服务器基本名称,只能被赋值一次
    private final String BaseName =configInfo.poolName();
    //默认的最大线程数
    private int maxThread=configInfo.maxPoolNum();
    //初始端口号
    private int BaseHost=configInfo.serverHost();

    public String getBaseName() {
        return BaseName;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getBaseHost() {
        return BaseHost;
    }

    public void setBaseHost(int baseHost) {
        BaseHost = baseHost;
    }

    @Override
    public String toString() {
        return "BaseConfiguration{" +
                "configInfo=" + configInfo +
                ", BaseName='" + BaseName + '\'' +
                ", maxThread=" + maxThread +
                ", BaseHost=" + BaseHost +
                '}';
    }
}
