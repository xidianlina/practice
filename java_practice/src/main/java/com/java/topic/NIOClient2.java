package com.java.topic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient2 {
    private SocketChannel sc = null;
    private Selector selector = null;

    public NIOClient2(int port) {
        try {
            sc = SocketChannel.open();
            selector = Selector.open();
            sc.connect(new InetSocketAddress(InetAddress.getLocalHost(), port));
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            ByteBuffer bf = ByteBuffer.wrap("Hello".getBytes());
            sc.write(bf);
            listener(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listener(Selector selector) {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //读取服务端数据事件
                    if (key.isReadable()) {
                        System.out.println("读取服务端数据");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer bf = ByteBuffer.allocate(1024);
                        socketChannel.read(bf);
                        System.out.println("来自服务端数据：" + new String(bf.array()));
                    }

                    //向服务端写入数据
                    if (key.isWritable()) {
                        System.out.println("客户端开始向服务端写数据");
                        new Thread(new DealWrite(key)).start();
                        // 取消写就绪，否则会一直触发写就绪
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NIOClient2 client2 = new NIOClient2(9999);
    }
}
