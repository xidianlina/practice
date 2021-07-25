### 10.java多线程中join()方法？
```java
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
```
> join()方法的底层是利用wait()方法实现的。join方法是一个同步方法，当主线程调用t1.join()方法时，主线程先获得了t1对象的锁，随后进入方法，
> 调用了t1对象的wait()方法，使主线程进入了t1对象的等待池，此时，A线程则还在执行，并且随后的t2.start()还没被执行，因此，B线程也还没开始。
> 等到A线程执行完毕之后，主线程继续执行，走到了t2.start()，B线程才会开始执行。
