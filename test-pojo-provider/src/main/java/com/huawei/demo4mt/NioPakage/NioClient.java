package com.huawei.demo4mt.NioPakage;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.NioPakage
 */
public class NioClient {
    private static int DEFAULT_PORT = 12345;

    private static String DEFAULT_HOST = "127.0.0.1";

    private static ClientHandle clientHandle;

    public static void start() {
        start(DEFAULT_HOST, DEFAULT_PORT);
    }

    private static void start(String defaultHost, int defaultPort) {
        if (clientHandle != null) {
            clientHandle.stop();
        }
        clientHandle = new ClientHandle(DEFAULT_HOST, DEFAULT_PORT);
        new Thread(clientHandle, "Client").start();
    }

    public static boolean sendMsg(String msg) throws Exception {
        if (msg.equals("q")) {
            return false;
        }
        clientHandle.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) {
        start();
    }
}
