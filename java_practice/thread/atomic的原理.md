### 23.atomic的原理？
> Atomic包中的类基本的特性就是在多线程环境下，当有多个线程同时对单个（包括基本类型及引用类型）变量进行操作时，具有排他性，
> 即当多个线程同时对该变量的值进行更新时，仅有一个线程能成功，而未成功的线程可以向自旋锁一样，继续尝试，一直等到执行成功。              
  Atomic系列的类中的核心方法都会调用unsafe类中的几个本地方法，通过CAS操作来封装实现的。      
>                        
> Java是编译型语言，不像C语言能支持操作内存，正常情况下都是由JVM进行内存的创建回收等操作，但unsafe类提供了一些直接操作内存相关的底层操作，
> 使得可以手动操作内存，但从类的名字就可以看出，这个类不是安全的，官方也是不建议使用的。               
>                   
> CAS包含3个参数CAS(V,E,N). V表示要更新的变量, E表示预期值, N表示新值。            
> 仅当V值等于E值时, 才会将V的值设为N, 如果V值和E值不同, 则说明已经有其他线程做了更新, 则当前线程什么都不做。
> 最后, CAS返回当前V的真实值. CAS操作是抱着乐观的态度进行的, 它总是认为自己可以成功完成操作。              
> 当多个线程同时使用CAS操作一个变量时, 只有一个会胜出, 并成功更新, 其余均会失败.失败的线程不会被挂起,仅是被告知失败, 并且允许再次尝试,
> 当然也允许失败的线程放弃操作.基于这样的原理, CAS操作即时没有锁,也可以发现其他线程对当前线程的干扰, 并进行恰当的处理。           
>                             
> 在JDK8的atomic包下，大概有16个类，按照原子的更新方式，大概可以分为4类：原子更新普通类型，原子更新数组，原子更新引用，原子更新字段。              
> 原子更新普通类型:             
> atomic 包下提供了三种基本类型的原子更新，分别是 AtomicBoolean，AtomicInteger，AtomicLong，这几个原子类对应于基础类型的布尔，
> 整形，长整形，至于 Java 中其他的基本类型，如 float 等，如果需要，可以参考这几个类的源码自行实现。      
```java
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
```                             
> 原子更新数组:                   
  atomic 包下提供了三种数组相关类型的原子更新，分别是 AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray，
> 对应于整型，长整形，引用类型，要说明的一点是，这里说的更新是指更新数组中的某一个元素的操作。     
```java
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
```                                        
> 原子更新引用:               
> 更新引用类型的原子类包含了AtomicReference（更新引用类型），AtomicReferenceFieldUpdater（抽象类，更新引用类型里的字段），
> AtomicMarkableReference（更新带有标记的引用类型）这三个类，这几个类能同时更新多个变量。      
```java
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
```                           
> 原子更新字段: 如果更新的时候只更新对象中的某一个字段，则可以使用atomic包提供的更新字段类型：AtomicIntegerFieldUpdater，AtomicLongFieldUpdater 
> 和AtomicStampedReference，前两个是更新int和long类型，最后一个是更新引用类型，该类提供了版本号，用于解决通过CAS 进行原子更新过程中，可能出现的ABA问题。
> 前面两个类AtomicReferenceFieldUpdater有些相似，都是抽象类，都需要通过newUpdater方法进行实例化，并且对字段的要求也是一样的。                  
>                   
> ABA问题:                
> 线程1准备用CAS将变量的值由A替换为B, 在此之前线程2将变量的值由A替换为C, 线程3又将C替换为A, 然后线程1执行CAS时发现变量的值仍然为A, 所以线程1CAS成功。                
>           
```java
package com.java.topic.thread;

import java.util.concurrent.atomic.AtomicStampedReference;

//要求:后台使用多个线程对用户充值, 要求只能充值一次
public class AtomicStampedReferenceTest {
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        //模拟多个线程同时更新后台数据库，为用户充值
        for (int i = 0; i < 3; i++) {
            // 获得当前时间戳
            final int timestamp = money.getStamp();
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        while (true) {
                            // 获得当前对象引用
                            Integer m = money.getReference();
                            if (m < 20) {
                                // 比较设置 参数依次为：期望值 写入新值 期望时间戳 新时间戳
                                if (money.compareAndSet(m, m + 20, timestamp, timestamp + 1)) {
                                    System.out.println("余额小于20元，充值成功，余额:" + money.getReference() + "元");
                                    break;
                                }
                            } else {
                                System.out.println("余额大于20元，无需充值");
                                break;
                            }
                        }
                    }
                }
            }.start();
        }

        //用户消费线程，模拟消费行为
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    while (true) {
                        int timestamp = money.getStamp();
                        Integer m = money.getReference();
                        if (m > 10) {
                            System.out.println("大于10元");
                            if (money.compareAndSet(m, m - 10, timestamp, timestamp + 1)) {
                                System.out.println("成功消费10元，余额:" + money.getReference());
                                break;
                            }
                        } else {
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }
}
```         
> AtomicIntegerFieldUpdater能够让普通变量也能够进行原子操作。  
```java
package com.java.topic.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {
    public static class Candidate {
        int id;
        volatile int score;
    }

    // 通过反射实现
    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
    // 检查Updater是否工作正确, allScore的结果应该跟score一致
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate();
        Thread[] t = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            t[i] = new Thread() {
                public void run() {
                    if (Math.random() > 0.4) {
                        scoreUpdater.incrementAndGet(stu);
                        allScore.incrementAndGet();
                    }
                }
            };
            t[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            t[i].join();
        }

        System.out.println("score=" + stu.score);
        System.out.println("allScore=" + allScore);
    }
}
```              
> AtomicIntegerFieldUpdater只能修改它可见范围内的变量。因为Updater使用反射得到这个变量。如果变量不可见，就会出错。比如如果score申明为private，就是不可行的。               
  为了确保变量被正确的读取，它必须是volatile类型的。如果我们原有代码中未申明这个类型，那么简单得申明一下就行。                    
  由于CAS操作会通过对象实例中的偏移量直接进行赋值，因此，它不支持static字段（Unsafe.objectFieldOffset()不支持静态变量）。             
> ![atomic](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/atomic.png)               
>                       
>推荐使用LongAdder对象，比AtomicLong性能更好                
>AtomicLong做累加时实际就是多个线程操作同一个目标资源。在高并发时，只有一个线程是执行成功的，其他线程都会失败，不断自旋重试，自旋会成为瓶颈             
>而LongAdder的思想是把要操作的目标资源分散到数组Cell中，每个线程对自己的Cell变量的value进行原子操作，大大降低了失败的次数。                            
>                                         
> 参考 https://segmentfault.com/a/1190000021155407                                       
