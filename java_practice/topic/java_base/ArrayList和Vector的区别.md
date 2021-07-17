### 24.ArrayList和Vector的区别是什么？
> (1).Vector是多线程安全的，而ArrayList不是，Vector类中的方法很多有synchronized进行修饰，这样就导致了Vector在效率上无法与ArrayList相比                                
  (2).两个都是采用的线性连续空间存储元素，但是当空间不足的时候，两个类的增加方式是不同的，ArrayList在底层数组不够用时在原来的基础上扩展0.5倍，
> Vector如果设置了增长因子，扩容的容量是(oldCapacity+capacityIncrement)；Vector如果没有设置增长因子，扩容后的容量就是(oldCapacity+oldCapacity)，就是原来容量的二倍。                   
> (3).Vector可以设置增长因子，而ArrayList不可以              
>                   
> ArrayList有三个构造方法：                 
  public ArrayList(int initialCapacity)//构造一个具有指定初始容量的空列表。                  
  public ArrayList()//构造一个初始容量为10的空列表。              
  public ArrayList(Collection<? extends E> c)//构造一个包含指定 collection 的元素的列表               
  Vector有四个构造方法：                
  public Vector()//构造一个空向量，使其内部数据数组的大小，其标准容量增量为零。                               
  public Vector(int initialCapacity) //使用指定的初始容量和等于零的容量增量构造一个空向量。                                  
  public Vector(Collection<? extends E> c)//构造一个包含指定 collection 中的元素的向量                     
  public Vector(int initialCapacity,int capacityIncrement)//使用指定的初始容量和容量增量构造一个空的向量              
  Vector比Arraylist多一个构造方法public Vector(int initialCapacity,int capacityIncrement)，capacityIncrement就是增长因子，ArrayList中是没有的。           
>                                  
> 在迭代的时候对列表进行改变，应该使用CopyOnWriteArrayList。     
>               
> 参考 https://blog.csdn.net/tayanxunhua/article/details/10037403                       
