### 28.写时复制(CopyOnWrite)
> CopyOnWrite容器即写时复制的容器。CopyOnWrite是计算机程序设计领域中的一种优化策略。其核心思想是，如果有多个调用者（Callers）同时要求相同的资源（如内存或者是磁盘上的数据存储），
> 他们会共同获取相同的指针指向相同的资源，直到某个调用者试图修改资源内容时，系统才会真正复制一份专用副本（private copy）给该调用者，而其他调用者所见到的最初的资源仍然保持不变。
> 这过程对其他的调用者都是透明的（transparently）。此做法主要的优点是如果调用者没有修改资源，就不会有副本（private copy）被创建，因此多个调用者只是读取操作时可以共享同一份资源。              
>                   
> CopyOnWriteArrayList特点:                                                           
  CopyOnWriteArrayList是线程安全容器(相对于ArrayList)，底层通过复制数组的方式来实现。                          
  CopyOnWriteArrayList在遍历的使用不会抛出ConcurrentModificationException异常，并且遍历的时候就不用额外加锁。                             
  元素可以为null。                            
  CopyOnWriteArrayList底层就是数组，加锁就交由ReentrantLock(在jdk11的时候采用了synchronized来替换ReentrantLock)来完成。                              
  在修改时，复制出一个新数组，修改的操作在新数组中完成，最后将新数组交由array变量指向。                      
  写加锁，读不加锁。         
>                                        
> CopyOnWriteArrayList缺点:                              
  内存占用:如果CopyOnWriteArrayList经常要增删改里面的数据，经常要执行add()、set()、remove()的话，那是比较耗费内存的。
> 因为每次add()、set()、remove()这些增删改操作都要复制一个数组出来。如果这个数组对象占用的内存较大的话，频繁的进行写入就会造成频繁的Yong GC和Full GC。                    
  数据一致性:CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
>                                        
> CopyOnWriteArraySet的底层实现就是CopyOnWriteArrayList。               
>                   
> 应用场景:                 
  CopyOnWrite并发容器适用于读多写少的并发场景，比如黑白名单、国家城市等基础数据缓存、系统配置等。             
>               
> 参考 https://segmentfault.com/a/1190000016931825                
> https://segmentfault.com/a/1190000038751180               
