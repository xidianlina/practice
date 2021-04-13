package com.java.topic.thread;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public final static AtomicReference<String> arrStr = new AtomicReference<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Math.abs((int) (Math.random() * 100)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    arrStr.set("abc");

                    if (arrStr.compareAndSet("abc", "def")) {
                        System.out.println("Thread:" + Thread.currentThread().getId() + " change value to " + arrStr.get());
                    } else {
                        System.out.println("Thread:" + Thread.currentThread().getId() + " change failed!");
                    }

                    System.out.println(arrStr.get());

                    System.out.println(arrStr.getAndSet("fg"));
                }
            }.start();
        }
    }
}
