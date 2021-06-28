package com.java.topic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    // 创建一个可重用固定个数的线程池
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(1, 2,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1));

    public static void main(String[] args) {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        printCount();
        System.out.println("加入第一个任务，线程池刚刚初始化，没有可以执行任务的核心线程，创建一个核心线程来执行任务");

        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        printCount();
        System.out.println("加入第二个任务，没有可以执行任务的核心线程，且任务数大于corePoolSize，新加入任务被放在了阻塞队列中");

        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        printCount();
        System.out.println("加入第三个任务，此时，阻塞队列已满，新建非核心线程执行新加入任务");

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printCount();
        System.out.println("第一个任务执行完毕，核心线程空闲，阻塞队列的任务被取出来，使用核心线程来执行");

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printCount();
        System.out.println("第二个任务执行完毕，核心线程空闲，非核心线程在执行第三个任务");

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printCount();
        System.out.println("第三个任务执行完毕，非核心线程被销毁，核心线程保留");

    }

    private static void printCount() {
        System.out.println("------------------------------------");
        System.out.println("当前活跃线程数:" + fixedThreadPool.getActiveCount());
        System.out.println("当前核心线程数:" + fixedThreadPool.getCorePoolSize());
        System.out.println("阻塞队列中的任务数:" + fixedThreadPool.getQueue().size());
    }
}
