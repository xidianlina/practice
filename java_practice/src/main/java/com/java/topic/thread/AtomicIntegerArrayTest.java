package com.java.topic.thread;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {
    public static void main(String[] args) {
        AtomicIntegerArray array = new AtomicIntegerArray(5);
        //将第i个下标的元素值设置为x
        array.set(0, 1);
        // 获得数组第i个下标的元素
        System.out.println(array.get(0) + "," + array.get(1));//1,0
        // 获得数组的长度
        System.out.println(array.length());//5
        //将数组第i个下标设置为newValue，并返回旧的值
        System.out.println(array.getAndSet(1, 10));//0
        // 进行CAS操作，如果第i个下标的元素等于expect，则设置为update，设置成功返回true
        System.out.println(array.compareAndSet(1, 10, 20));
        // 将第i个下标的元素加1,返回旧值
        System.out.println(array.getAndIncrement(1));//20
        // 将第i个下标的元素减1,返回旧值
        System.out.println(array.getAndDecrement(1));//21
        // 将第i个下标的元素增加delta（delta可以是负数）,返回旧值
        System.out.println(array.getAndAdd(1, 5));//20
        // 将第i个下标的元素增加delta（delta可以是负数）,返回新值
        System.out.println(array.addAndGet(1, 5));//30
    }
}
