package com.java.topic.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        // 取得当前值
        System.out.println(atomicInteger.get());//1
        // 当前值加1，返回新值
        System.out.println(atomicInteger.incrementAndGet());//2
        // 设置新值，并返回旧值
        System.out.println(atomicInteger.getAndSet(1));//2
        // 当前值加1，返回旧值
        System.out.println(atomicInteger.incrementAndGet());//2
        // 当前值减1，返回旧值
        System.out.println(atomicInteger.getAndDecrement());//2
        // 当前值增加delta，返回旧值
        System.out.println(atomicInteger.getAndAdd(5));//1
        // 当前值加1，返回新值
        System.out.println(atomicInteger.incrementAndGet());//7
        // 当前值减1，返回新值
        System.out.println(atomicInteger.decrementAndGet());//6
        // 当前值增加delta，返回新值
        System.out.println(atomicInteger.addAndGet(4));//10
        // 当前值与x做计算，返回旧值
        System.out.println(atomicInteger.getAndAccumulate(2, (i, j) -> i + j));//10
        // 当前值与x做计算，返回新值
        System.out.println(atomicInteger.accumulateAndGet(2, (i, j) -> i + j));//14
        // 如果当前值为expect，则设置为u,返回true活着false
        System.out.println(atomicInteger.compareAndSet(14, 1));
    }
}
