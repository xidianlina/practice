### 16.ThreadLocal是什么？有哪些使用场景？
> ThreadLocal线程局部变量，当多线程需要多次使用同一个对象，并且需要该对象具有相同初始化值的时候最适合使用ThreadLocal。                              
> ThreadLocal在每个线程中对该对象会创建一个副本，即每个线程内部都会有一个该对象，且在线程内部任何地方都可以使用，线程之间互不影响，不存在线程安全问题。
> 但是由于在每个线程中都创建了对象副本，对资源的消耗比较大，比如内存的占用会比不使用ThreadLocal要大。           
> 每个线程对象内部都维护了一个ThreadLocalMap这样一个ThreadLocal的Map，可以存放若干个ThreadLocal。                   
> ThreadLocal中重要的方法有get()和set()方法，当调用get()方法的时候，先获取当前线程，然后获取到当前线程的ThreadLocalMap对象，如果非空，
> 那么就以当前线程的ThreadLocal对象作为key取出value，否则进行初始化，初始化就是将initialValue的值set到ThreadLocal中。              
> 每个线程维护的ThreadLocalMap对象的键就是ThreadLocal对象实例，值就是set的那个对象，每次线程在get的时候，都从自己的对象中取值，不存在线程安全问题。
> ThreadLocal这个变量的状态根本没有发生变化，它仅仅是充当一个key的角色，提供给每一个线程一个初始值。ThreadLocal是通过final类型的threadLocalHashCode变量来区分不同的ThreadLocal实例。                   
>               
> 最常见的ThreadLocal使用场景为用来解决数据库连接、Session管理等。               
>                                                      
> 参考 https://www.cnblogs.com/dolphin0520/p/3920407.html                 
> https://www.cnblogs.com/dreamroute/p/5034726.html             
> https://www.cnblogs.com/xidian2014/p/10322239.html                                               
