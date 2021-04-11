package com.java.topic.thread;

public class ThreadImplementsRunnable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(new MyThread());
        thread.setName("我是通过实现接口的线程实现方式！");
        thread.start();
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}