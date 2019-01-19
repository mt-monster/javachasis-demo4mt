package com.huawei.demo4mt.aiopkg;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.aiopkg
 */
public class AsyncServerHandler implements Runnable {
    public CountDownLatch latch;

    public AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncServerHandler(int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务已启动，端口号" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override public void run() {
        latch = new CountDownLatch(1);
        serverSocketChannel.accept(this,new AccpetHandler());
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
