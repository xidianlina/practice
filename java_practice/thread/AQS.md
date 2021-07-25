### 26.AQS
>AQS(AbstractQueuedSynchronizer),抽象的队列同步器，AQS定义了一套多线程访问共享资源的同步器框架，
>许多同步类实现都依赖于它，如常用的ReentrantLock/Semaphore/CountDownLatch...。                        
>           
>核心思想:                      
 如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并将共享资源设置为锁定状态，如果被请求的共享资源被占用，
>那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制AQS是用CLH队列锁实现的，即将暂时获取不到锁的线程加入到队列中。                   
 CLH（Craig，Landin，and Hagersten）队列是一个虚拟的双向队列，虚拟的双向队列即不存在队列实例，仅存在节点之间的关联关系。                          
 AQS是将每一条请求共享资源的线程封装成一个CLH锁队列的一个结点（Node），来实现锁的分配。                           
>       
>AQS就是基于CLH队列，用volatile修饰共享变量state，线程通过CAS去改变状态符，成功则获取锁成功，失败则进入等待队列，等待被唤醒。                      
 AQS是自旋锁：线程在等待唤醒的时候，经常会使用自旋（while(!cas())）的方式，不停地尝试获取锁，直到被其他线程获取成功                  
 实现了AQS的锁有：自旋锁、互斥锁、读锁写锁、条件产量、信号量、栅栏                 
>           
>AQS维护了一个volatile int state和一个FIFO线程等待队列，多线程争用资源被阻塞的时候就会进入这个队列。state就是共享资源，其访问方式有如下三种：                                
 getState();            
>setState();        
>compareAndSetState();            
>               
>AQS定义了两种资源共享方式：                                    
 Exclusive：独占，只有一个线程能执行，如ReentrantLock                  
 Share：共享，多个线程可以同时执行，如Semaphore、CountDownLatch、ReadWriteLock，CyclicBarrier                      