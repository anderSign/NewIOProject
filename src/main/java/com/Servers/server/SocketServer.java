package com.Servers.server;

import java.io.InvalidObjectException;

/**
 * 顶层接口
 * 功能：指定程序正确打开线程
 */
public interface SocketServer {
    /**
     * 这是一个启动的方法一般用于外部创建时提供
     * @throws InvalidObjectException
     */
    void init() throws InvalidObjectException;
}
