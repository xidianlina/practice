### 16.Collection和Collections有什么区别？
> java.util.Collection是一个集合接口。它提供了对集合对象进行基本操作的通用接口方法。Collection接口在Java类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式。                           
  List，Set，Queue接口都继承Collection。                            
  直接实现该接口的类只有AbstractCollection类，该类只是一个抽象类，提供了对集合类操作的一些基本实现。List和Set的具体实现类基本上都直接或间接的继承了该类。                  
  java.util.Collections是一个包装类。它包含有各种有关集合操作的静态方法（对集合的搜索、排序、线程安全化等），大多数方法都是用来处理线性表的。此类不能实例化，是一个工具类，服务于Java的Collection框架。              
