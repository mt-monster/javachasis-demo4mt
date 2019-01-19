package com.huawei.demo4mt.aiopkg;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.aiopkg
 */
public class AccpetHandler implements CompletionHandler<AsynchronousSocketChannel, com.huawei.demo4mt.aiopkg.AsyncServerHandler> {
    @Override public void completed(AsynchronousSocketChannel socketChannel, com.huawei.demo4mt.aiopkg.AsyncServerHandler serverHandler) {

        com.huawei.demo4mt.aiopkg.AioServer.clientcount++;
        System.out.println("连接的客户端数： " + com.huawei.demo4mt.aiopkg.AioServer.clientcount);
        serverHandler.serverSocketChannel.accept(serverHandler, this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer, byteBuffer, new com.huawei.demo4mt.aiopkg.ReadHandler(socketChannel));

    }

    @Override public void failed(Throwable exc, com.huawei.demo4mt.aiopkg.AsyncServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }

}
