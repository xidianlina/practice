package com.java.topic;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NIOTest {
    @Test
    public void IntBufferTest() {
        // 分配内存大小为11的整型缓存区
        IntBuffer buffer = IntBuffer.allocate(11);

        // 往buffer里写入2个整型数据
        for (int i = 0; i < 2; ++i) {
            int randomNum = new SecureRandom().nextInt();
            buffer.put(randomNum);
        }
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());

        // 将Buffer从写模式切换到读模式
        buffer.flip();
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());

        // 读取buffer里的数据
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());

        // 重新读取buffer中的数据
        buffer.flip();
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());

        System.out.println("----------------------------");
        // 将Buffer从读模式切换到写模式
        buffer.flip();
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());
        for (int i = 0; i < 2; ++i) {
            int randomNum = new SecureRandom().nextInt();
            buffer.put(randomNum);
        }
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());

        //需要再切换模式才能读
        while (buffer.hasRemaining()) {
            System.out.println("while");
            System.out.println(buffer.get());
        }
        System.out.println("position >> " + buffer.position()
                + " limit >> " + buffer.limit()
                + " capacity >> " + buffer.capacity());
    }

    /**
     * 缓冲区的大小
     */
    public static final int SIZE = 1024;

    @Test
    public void channelTest() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            // 打开文件输入流
            inChannel = new FileInputStream("/Users/lina/practice/java_practice/topic/tu.png").getChannel();

            // 打开文件输出流
            outChannel = new FileOutputStream("/Users/lina/practice/java_practice/topic/tu_copy.png").getChannel();

            // 分配 1024 个字节大小的缓冲区
            ByteBuffer dsts = ByteBuffer.allocate(SIZE);

            // 将数据从通道读入缓冲区
            while (inChannel.read(dsts) != -1) {
                // 切换缓冲区的读写模式
                dsts.flip();

                // 将缓冲区的数据通过通道写到目的地
                outChannel.write(dsts);

                // 清空缓冲区，准备下一次读
                dsts.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
