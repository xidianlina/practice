多线程
======
## 问题列表
### 1.并行和并发有什么区别？
### 2.线程和进程的区别？
### 3.守护线程是什么？
### 4.创建线程有哪几种方式？
### 5.runnable和callable有什么区别？
### 6.线程有哪些状态？
### 7.sleep()和wait()有什么区别？
### 8.notify()和notifyAll()有什么区别？
### 9.线程的run()和start()有什么区别？
### 10.创建线程池有哪几种方式？
### 11.线程池都有哪些状态？
### 12.线程池中submit()和execute()方法有什么区别？
### 13.什么是线程安全？在java程序中怎么保证多线程的运行安全？
### 14.多线程锁的升级原理是什么？
### 15.什么是死锁？怎么防止死锁？
### 16.ThreadLocal是什么？有哪些使用场景？
### 17.synchronized底层实现原理？
### 18.volatile关键字？
### 19.synchronized和volatile的区别是什么？
### 20.synchronized和Lock有什么区别？
### 21.synchronized和ReentrantLock区别是什么？
### 22.atomic的原理？
## 问题答案
### 1.并行和并发有什么区别？
> 并发性又称共行性，是指能处理多个同时性活动的能力，在同一个时间段内，两个或多个程序执行，有时间上的重叠（宏观上是同时，微观上仍是顺序执行）。
> 并发的实质是一个物理CPU（也可以多个物理CPU） 在若干道程序之间多路复用，并发性是对有限物理资源强制行使多用户共享以提高效率。                                 
  并行是指同时发生的两个并发事件，具有并发的含义，而并发则不一定并行，也亦是说并发事件之间不一定要同一时刻发生。                       
  操作系统并发程序执行的特点：                    
  并发环境下，由于程序的封闭性被打破，出现了新的特点                 
  (1).程序与计算不再一一对应，一个程序副本可以有多个计算             
  (2).并发程序之间有相互制约关系，直接制约体现为一个程序需要另一个程序的计算结果，间接制约体现为多个程序竞争某一资源，如处理机、缓冲区等。                
  (3).并发程序在执行中是走走停停，断续推进的               
  并发和并行的作用:                 
  通过并发和并行能够使得应用程序可以充分利用多核以及GPU的计算能力，从而提高应用程序的性能。比如:             
  (1).使用异步I/O操作可以提高应用程序的响应性。                
  (2).跨多线程的并行工作可以更好的利用系统的资源。                
  (3).同时执行多个I/O操作（如同时从多个网站上获取信息）可以提高总体的吞吐量（throughput），等待I/O相应的操作可以用来发起新的操作，或者是处理操作返回的结果。               
  并行和并发区别:                  
  (1).并发是指一个处理器同时处理多个任务。并行是指多个处理器或者是多核的处理器同时处理多个不同的任务。              
  并发是逻辑上的同时发生（simultaneous），而并行是物理上的同时发生。来个比喻：并发是一个人同时吃三个馒头，而并行是三个人同时吃三个馒头。             
  (2).并行(parallel):指在同一时刻，有多条指令在多个处理器上同时执行。就好像两个人各拿一把铁锨在挖坑，一小时后，每人一个大坑。所以无论从微观还是从宏观来看，二者都是一起执行的。                
  并发(concurrency):指在同一时刻只能有一条指令执行，但多个进程指令被快速的轮换执行，使得在宏观上具有多个进程同时执行的效果，
> 但在微观上并不是同时执行的，只是把时间分成若干段，使多个进程快速交替的执行。这就好像两个人用同一把铁锨，轮流挖坑，一小时后，两个人各挖一个小一点的坑，要想挖两个大一点得坑，一定会用两个小时。                       
  并行在多处理器系统中存在，而并发可以在单处理器和多处理器系统中都存在，并发能够在单处理器系统中存在是因为并发是并行的假象，并行要求程序能够同时执行多个操作，
> 而并发只是要求程序假装同时执行多个操作(每个小时间片执行一个操作，多个操作快速切换执行)。                 
  (3).当有多个线程在操作时，如果系统只有一个CPU，则它根本不可能真正同时进行一个以上的线程，它只能把CPU运行时间划分成若干个时间段，
> 再将时间段分配给各个线程执行，在一个时间段的线程代码运行时，其它线程处于挂起状态。这种方式我们称之为并发（Concurrent）。                         
  当系统有一个以上CPU时，则线程的操作有可能非并发。当一个CPU执行一个线程时，另一个CPU可以执行另一个线程，两个线程互不抢占CPU资源，可以同时进行，这种方式我们称之为并行（Parallel）。                       
### 2.线程和进程的区别？
> 进程是具有一定独立功能的程序关于某个数据集合上的一次运行活动，进程是系统进行资源分配和调度的一个独立单位。                      
> 线程是进程的一个实体，是CPU调度和分派的基本单位，它是比进程更小的能独立运行的基本单位。                 
> 线程自己基本上不拥有系统资源，只拥有一点在运行中必不可少的资源(如程序计数器,一组寄存器和栈)，但是它可与同属一个进程的其他的线程共享进程所拥有的全部资源。                    
> 进程与线程的区别:             
  进程是资源分配最小单位，线程是程序执行的最小单位；         
  进程有自己独立的地址空间，每启动一个进程，系统都会为其分配地址空间，建立数据表来维护代码段、堆栈段和数据段，线程没有独立的地址空间，它使用相同的地址空间共享数据；             
  CPU切换一个线程比切换进程花费小；            
  创建一个线程比进程开销小；             
  线程占用的资源要⽐进程少很多。               
  线程之间通信更方便，同一个进程下，线程共享全局变量，静态变量等数据，进程之间的通信需要以通信的方式（IPC）进行；（但多线程程序处理好同步与互斥是个难点）                 
  多进程程序更安全，生命力更强，一个进程死掉不会对另一个进程造成影响（源于有独立的地址空间），多线程程序更不易维护，一个线程死掉，整个进程就死掉了（因为共享地址空间）；               
  进程对资源保护要求高，开销大，效率相对较低，线程资源保护要求不高，但开销小，效率高，可频繁切换；       
>                   
> 参考 https://segmentfault.com/a/1190000022975740               
### 3.守护线程是什么？
> 守护线程就是守护用户线程，守护线程依赋于用户线程，当用户线程全部执行完结束之后，守护线程才会跟着结束。
```java
package com.java.topic.thread;

public class DaemonThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin running");
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
### 4.创建线程有哪几种方式？
> (1).继承Thread类创建线程类:                   
  定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。              
  创建Thread子类的实例，即创建了线程对象。               
  调用线程对象的start()方法来启动该线程。    
```java
package com.java.topic.thread;

public class ThreadExtendsThread extends Thread {
    public ThreadExtendsThread() {
        //编写子类的构造方法，可缺省
    }

    @Override
    public void run() {
        //编写执行体代码
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ThreadExtendsThread threadExtendsThread = new ThreadExtendsThread();
        threadExtendsThread.setName("thread1");
        threadExtendsThread.start();
        System.out.println(Thread.currentThread().toString());
    }
}
```           
> (2).实现Runnable接口创建线程类             
  定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。             
  创建Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。              
  调用线程对象的start()方法来启动该线程。
```java
package com.java.topic.thread;

public class ThreadImplementsRunnable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(new MyThread());
        thread.setName("我是通过实现接口的线程实现方式！");
        thread.start();
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
``` 
> 匿名内部类         
```java
package com.java.topic.thread;

public class ThreadRunnableInnerClass {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        System.out.println(Thread.currentThread().getName());
    }
}
```              
> (3).通过Callable和Future创建线程             
  创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。            
  创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。             
  使用FutureTask对象作为Thread对象的target创建并启动新线程。              
  调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。            
```java
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
```
> (4).通过线程池创建线程                 
```java
package com.java.topic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadByThreadPool {
    private static final int POOLNUM = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < POOLNUM; ++i) {
            RunnableThread thread = new RunnableThread();
            executorService.execute(thread);
        }

        //关闭线程池
        executorService.shutdown();
    }
}

class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println("通过线程池方式创建的线程：" + Thread.currentThread().getName());
    }
}
```
> 继承Thread类和实现Runnable接口的区别:                                   
> java是单继承的机制，如果选择使用继承Thread类的方式实现多线程，则无法再继承别的类实现对应的功能，而实现Runnable接口则不会有这样的问题。                      
  继承Thread类不适合资源共享,线程A不能把线程B的run方法当成是自己的执行单元;
> 而实现Runnable接口则适合实现资源共享,因为同一个Runnable可以构造多个实例，通俗的说可以理解Runnable相当于是Thread的task。                  
>                   
> 参考 https://segmentfault.com/a/1190000021253123                                                
### 5.runnable和callable有什么区别？
> Runnable接口中的run()方法的返回值是void，它做的事情只是纯粹地去执行run()方法中的代码而已；                  
  Callable接口中的call()方法是有返回值的，是一个泛型，和Future、FutureTask配合可以用来获取异步执行的结果。                   
### 6.线程有哪些状态？
> ![thread_status](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/thread_status.png)                   
> 线程通常都有五种状态，新建、就绪、运行、阻塞和死亡。                    
  新建状态:在生成线程对象，并没有调用该对象的start方法，这是线程处于创建状态。                 
  就绪状态:也被称为“可执行状态”,当调用了线程对象的start方法之后，该线程就进入了就绪状态，但是此时线程调度程序还没有把该线程设置为当前线程，
> 此时处于就绪状态。在线程运行之后，从等待或者睡眠中回来之后，也会处于就绪状态。处于就绪状态的线程，随时可能被CPU调度执行。                    
  运行状态:线程调度程序将处于就绪状态的线程设置为当前线程，此时线程就进入了运行状态，开始运行run函数当中的代码。             
  阻塞状态:线程正在运行的时候被暂停，是线程因为某种原因放弃CPU使用权，暂时停止运行,直到线程进入就绪状态，才有机会转到运行状态。
> 通常是为了等待某个事件的发生(比如说某项资源就绪)之后再继续运行。sleep,suspend，wait等方法都可以导致线程阻塞。                  
  阻塞的情况分三种：                 
  等待阻塞：通过调用线程的wait()方法，让线程等待某工作的完成。                 
  同步阻塞：线程在获取synchronized同步锁失败(因为锁被其它线程所占用)，它会进入同步阻塞状态。              
  其他阻塞：通过调用线程的sleep()或join()或发出了I/O请求时，线程会进入到阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。                  
  死亡状态:如果一个线程的run方法执行结束或者调用stop方法后，该线程就会死亡。对于已经死亡的线程，无法再使用start方法令其进入就绪状态。                   　　
>                                             
> 参考 https://segmentfault.com/a/1190000023573024                                
### 7.sleep()和wait()有什么区别？
> 方法是线程类（Thread）的静态方法,wait()是Object类的方法;                
> sleep()是static静态的方法，不能改变对象的锁，当一个synchronized块中调用了sleep()方法，线程虽然进入休眠，但是对象的锁没有被释放，其他线程依然无法访问这个对象,
> 当一个线程执行到wait方法时，它就进入到一个和该对象相关的等待池，同时释放对象的机锁，使得其他线程能够访问;               
> sleep()需要等到休眠时间结束后线程进入就绪状态，wait()需要通过notify，notifyAll方法来唤醒等待的线程;                                 
> wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”，而sleep()的作用是让当前线程由“运行状态”进入到“休眠(阻塞)状态”。
### 8.notify()和notifyAll()有什么区别？
> 当有线程调用了对象的notifyAll()方法（唤醒所有 wait 线程）或 notify()方法（只随机唤醒一个 wait 线程），被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。                                     
  调用了notify()后只要一个线程会由等待池进入锁池，而notifyAll会将该对象等待池内的所有线程移动到锁池中，等待锁竞争。                         
### 9.线程的run()和start()有什么区别？
> 每个线程都是通过某个特定Thread对象所对应的方法run()来完成其操作的，方法run()称为线程体。通过调用Thread类的start()方法来启动一个线程。                 
> start()方法来启动一个线程，真正实现了多线程运行。这时无需等待run方法体代码执行完毕，可以直接继续执行下面的代码，这时此线程是处于就绪状态， 并没有运行。
> 然后通过此Thread类调用方法run()来完成其运行状态， 这里方法run()称为线程体，它包含了要执行的这个线程的内容， run方法运行结束， 此线程终止。然后CPU再调度其它线程。                                 
>                       
> run()方法是在本线程里的，只是线程里的一个函数,而不是多线程的。 如果直接调用run(),其实就相当于是调用了一个普通函数而已，
> 直接调用run()方法必须等待run()方法执行完毕才能执行下面的代码，所以执行路径还是只有一条，根本就没有线程的特征，所以在多线程执行时要使用start()方法而不是run()方法。              
### 10.创建线程池有哪几种方式？
> 线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。              
> 线程池的体系结构：             
> java.util.concurrent.Executor : 负责线程的使用与调度的根接口                    
  |--ExecutorService 子接口: 线程池的主要接口                  
  |--ThreadPoolExecutor 线程池的实现类             
  |--ScheduledExecutorService 子接口：负责线程的调度                   
  |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService                                        
> ![thread_pool](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/thread_pool.png)                 
> 创建线程池的方式主要是通过调用工具类Executors的四个静态方法:
> ExecutorService newFixedThreadPool():创建一个固定长度的线程池，每当提交一个任务就创建一个线程，直到达到线程池的最大数量，这时线程规模将不再变化，当线程发生未预期的错误而结束时，线程池会补充一个新的线程。                  
> ExecutorService newCachedThreadPool():缓存线程池,创建一个可缓存的线程池，如果线程池的规模超过了处理需求，将自动回收空闲线程，而当需求增加时，则可以自动添加新线程，线程池的规模不存在任何限制。                   
> ExecutorService newSingleThreadExecutor():创建单个线程池。线程池中只有一个线程。它创建单个工作线程来执行任务，如果这个线程异常结束，会创建一个新的来替代它；它的特点是能确保依照任务在队列中的顺序来串行执行。              
> ScheduledExecutorService newScheduledThreadPool():创建固定大小的线程，可以延迟或定时的执行任务，类似于Timer。                
> 线程池的作用：                       
  降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。                                
  提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。                
  提高线程的可管理性。                                     
### 11.线程池都有哪些状态？
> 线程池有5种状态：Running、ShutDown、Stop、Tidying、Terminated。                
> ![thread_pool_status](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/thread_pool_status.png)                   
> running:                  
  线程池处在running状态时，能够接收新任务，以及对已添加的任务进行处理。                
  线程池的初始化状态是running。换句话说，线程池被一旦被创建，就处于running状态，并且线程池中的任务数为0。               
  shutdown:                 
  线程池处在shutdown状态时，不接收新任务，但能处理已添加的任务。               
  调用线程池的shutdown()接口时，线程池由running > shutdown                
  stop:                           
  线程池处在stop状态时，不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。                
  调用线程池的shutdownNow()接口时，线程池由running或 shutdown > stop               
  tidying:              
  当所有的任务已终止，ctl的任务数量为0，线程池会变为tidying状态。当线程池变为tidying状态时，会执行钩子函数terminated()。
> terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为tidying时，进行相应的处理；可以通过重载terminated()函数来实现。              
  当线程池在shutdown状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 shutdown -> tidying                    
  当线程池在stop状态下，线程池中执行的任务为空时，就会由stop > tidying               
  terminated                
  线程池彻底终止，就变成terminated状态                   
  线程池处在tidying状态时，执行完terminated()之后，就会由 tidying > terminated                                
>                                       
> 参考 https://segmentfault.com/a/1190000023332793                
### 12.线程池中submit()和execute()方法有什么区别？
> public interface Executor {                   
      void execute(Runnable command);               
  }                     
>                       
> ![thread_pool_submit](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/thread_pool_submit.png)               
> submit()和 execute()接收的参数不一样           
> submit有返回值，而execute没有                 
> submit方便Exception处理。execute直接抛出异常之后线程就死掉了，submit保存异常线程没有死掉，因此execute的线程池可能会出现没有意义的情况，因为线程没有得到重用。而submit不会出现这种情况。                                                   
### 13.什么是线程安全？在java程序中怎么保证多线程的运行安全？
> 如果一段代码所在的进程中有多个线程在同时运行，而这些线程可能会同时运行这段代码。如果每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。                 
>               
> 线程安全在三个方面体现：                  
  原子性：提供互斥访问，同一时刻只能有一个线程对数据进行操作，（atomic,synchronized）；                  
  可见性：一个线程对主内存的修改可以及时地被其他线程看到，（synchronized,volatile）；                  
  有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序，该观察结果一般杂乱无序，（happens-before原则）。                    
>               
> 保证线程安全的方法:            
> (1).使用线程安全的类；         
> (2).使用synchronized同步代码块，或者用Lock锁；由于线程安全问题，使用synchronized同步代码块时，
> 当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。
> 另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。                          
> (3).多线程并发情况下，线程共享的变量改为方法局部级变量；            
> (4).使用线程局部变量ThreadLocal。
### 14.多线程锁的升级原理是什么？
> JVM优化synchronized的运行机制，当JVM检测到不同的竞争状态时，就会根据需要自动切换到合适的锁，这种切换就是锁的升级。                
> 在Java中，锁共有4种状态，级别从低到高依次为：无状态锁，偏向锁，轻量级锁和重量级锁状态，这几个状态会随着竞争情况逐渐升级。锁可以升级但不能降级,是不可逆的。                  
> ![lock](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/lock.png)                   
> synchronized可用来给对象和方法或者代码块加锁，当它锁定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码。                       
> synchronized有三种应用方式：
> 作用于实例方法，当前实例加锁，进入同步代码前要获得当前实例的锁；                  
  作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁；                  
  作用于代码块，对括号里配置的对象加锁。                   
>                           
> Java对象头                       
  synchronized用的锁存在Java对象头里，Java对象头里的Mark Word默认存储对象的HashCode、分代年龄和锁标记位。
> 在运行期间，Mark Word里存储的数据会随着锁标志位的变化而变化。32位JVM的Mark Word可能变化存储为以下5种数据：                     
> ![lock2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/lock2.png)                 
>                       
> CAS算法:                
> compareAndSwap，比较并替换，是一种实现并发算法时常用到的技术。CAS需要有3个操作数：内存地址V，旧的预期值A，即将要更新的目标值B；
> 比如你要操作一个变量，他的值为A，你希望将他修改为B，这期间不会进行加锁，当你在修改的时候，你发现值仍旧是A，然后将它修改为B，
> 如果此时值被其他线程修改了，变成了C，那么将不会进行值B的写入操作，这就是CAS的核心理论，通过这样的操作可以实现逻辑上的一种“加锁”，避免了真正去加锁。                         
````java
package com.java.topic.thread;

public class CASTest {
    public final int incrementAndGet() {
        for (; ; ) {                    //自旋
            int current = get();        //旧值
            int next = current + 1;     //新值
            if (compareAndSet(current, next))  //如果旧的预期值与内存中的值一致，那么将新值进行赋值，否则继续自旋
                return next;
        }

    }
}
````                       
>                   
> 偏向锁:              
> 当一个线程访问同步块并获取锁时，会在对象头和栈帧的锁记录里存储偏向的线程ID，以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁，
> 只需测试Mark Word里线程ID是否为当前线程。如果测试成功，表示线程已经获得了锁。如果测试失败，则需要判断偏向锁的标识。
> 如果标识被设置为0（表示当前是无锁状态），则使用CAS竞争锁；如果标识设置成1（表示当前是偏向锁状态），则尝试使用CAS将对象头的偏向锁指向当前线程，
> 触发偏向锁的撤销。偏向锁只有在竞争出现才会释放锁。当其他线程尝试竞争偏向锁时，程序到达全局安全点后（没有正在执行的代码），
> 它会查看Java对象头中记录的线程是否存活，如果没有存活，那么锁对象被重置为无锁状态，其它线程可以竞争将其设置为偏向锁；
> 如果存活，那么立刻查找该线程的栈帧信息，如果还是需要继续持有这个锁对象，那么暂停当前线程，撤销偏向锁，升级为轻量级锁，
> 如果线程不再使用该锁对象，那么将锁对象状态设为无锁状态，重新偏向新的线程。                         
> 偏向锁应用的场景是一个同步代码块只有一个线程频繁访问，使用偏向锁，就不需要频繁使用CAS获取锁和释放锁，
> 只需要简单判断对象头中记录的偏向锁的线程ID是否是当期线程的就可以了，所以偏向锁在这种场景下可以大大提升效率。       
>                   
> 线程在执行同步块之前，JVM会先在当前线程的栈帧中创建用于存储锁记录的空间，并将对象头的MarkWord复制到锁记录中，即Displaced Mark Word。
> 然后线程会尝试使用CAS将对象头中的Mark Word替换为指向锁记录的指针。如果成功，当前线程获得锁。如果失败，表示其他线程在竞争锁，
> 当前线程使用自旋来获取锁。当自旋次数达到一定次数时，锁就会升级为重量级锁。                     
> 当线程存在竞争时，偏向锁的效率就会降低，因为当多条线程竞争同一个偏向锁时，会频繁产生偏向锁的撤销，所以此时应该升级为轻量级锁，
> 轻量级锁当线程竞争锁失败时，线程不会阻塞进入自旋，继续获取锁，当竞争非常激烈时，持续自旋而获取不到锁会消耗大量CPU资源，
> 此时就会升级为重量级锁，重量级锁当获取锁失败线程会阻塞，重量级锁的缺点是线程上下文会频繁的切换。          
>                           
> synchronized优化-锁消除:                       
> 消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时(可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译)，
> 通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁，通过这种方式消除没有必要的锁，可以节省毫无意义的请求锁时间。
```java
public class Test extends Thread{
    public static void main(String[] args) {
        contactString("aa", "bb", "cc");
    }
    public static String contactString(String s1, String s2, String s3) {
        return new StringBuffer().append(s1).append(s2).append(s3).toString();
    }
}
```
> StringBuffer的append是一个同步方法，锁就是this也就是(new StringBuilder()),但是在contactString方法中的StringBuffer属于一个局部变量，
> 并且不会被其他线程所使用，因此StringBuffer不可能存在共享资源竞争的情景，JVM会自动将其锁消除。                
>                       
> 自旋锁：              
> 是指当一个线程在获取锁的时候，如果锁已经被其它线程获取，那么该线程将循环等待，然后不断的判断锁是否能够被成功获取，直到获取到锁才会退出循环。                     
> 获取锁的线程一直处于活跃状态，但是并没有执行任何有效的任务，使用这种锁会造成busy-waiting。它是为实现保护共享资源而提出一种锁机制。                           
> 自旋锁与互斥锁比较类似，它们都是为了解决对某项资源的互斥使用。无论是互斥锁，还是自旋锁，在任何时刻，最多只能有一个保持者，
> 也就说，在任何时刻最多只能有一个执行单元获得锁。但是两者在调度机制上略有不同。对于互斥锁，如果资源已经被占用，资源申请者只能进入睡眠状态。
> 但是自旋锁不会引起调用者睡眠，如果自旋锁已经被别的执行单元保持，调用者就一直循环在那里看是否该自旋锁的保持者已经释放了锁，"自旋"一词就是因此而得名。                                                              
>                           
> 参考 https://www.cnblogs.com/jxxblogs/p/11890563.html                                                                                                       
### 15.什么是死锁？怎么防止死锁？
> 
### 16.ThreadLocal是什么？有哪些使用场景？
> 
### 17.synchronized底层实现原理？
> 
### 18.volatile关键字？
> 
### 19.synchronized和volatile的区别是什么？
> 
### 20.synchronized和Lock有什么区别？
> 
### 21.synchronized和ReentrantLock区别是什么？
> 
### 22.atomic的原理？
> 