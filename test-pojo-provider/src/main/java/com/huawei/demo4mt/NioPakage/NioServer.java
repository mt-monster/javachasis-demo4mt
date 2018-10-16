package com.huawei.demo4mt.NioPakage;

import java.net.ServerSocket;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.NioPakage
 */
public class NioServer {
    private static int DEFAULT_PORT = 12345;
    private static ServerHandle serverHandle;

    public static void start() throws Exception {
        start(DEFAULT_PORT);
    }

    private static void start(int port) {
        if(serverHandle!=null)
            serverHandle.stop();
        serverHandle = new ServerHandle(port);
        new Thread(serverHandle,"Server").start();
    }

    public static void main(String[] args) throws Exception {
        start();
    }

}
