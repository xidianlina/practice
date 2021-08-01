### 20.HashMap的实现原理？HashSet的实现原理？
> HashMap(): 构建一个空的哈希映像                         
  HashMap(Map m): 构建一个哈希映像，并且添加映像m的所有映射                 
  HashMap(int initialCapacity): 构建一个拥有特定容量的空的哈希映像                   
  HashMap(int initialCapacity, float loadFactor): 构建一个拥有特定容量和加载因子的空的哈希映像                
> 为了优化HashMap空间的使用，您可以调优初始容量和负载因子。              
> ![hashmap2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap2.png)           
> 当新建一个hashmap的时候，就会初始化一个数组。                
> ![hashmap3](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap3.png)               
> Entry就是数组中的元素，它持有一个指向下一个元素的引用，这就构成了链表。                
>               
>           
> (1).HashMap的数据结构                                
> HashMap基于哈希表实现，jdk1.7之前是一个数组和链表的结合体。                  
> JDK1.8中对HashMap进行了优化。jdk1.8之后是数组、链表、红黑树的结合体。              
> 当链表长度太长（TREEIFY_THRESHOLD默认超过8）时，链表就转换为红黑树，利用红黑树快速增删改查的特点提高HashMap的性能,从原来的O(n)到O(logn)。
>当长度小于（UNTREEIFY_THRESHOLD默认为6），就会退化成链表。                        
  HashMap 中关于红黑树的三个关键参数                 
> ![hashmap5](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap5.png)               
>
> (2)key的hashcode与equals方法改写                
> 使用HashMap要求添加的键类明确定义了hashCode()和equals()(可以重写hashCode()和equals())         
> 当往hashmap中put元素时，先根据key的hash值得到这个元素在数组中的位置（即下标），然后就可以把这个元素放到对应的位置中了。          
> 如果这个元素所在的位置上已经存放有其他元素了，那么在同一个位置上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。          
> 从hashmap中get元素时，首先计算key的hashcode，找到数组中对应位置的某一元素，然后通过key的equals方法在对应位置的链表中找到需要的元素。     
> 如果每个位置上的链表只有一个元素，那么hashmap的get效率将是最高的。            
>
> 所以，hashcode与equals方法对于找到对应元素是两个关键方法。                  
  Hashmap的key可以是任何类型的对象，但一定要是不可变对象。                  
  在改写equals方法的时候，需要满足以下三点：              
  自反性：就是说a.equals(a)必须为true。            
  对称性：就是说a.equals(b)=true的话，b.equals(a)也必须为true。            
  传递性：就是说a.equals(b)=true，并且b.equals(c)=true的话，a.equals(c)也必须为true。         
  通过改写key对象的equals和hashcode方法，可以将任意的业务对象作为map的key(前提是你确实有这样的需要)。            
>
> (3).hash算法            
> 在hashmap中要找到某个元素，需要根据key的hash值来求得对应数组中的位置。希望这个hashmap里面的元素位置尽量的分布均匀些，         
> 尽量使得每个位置上的元素数量只有一个，那么当用hash算法求得这个位置时，马上就可以知道对应位置的元素就是想要的，而不用再去遍历链表,效率将是最高的。               
  首先想到的就是把hashcode对数组长度取模运算，这样一来，元素的分布相对来说是比较均匀的。但是，“模”运算的消耗还是比较大的。             
> 所以Java中是这样做的：hashmap的数组初始化大小都是2的次方大小。首先算得key的hashcode值，然后跟数组的长度-1做一次“与”运算（&）。             
> 减少碰撞的几率，提高查询的效率。              
> 2的次方是偶数，减1之后是奇数，奇数的二进制末尾是1，与key的hashcode值做“与”运算不易产生空间浪费。                
>               
> static int indexFor(int h, int length) {                                      
         return h & (length-1);                               
  }                         
  看上去很简单，其实比较有玄机。比如数组的长度是2的4次方，那么hashcode就会和2的4次方-1做“与”运算。                                    
  为什么hashmap的数组初始化大小都是2的次方大小时，hashmap的效率最高？                   
  以2的4次方举例，来解释一下为什么数组大小为2的幂时hashmap访问的性能最高。                         
  看下图，左边两组是数组长度为16（2的4次方），右边两组是数组长度为15。两组的hashcode均为8和9，但是很明显，当它们和1110“与”的时候，           
> 产生了相同的结果，也就是说它们会定位到数组中的同一个位置上去，这就产生了碰撞，8和9会被放到同一个链表上，那么查询的时候就需要遍历这个链表，                
> 得到8或者9，这样就降低了查询的效率。当数组长度为15的时候，hashcode的值会与14（1110）进行“与”，那么最后一位永远是0，              
> 而0001，0011，0101，1001，1011，0111，1101这几个位置永远都不能存放元素了，空间浪费相当大，更糟的是这种情况中，         
> 数组可以使用的位置比数组长度小了很多，这意味着进一步增加了碰撞的几率，减慢了查询的效率！              
> ![hashmap4](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap4.png)                               
> 所以当数组长度为2的n次幂的时候，不同的key算得得index相同的几率较小，那么数据在数组上分布就比较均匀，也就是说碰撞的几率小，相对的，查询的时候就不用遍历某个位置上的链表，这样查询效率也就较高了。                                              
  在存储大容量数据的时候，最好预先指定hashmap的size为2的整数次幂次方。就算不指定的话，也会以大于且最接近指定值大小的2次幂来初始化的。                            
>                   
> (4).HashMap的resize                
> 当hashmap中的元素越来越多时，碰撞的几率也就越来越高（因为数组的长度是固定的），所以为了提高查询的效率，就要对hashmap的数组进行扩容。             
> 当hashmap中的元素个数超过数组大小 x loadFactor时，就会进行数组扩容，loadFactor的默认值为0.75。              
> 在hashmap数组扩容之后，最消耗性能的点就是原数组中的数据必须重新计算其在新数组中的位置，并放进去。          
> 所以如果已经预知hashmap中元素的个数，预设元素的个数能够有效的提高hashmap的性能。           
> 比如说，有1000个元素new HashMap(1000), 但是理论上来讲new HashMap(1024)更合适，即使是1000，hashmap也自动会将其设置为1024。      
> 但是new HashMap(1024)还不是更合适的，因为0.75*1000 < 1000, 也就是说为了让0.75 * size > 1000, 必须这样new HashMap(2048)才最合适，既考虑了&的问题，也避免了resize的问题。                                      
>                                                
> JDK1.8中对HashMap扩容机制进行了优化。         
> 使用的是2次幂的扩展(指长度扩为原来2倍)，所以元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置。
> 图（a）表示扩容前的key1和key2两种key确定索引位置的示例，图（b）表示扩容后key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果。              
> ![hashmap6](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap6.png)                                          
> 元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：            
> ![hashmap7](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap7.png)           
> 因此，在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，
> 是1的话索引变成“原索引+oldCap”，可以看看下图为16扩充为32的resize示意图：                    
> ![hashmap8](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap8.png)               
> 这个设计确实非常的巧妙，既省去了重新计算hash值的时间，而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，
> 均匀的把之前的冲突的节点分散到新的bucket了。这一块就是JDK1.8新增的优化点。有一点注意区别，JDK1.7中rehash的时候，旧链表迁移新链表的时候，
> 如果在新表的数组索引位置相同，则链表元素会倒置，但是从上图可以看出，JDK1.8不会倒置。                                          
>                                     
> 参考 https://www.cnblogs.com/xidian2014/p/10466611.html                 
>                   
> HashSet的实现原理:                                                                                   
> HashSet中不允许有重复元素，这是因为HashSet是基于HashMap实现的，HashSet中的元素都存放在HashMap的key上面，
> 而value中的值都是统一的一个private static final Object PRESENT = new Object();。HashSet跟HashMap一样，都是一个存放链表的数组。                
>               
> HashSet底层由HashMap实现               
  HashSet的值存放于HashMap的key上              
  HashMap的value统一为PRESENT                       