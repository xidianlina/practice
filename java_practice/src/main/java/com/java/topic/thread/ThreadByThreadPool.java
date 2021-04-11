package com.java.topic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadByThreadPool {
    private static final int POOLNUM = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < POOLNUM; ++i) {
            RunnableThread thread = new RunnableThread();
            executorService.execute(thread);
        }
        //关闭线程池
        executorService.shutdown();
    }
}

class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println("通过线程池方式创建的线程：" + Thread.currentThread().getName());
    }
}