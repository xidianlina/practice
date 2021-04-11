package com.java.topic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadImplementsCallable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Callable<Object> callable = new MyCallable();
        FutureTask<Object> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.setName("我是通过实现Callable接口通过FutureTask包装器来实现的线程");
        thread.start();
        try {
            Object res = futureTask.get();
            System.out.println(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<Object> {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        int sum = 0;
        for (int i = 0; i < 10; ++i) {
            sum += i;
        }
        return sum;
    }
}