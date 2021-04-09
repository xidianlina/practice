package com.java.topic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

//网络客户端程序
public class NIOClient {
    public static void main(String[] args) {
        try {
            //得到一个网络通道
            SocketChannel socketChannel = SocketChannel.open();

            //设置非阻塞式
            socketChannel.configureBlocking(false);

            //提供服务器ip与端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9090);

            //连接服务器端
            if (!socketChannel.connect(inetSocketAddress)) {
                //如果连接不上
                while (!socketChannel.finishConnect()) {
                    System.out.println("nio非阻塞");
                }
            }

            new Thread(new MyRunnable(socketChannel)).start();

            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(buffer);
                if (read > 0) {
                    System.out.println(new String(buffer.array()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MyRunnable implements Runnable {
        SocketChannel socketChannel;

        MyRunnable(SocketChannel channel) {
            this.socketChannel = channel;
        }

        @Override
        public void run() {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                //创建一个buffer对象并存入数据
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());

                try {
                    //发送数据
                    socketChannel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
