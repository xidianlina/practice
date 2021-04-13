package com.java.topic.thread;

public class JoinTest {
    public static void main(String[] args) {
        MyThreadJoin t1 = new MyThreadJoin("A");
        MyThreadJoin t2 = new MyThreadJoin("B");
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.out.println("main");
    }
}

class MyThreadJoin extends Thread {
    private String name;

    public MyThreadJoin(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; ++i) {
            System.out.println(name + "-" + i);
        }
    }
}