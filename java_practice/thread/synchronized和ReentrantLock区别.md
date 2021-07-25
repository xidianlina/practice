### 22.synchronized和ReentrantLock区别是什么？
> (1).synchronized是和if、else、for、while一样的关键字，ReentrantLock是类，这是二者的本质区别。                 
> (2).锁机制不一样:ReentrantLock底层调用的是Unsafe的park方法加锁，synchronized操作的应该是对象头中mark word。                
> (3).ReentrantLock是类，提供了比synchronized更多更灵活的特性，可以被继承、可以有方法、可以有各种各样的类变量，
> ReentrantLock比synchronized的扩展性体现在：ReentrantLock可以对获取锁的等待时间进行设置，这样就避免了死锁;
> ReentrantLock可以获取各种锁的信息;ReentrantLock可以灵活地实现多路通知。
>                   
> synchronized 竞争锁时会一直等待；ReentrantLock 可以尝试获取锁，并得到获取结果                  
  synchronized 获取锁无法设置超时；ReentrantLock 可以设置获取锁的超时时间                 
  synchronized 无法实现公平锁；ReentrantLock 可以满足公平锁，即先等待先获取到锁                                
  synchronized 控制等待和唤醒需要结合加锁对象的 wait() 和 notify()、notifyAll()；ReentrantLock 控制等待和唤醒需要结合 Condition 的 await() 和 signal()、signalAll() 方法                   
  synchronized 是 JVM 层面实现的；ReentrantLock 是 JDK 代码层面实现                   
  synchronized 在加锁代码块执行完或者出现异常，自动释放锁；ReentrantLock 不会自动释放锁，需要在 finally{} 代码块显示释放                         
