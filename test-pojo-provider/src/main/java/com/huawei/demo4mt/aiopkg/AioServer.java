package com.huawei.demo4mt.aiopkg;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.AioPkg
 */
public class AioServer {

    private static int DEFAULT_PORT = 12345;

    private static AsyncServerHandler serverHandler;

    public static volatile long clientcount = 0;

    public static void start() {
        start(DEFAULT_PORT);
    }

    private static void start(int port) {
        if (serverHandler != null) {
            return;
        }
        serverHandler = new AsyncServerHandler(port);
        new Thread(serverHandler, "AioServer").start();
        System.out.println("Aio Main Thread is running ....");

    }

    public static void main(String[] args) {
        AioServer.start();
    }
}
