package com.Servers.server;

import java.util.ArrayList;
import java.util.List;

public class ServerChannel {
    private List<MainSocketServer> serverSocketList;

    public List<MainSocketServer> getServerSocketList() {
        return serverSocketList;
    }

    public void setServerSocketList(List<MainSocketServer> serverSocketList) {
        this.serverSocketList = serverSocketList;
    }

    public ServerChannel(){
        getServerSocketListInstance();
    }
    private void getServerSocketListInstance(){
        this.serverSocketList =new ArrayList<>();
    }
}
