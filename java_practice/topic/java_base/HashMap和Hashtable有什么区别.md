### 18.HashMap和Hashtable有什么区别？
> Hashmap是一个数组和链表的结合体（在数据结构称“链表散列“）
> ![hashmap](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap.png)                     
> 当向hashmap中put元素的时候，先根据key的hash值得到这个元素在数组中的位置（即下标），然后就可以把这个元素放到对应的位置中了。
>如果这个元素所在的位子上已经存放有其他元素了，那么在同一个位子上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。           
>hashtable：底层结构是哈希表，无序，线程安全，效率低，不允许有null值，父类是Dictionary                            
>               
> (1).继承不同              
  public class Hashtable extends Dictionary implements Map              
  public class HashMap  extends AbstractMap implements Map          
  (2).Hashtable线程安全，HashMap线程不安全                
  Hashtable中的方法是同步的，而HashMap中的方法在缺省情况下是非同步的。在多线程并发的环境下，可以直接使用Hashtable，但是要使用HashMap的话就要自己增加同步处理了。               
  (3).Hashtable中，key和value都不允许出现null值；HashMap中，key和value都允许出现null值          
  Hashtable中，key和value都不允许出现null值。          
  在HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。当get()方法返回null值时，即可以表示 HashMap中没有该键，
> 也可以表示该键所对应的值为null。因此，在HashMap中不能由get()方法来判断HashMap中是否存在某个键， 而应该用containsKey()方法来判断。               
  (4).两者遍历方式的内部实现上不同。               
  Hashtable、HashMap都使用了Iterator。而由于历史原因，Hashtable还使用了Enumeration的方式。            
  (5).哈希值的使用不同          
  HashTable直接使用对象的hashCode          
  HashMap重新计算hash值          
  (6).Hashtable和HashMap内部实现方式的数组的初始大小和扩容的方式不同               
  HashTable中hash数组默认大小是11，增加的方式是old*2+1。HashMap中hash数组的默认大小是16，而且一定是2的指数。                                                                                                                     
