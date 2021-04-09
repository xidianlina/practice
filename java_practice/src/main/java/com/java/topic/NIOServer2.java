package com.java.topic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOServer2 {

    private boolean isFirst = true;
    private ServerSocketChannel ssc = null;
    private Selector selector = null;

    public NIOServer2(int port) throws IOException {
        ssc = ServerSocketChannel.open();
        selector = Selector.open();
        InetSocketAddress inetAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);

        ssc.socket().bind(inetAddress);
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        listener(selector);
    }

    private void listener(Selector selector) throws IOException {
        while (true) {
            System.out.println("等待客户端连接");
            selector.select();
            System.out.println("捕获客户端连接");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //连接事件
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    channel.accept().configureBlocking(false).register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    System.out.println(channel.toString() + "-已连接");
                }

                //读数据
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    channel.read(bf);
                    System.out.println("来自客户端数据：" + new String(bf.array()));

                    // 只有第一次通信返回消息
                    if (isFirst) {
                        isFirst = false;
                        ByteBuffer bst = ByteBuffer.wrap("Hello".getBytes());
                        channel.write(bst);
                    }
                }

                //写数据
                if (key.isWritable()) {
                    System.out.println("服务端写就绪");
                    new Thread(new DealWrite(key)).start();
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);// 取消写就绪，否则会一直触发写就绪
                }
                iterator.remove();

            }

        }
    }

    public static void main(String[] args) {
        try {
            NIOServer2 server = new NIOServer2(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class DealWrite implements Runnable {

    private SelectionKey key;

    public DealWrite(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String msg = sdf.format(new Date()) + "\t" + scanner.nextLine();
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer bst = ByteBuffer.wrap(msg.getBytes());
            try {
                channel.write(bst);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}