package com.java.topic.thread;

public class ThreadRunnableInnerClass {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        System.out.println(Thread.currentThread().getName());
    }
}
