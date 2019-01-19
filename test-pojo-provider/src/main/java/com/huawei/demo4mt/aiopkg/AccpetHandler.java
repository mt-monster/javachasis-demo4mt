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
 * Package Name:com.huawei.demo4mt.AioPkg
 */
public class AccpetHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {
  @Override
  public void completed(AsynchronousSocketChannel socketChannel, AsyncServerHandler serverHandler) {

    AioServer.clientcount++;
    System.out.println("连接的客户端数： " + AioServer.clientcount);
    serverHandler.serverSocketChannel.accept(serverHandler, this);
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    socketChannel.read(byteBuffer, byteBuffer, new ReadHandler(socketChannel));
  }

  @Override
  public void failed(Throwable exc, AsyncServerHandler serverHandler) {
    exc.printStackTrace();
    serverHandler.latch.countDown();
  }
}
