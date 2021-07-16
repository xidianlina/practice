### 3.equals与hashcode的区别和联系？  
> hashcode是为了算法快速定位数据而存在的，而equals是为了对比真实值而存在的。              
  两个对象的hashcode值相等时，两个对象的equals不一定相等，两个对象的equals值相等时，两个对象的hashcode一定相等。             
  equals和hashCode同时存在的意义:hashCode保证在最快的时间内判断两个对象是否相等，可能有误差值，但是能够保证性能；
>equals保证比较对象是否是绝对相等的，保证值比较的可靠性。                                             
>               
> hashCode()的作用:                                
  public native int hashCode();                 
  hashCode()是一个native方法，而且返回值类型是整形；实际上，该native方法将对象在内存中的地址作为哈希码返回，可以保证不同对象的返回值不同。               
  总的来说，hashCode()在哈希表中起作用，如HashSet、HashMap等。                
  当向哈希表(如HashSet、HashMap等)中添加对象object时，首先调用hashCode()方法计算object的哈希码，
> 通过哈希码可以直接定位object在哈希表中的位置(一般是哈希码对哈希表大小取余)。如果该位置没有对象，可以直接将object插入该位置；
> 如果该位置有对象(可能有多个，通过链表实现)，则调用equals()方法比较这些对象与object是否相等，如果相等，则不需要保存object；如果不相等，则将该对象加入到链表中。                    
>                       
> 每个覆盖了equals方法的类中，必须覆盖hashCode。如果不这么做，就违背了hashCode的通用约定。
> 进而导致该类无法结合所有与散列的集合(HashMap、HashSet、HashTable、ConcurrentHashMap)一起正常运作。                 
  不重写HashCode对于散列表的影响:              
  两个所有属性都相等的对象，它们的地址不同。没重写hashCode时，hashCode一定不相等。但是逻辑上这两个对象是相等的，并且equals也是相等的。
> 这就会导致，HashMap里面本来有这个key，但是你告诉我没有，导致了put操作成功。逻辑上是不符合规范的，get时取出来的也可能是自己另一个的value。                       
>         
> 实现equals()方法应该遵守的约定:          
   (1).自反性:x.equals(x)必须返回true。              
   (2).对称性:x.equals(y)与y.equals(x)的返回值必须相等。              
   (3).传递性:x.equals(y)为true，y.equals(z)也为true，那么x.equals(z)必须为true。              
   (4).一致性:如果对象x和y在equals()中使用的信息都没有改变，那么x.equals(y)值始终不变。               
   (5).非null:x不是null，y为null，则x.equals(y)必须为false。                
>                               
> 参考 https://segmentfault.com/a/1190000024478811            
> https://segmentfault.com/a/1190000022735238  