### 29.迭代器Iterator是什么？Iterator怎么使用？有什么特点？
> 迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。                
  Iterator提供了遍历操作集合元素的统一接口，Collection接口继承了Iterable接口，每个集合都通过实现Iterable接口中的iterator()方法返回Iterator接口的实例，然后对集合的元素进行迭代操作。                   
  迭代器可以在迭代过程中删除底层集合的元素，但是不可以直接调用集合的remove(Object obj)删除，可以通过迭代器的remove()方法删除。                   
>                        
> Java中的Iterator功能比较简单，并且只能单向移动：                    
  (1).使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。                                   
  (2).使用next()获得序列中的下一个元素。                                   
  (3).使用hasNext()检查序列中是否还有元素。                                  
  (4).使用remove()将迭代器新返回的元素删除。                                  
  Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。                                      
