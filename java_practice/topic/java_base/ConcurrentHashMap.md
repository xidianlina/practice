### 21.HashMap和ConcurrentHashMap的区别？ConcurrentHashMap的实现原理？
> HashMap和ConcurrentHashMap的区别                          
  (1).HashMap不是线程安全的；ConcurrentHashMap是线程安全的和在并发环境下不需要加额外的同步                
  (2).ConcurrentHashMap有很好的扩展性，在多线程环境下性能方面比做了同步的HashMap要好，但是在单线程环境下，HashMap会比ConcurrentHashMap好一点                   
>
> ConcurrentHashMap的实现原理：               
> ConcurrentHashMap是Java并发包中提供的一个线程安全且高效的HashMap实现。             
  在jdk7中ConcurrentHashMap的底层数据结构是数组加链表。存放的数据是一段段的，由多个Segment(段)组成的。每个Segment中都是数组加链表的结构。                    
  一个Segment就是一个子哈希表，维护了一个HashEntry数组，Segment继承了可重入锁ReentrantLock。
>并发环境下，对于同一个Segment的操作才需考虑线程同步，不同的Segment则无需考虑。                         
  分段锁的优势在于保证在操作map的不同段时可以并发执行，操作map的同段时进行锁的竞争和等待。这相对于直接对整个map同步synchronized是有优势的。                       
  分段锁缺点在于分成很多段时会比较浪费内存空间(不连续，碎片化); 操作map时竞争同一个分段锁的概率非常小时，分段锁反而会造成更新等操作的长时间等待;
> 当某个段很大时，分段锁的性能会下降。
>                                                   
> ConcurrentHashMap有3个参数：                                     
  initialCapacity：初始总容量，默认16                
  loadFactor：加载因子，默认0.75                    
  concurrencyLevel：并发级别，默认16                    
  其中并发级别控制了Segment的个数，在一个ConcurrentHashMap创建后Segment的个数是不能变的，扩容过程过改变的是每个Segment的大小。                 
>
> 在jdk8中对ConcurrentHashMap进行了优化，采用了数组+链表+红黑树的实现方式，取消了Segment分段锁的数据结构。采用CAS+Synchronized保证线程安全。                  
  CAS一种基于乐观锁的操作，CAS包含3个参数CAS(V,E,N). V表示要更新的变量, E表示预期值, N表示新值。                  
  仅当V值等于E值时, 才会将V的值设为N, 如果V值和E值不同, 则说明已经有其他线程做了更新, 则当前线程不会更新V的值。                    
  当链表中元素个数超过默认设定（8个），当数组的大小还未超过64的时候，此时进行数组的扩容。                     
  当数组大小已经超过64并且链表中的元素个数超过默认设定（8个）时，链表将转化为红黑树。                   
>
> 为什么不用ReentrantLock而用synchronized ?                    
  减少内存开销:如果使用ReentrantLock则需要节点继承AQS来获得同步支持，增加内存开销，而1.8中只有头节点需要进行同步。                
  内部优化:synchronized则是JVM直接支持的，JVM能够在运行时作出相应的优化措施：锁粗化、锁消除、锁自旋等等。                 
>
> JDK1.8版本和JDK1.7版本对ConcurrentHashMap的实现上的不同:               
  (1).数据结构：取消了Segment分段锁的数据结构，取而代之的是数组+链表+红黑树的结构。           
  (2).保证线程安全机制：JDK1.7采用segment的分段锁机制实现线程安全，其中segment继承自ReentrantLock。JDK1.8采用CAS+Synchronized保证线程安全。                
  (3).锁的粒度：原来是对需要进行数据操作的Segment加锁，现调整为对每个数组元素加锁（Node）。              
  (4).链表转化为红黑树:定位结点的hash算法简化会带来弊端,Hash冲突加剧,因此在链表节点数量大于8时，会将链表转化为红黑树进行存储。                    
  (5).查询时间复杂度：从原来的遍历链表O(n)，变成遍历红黑树O(logN)。                  