### 17.synchronized底层实现原理？
> [1].synchronized作用                    
  原子性：synchronized保证语句块内操作是原子的                  
  可见性：synchronized保证可见性（通过“在执行unlock之前，必须先把此变量同步回主内存”实现）                
  有序性：synchronized保证有序性（通过“一个变量在同一时刻只允许一条线程对其进行lock操作”）             
  [2].synchronized的使用                           
> synchronized可用来给对象和方法或者代码块加锁，当它锁定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码，同时它还可以保证共享变量的内存可见性。                                           
> Java中每一个对象都可以作为锁，这是synchronized实现同步的基础。               
> synchronized有三种应用方式：                  
> 作用于实例方法，当前实例加锁，进入同步代码前要获得当前实例的锁；                                
  作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁；                                
  作用于代码块，对括号里配置的对象加锁。                                   
> [3].实现原理                  
> synchronized是JVM实现的一种互斥同步访问方式，底层是基于每个对象的监视器(monitor)来实现的。                 
  被synchronized修饰的代码，被编译器编译后在前后加上了一组字节指令。在代码开始前加入了monitorenter，在代码后面加入了monitorexit。                 
  当monitor被占用时就会处于锁定状态，线程执行monitorenter指令时尝试获取monitor的所有权，过程如下：             
  a.如果monitor的进入数为0，则该线程进入monitor，然后将进入数设置为1，该线程即为monitor的所有者。                  
  b.如果线程已经占有该monitor，只是重新进入，则进入monitor的进入数加1.                   
  c.如果其他线程已经占用了monitor，则该线程进入阻塞状态，直到monitor的进入数为0，再重新尝试获取monitor的所有权。                   
  执行monitorexit的线程必须是objectref所对应的monitor的所有者。                  
  指令执行时，monitor的进入数减1，如果减1后进入数为0，那线程退出monitor，不再是这个monitor的所有者。其他被这个monitor阻塞的线程可以尝试去获取这个monitor的所有权。                   
  
  ①synchronized 是可重入的，所以不会自己把自己锁死
  ②synchronized 锁一旦被一个线程持有，其他试图获取该锁的线程将被阻塞

> ![synchronized](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/synchronized.png)                    
>                   
> 参考 https://github.com/farmerjohngit/myblog/issues/12        
