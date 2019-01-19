package com.huawei.demo4mt.niopkg;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.niopkg
 */
public class ServerHandle implements Runnable {
    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean started;

    public ServerHandle(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("服务器已经启动在" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void stop() {
        started = false;

    }

    @Override public void run() {
        while (started) {
            try {
                // TODO: 2018/10/14 compare
                //            selector.select();
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey sk;
                while (iterator.hasNext()) {
                    sk = iterator.next();
                    iterator.remove();
                    try {
                        handInput(sk);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (sk != null) {
                            sk.cancel();
                            if (sk.channel() != null) {
                                sk.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handInput(SelectionKey sk) throws IOException {
        if (sk.isValid()) {
            if (sk.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
                //通过ServerSocketChannel的accept创建SocketChannel实例
                //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
                SocketChannel sc = ssc.accept();
                //设置为非阻塞的
                sc.configureBlocking(false);
                //注册为读
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (sk.isReadable()) {
                SocketChannel sc = (SocketChannel) sk.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = sc.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String expression = new String(bytes, "UTF-8");
                    System.out.println("receive data is ..." + expression);
                    String result;
                    try {
                        result = Calculator.cal(expression).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = "calucate error " + e.getMessage();
                    }

                    dowrite(sc, result);
                } else if (read < 0) {
                    sc.close();
                    sk.cancel();
                }

            }
        }
    }

    private void dowrite(SocketChannel sc, String resp) throws IOException {
        byte[] bytes = resp.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);

    }
}
