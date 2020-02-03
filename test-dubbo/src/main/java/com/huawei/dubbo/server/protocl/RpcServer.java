package com.huawei.dubbo.server.protocl;

public interface RpcServer {
    /**
     * 开启服务，监听host:port
     * @param host
     * @param port
     */
    void start(String host,int port);
}
