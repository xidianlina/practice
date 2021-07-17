### 46.Integer类和int的区别
>(1).int是八大基本数据类型之一（byte,char,short,int,long,float,double,boolean），Integer是int包装类；              
 (2).int是基本数据类型，默认值为0，Integer是类，默认值为null；                   
 (3).int是基本数据类型，直接存储数值，Integer表示的是对象，用一个引用指向这个对象；                   
 (4).int存储在栈上，分配固定大小的内存，Integer存储在堆上，运行时动态分配存储空间；                   
 (5).int变量超过其作用域后，JVM会自动释放掉为该变量所分配的内存空间;Integer在堆中分配的内存，由JVM自动垃圾回收器来管理。                 
