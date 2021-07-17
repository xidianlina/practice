### 30.Iterator和ListIterator有什么区别？
> (1).遍历:                   
  使用Iterator，可以遍历所有集合，如Map，List，Set；但只能在向前方向上遍历集合中的元素。              
  使用ListIterator，只能遍历List实现的对象，但可以向前和向后遍历集合中的元素。                
  (2).添加元素:             
  Iterator无法向集合中添加元素                
  ListIteror可以向集合添加元素               
  (3).修改元素:             
  Iterator无法修改集合中的元素                
  ListIterator可以使用set()修改集合中的元素             
  (4)索引:                
  Iterator无法获取集合中元素的索引                  
  使用ListIterator，可以获取集合中元素的索引                       
>                       
> Iterator的方法如下：                
  hasNext()：如果返回true，则确认集合中有更多元素。               
  next()：返回列表的下一个元素。            
  remove()：从集合中删除元素。                
>           
> ListIterator的方法如下：                
  hasNext()：如果返回true，则确认集合中有更多元素。               
  next()：返回列表的下一个元素。                
  nextIndex()：返回列表中下一个元素的索引。                
  hasPrevious()：如果集合中有相反的元素，则返回true。                
  previous()：返回集合中的上一个元素。               
  previousIndex()：返回集合中上一个元素的索引。                
  remove()：从集合中删除元素。                
  set()：修改集合中的元素。               
  add()：在集合中添加新元素。              
