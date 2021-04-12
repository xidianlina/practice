package com.java.topic.thread;

public class CASTest {
    /*
    public final int incrementAndGet() {
        for (; ; ) {                    //自旋
            int current = get();        //旧值
            int next = current + 1;     //新值
            if (compareAndSet(current, next))  //如果旧的预期值与内存中的值一致，那么将新值进行赋值，否则继续自旋
                return next;
        }

    }
     */
}
