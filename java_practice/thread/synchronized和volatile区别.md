### 20.synchronized和volatile的区别是什么？
> (1).volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取;synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。                 
  (2).volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的。                            
  (3).volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性。                       
  (4).volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。                                 
  (5).volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化。                           
