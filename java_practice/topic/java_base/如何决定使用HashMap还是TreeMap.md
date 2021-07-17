### 19.如何决定使用HashMap还是TreeMap？
> TreeMap<K,V>的Key值是要求实现java.lang.Comparable，所以迭代的时候TreeMap默认是按照Key值升序排序的；TreeMap的实现是基于红黑树结构。适用于按自然顺序或自定义顺序遍历键（key）。                              
  HashMap<K,V>的Key值实现散列hashCode()，分布是散列的、均匀的，不支持排序；数据结构主要是桶(数组)，链表或红黑树。适用于在Map中插入、删除和定位元素。              
>           
> 对于在Map中插入、删除和定位元素这类操作，HashMap是最好的选择。然而，假如需要对一个有序的key集合进行遍历，TreeMap是更好的选择。                                           
