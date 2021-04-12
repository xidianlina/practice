package com.java.topic.thread;

public class ThreadLocalTest {
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();
    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
        stringThreadLocal.set(Thread.currentThread().getName());
        ;
    }

    public long getLong() {
        return longThreadLocal.get();
    }

    public String getString() {
        return stringThreadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest test = new ThreadLocalTest();
        test.set();

        System.out.println("main " + test.getLong());
        System.out.println("main " + test.getString());

        Thread thread = new Thread() {
            @Override
            public void run() {
                test.set();
                System.out.println("thread " + test.getLong());
                System.out.println("thread " + test.getString());
            }
        };

        thread.start();
        thread.join();

        System.out.println("fianl " + test.getLong());
        System.out.println("fianl " + test.getString());
    }
}
