### 21.synchronized和Lock有什么区别？
> (1).原始构成不同:synchronized是关键字，属于JVM层面，底层是通过monitor对象来完成(其实wait/notify等方法也依赖于monitor对象,只有在同步块或者方法中才能调用wait/notify等方法)。                   
  lock是具体类(java.util.concurrent.locks.lock),是api层面的锁,是一个接口。                 
  (2).synchronized不需要用户去手动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用。               
  ReentrantLock则需要用户去手动释放锁，若没有主动释放锁，就有可能导致出现死锁现象。需要lock()和unlock()方法配合try/finally语句块来完成。                
  (3).synchronized不可中断，除非抛出异常或者正常运行完成。                  
  ReentrantLock可中断。设置超时方法tryLock(long timeout,TimeUnit unit);lockInterruptibly()放代码块中，调用interrupt()方法可中断。               
  (4).synchronized无法知道是否成功获取锁;Lock可以知道有没有成功获取锁。             
  (5).synchronized非公平锁。ReentrantLock两者都可以，默认非公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁。              
  (6).性能上来说，在资源竞争不激烈的情形下，Lock性能稍微比synchronized差点（编译程序通常会尽可能的进行优化synchronized）。但是当同步非常激烈的时候，synchronized的性能一下子能下降好几十倍。而ReentrantLock确还能维持常态。                 
  (7).Lock可以提高多个线程进行读操作的效率。(可以通过readwritelock实现读写分离)                
  (8).用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。
> 如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了。                 
> (9).ReentrantLock用来实现分组唤醒需要唤醒的线程，可以精确唤醒，而不是像synchronized要么随机唤醒一个要么唤醒全部线程。                 
> (10).Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。                                     
>               
> 公平锁即尽量以请求锁的顺序来获取锁。同时有多个线程在等待一个锁，当这个锁被释放时，等待时间最久的线程（最先请求的线程）会获得该锁，这种就是公平锁。                     
  非公平锁是无法保证锁的获取是按照请求锁的顺序进行的，这样就可能导致某个或者一些线程永远获取不到锁。                 
  synchronized是非公平锁，它无法保证等待的线程获取锁的顺序。对于ReentrantLock和ReentrantReadWriteLock，默认情况下是非公平锁，但是可以设置为公平锁。          
> 公平锁和非公平锁的实现思想:            
> 公平锁把竞争的线程放在一个先进先出的队列上，持有锁的线程执行完了唤醒队列的下一个线程去获取锁。           
> 非公平锁是线程先尝试获取锁，如果获取到了锁就执行同步代码，如果获取不到锁再把这个线程放到队列。           
> 公平锁和非公平锁到区别就是线程执行同步代码块时，是否会先尝试获取锁，如果会先尝试获取锁，就是非公平锁。如果不会尝试获取锁，而是直接进入队列，
> 再等待唤醒，就是公平锁。                                    
>                           
```java
package com.java.topic.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; ++i) {
                shareResource.print3();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; ++i) {
                shareResource.print4();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; ++i) {
                shareResource.print5();
            }
        }, "C").start();
    }
}

class ShareResource {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print3() {
        lock.lock();
        try {
            //1.判断
            while (number != 1) {
                c1.await();
            }

            //2.工作
            for (int i = 1; i <= 3; ++i) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            //3.通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print4() {
        lock.lock();
        try {
            //1.判断
            while (number != 2) {
                c2.await();
            }

            //2.工作
            for (int i = 1; i <= 4; ++i) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            //3.通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print5() {
        lock.lock();
        try {
            //1.判断
            while (number != 3) {
                c3.await();
            }

            //2.工作
            for (int i = 1; i <= 5; ++i) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            //3.通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```                  
> 参考 https://zhuanlan.zhihu.com/p/279315342                        
