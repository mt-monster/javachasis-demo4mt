package com.huawei.demo4mt.nioPkg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.nioPkg
 */
public class ServerBetter {

    private static int DEFAULT_PORT = 12345;

    private static ServerSocket serverSocket;

    private static ExecutorService executorService = Executors.newFixedThreadPool(70);

    public static void start() throws Exception {
        start(DEFAULT_PORT);
    }

    public static void start(int defaultPort) throws Exception {

        if (serverSocket != null) {
            return;
        }
        try {
            serverSocket = new ServerSocket(defaultPort);
            System.out.println("服务已启动,端口号为" + defaultPort);
            while (true) {
                Socket socket = serverSocket.accept();
                //                new Thread(new ServerHandler(socket)).start();
                executorService.execute(new ServerHandler(socket));
            }
        } finally {
            if (serverSocket != null) {
                System.out.println("doing close server....");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
