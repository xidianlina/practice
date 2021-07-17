### 22.ArrayList和LinkedList的区别是什么？
> (1).从数据结构上看，ArrayList是实现了基于动态数组的结构，而LinkedList则是基于实现链表的数据结构。
  (2).对于随机访问get和set，ArrayList要优于LinkedList，因为LinkedList要移动指针。
  (3).对于添加和删除操作add和remove，一般认为LinkedList要比ArrayList快，因为ArrayList要移动数据。
> 但是实际情况并非这样，对于添加或删除，LinkedList和ArrayList并不能明确说明谁快谁慢。
  当插入的数据量很小时，两者区别不太大，当插入的数据量大时，大约在容量的1/10之前，LinkedList会优于ArrayList，在其后就劣与ArrayList，且越靠近后面越差。
  所以，一般首选用ArrayList，由于LinkedList可以实现栈、队列以及双端队列等数据结构，所以当特定需要时候，使用LinkedList。
> 数据量小的时候，两者差不多，视具体情况去选择使用；当数据量大的时候，如果只需要在靠前的部分插入或删除数据，那也可以选用LinkedList，反之选择ArrayList反而效率更高。
