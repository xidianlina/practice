package com.java.topic.thread;

public class ThreadExtendsThread extends Thread {
    public ThreadExtendsThread() {
        //编写子类的构造方法，可缺省
    }

    @Override
    public void run() {
        //编写执行体代码
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ThreadExtendsThread threadExtendsThread = new ThreadExtendsThread();
        threadExtendsThread.setName("thread1");
        threadExtendsThread.start();
        System.out.println(Thread.currentThread().toString());
    }
}
