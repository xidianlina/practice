### 26.在Queue中poll()和remove()有什么区别？
> Queue中remove()和poll()都是用来从队列头部删除一个元素。                         
  在队列元素为空的情况下，remove()方法会抛出NoSuchElementException异常，poll()方法只会返回null。                   
