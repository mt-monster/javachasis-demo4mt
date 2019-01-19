package com.huawei.demo4mt.aiopkg;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

import com.huawei.demo4mt.nioPkg.Calculator;


/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.aiopkg
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel socketChannel) {
        this.channel = socketChannel;
    }

    @Override public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        byte[] msg = new byte[buffer.remaining()];
        buffer.get(msg);
        try {
            String expression = new String(msg, StandardCharsets.UTF_8);
            System.out.println("服务器接收到的信息：" + expression);
            String calResult = null;
            try {
                calResult = Calculator.cal(expression).toString();
            } catch (Exception e) {
                e.printStackTrace();
                calResult = "计算错误：" + e.getMessage();
            }
            dowrite(calResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dowrite(String data) {
        byte[] bytes = data.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                  channel.write(buffer, buffer, this);
                }else{
                    //创建新的Buffer
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //异步读  第三个参数为接收消息回调的业务Handler
                    channel.read(readBuffer, readBuffer, new ReadHandler(channel));
                }

            }

            @Override public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
