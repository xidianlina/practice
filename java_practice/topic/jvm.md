java虚拟机JVM
======
## 问题列表
### 1.Java虚拟机运行时数据区域
## 问题答案
### 1.Java虚拟机运行时数据区域
> ![java_run_data_area](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/java_run_data_area.png)                                               
> (1).方法区:属于共享内存区域，存储被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。                 
> (2).堆:对于绝大多数应用来说，这块区域是JVM所管理的内存中最大的一块。线程共享，主要是存放对象实例和数组。内部会划分出多个线程私有的分配缓冲区(Thread Local Allocation Buffer, TLAB)。可以位于物理上不连续的空间，但是逻辑上要连续。                                  
  OutOfMemoryError:如果堆中没有内存完成实例分配，并且堆也无法再扩展时，抛出该异常。                 
> (3).Java虚拟机栈:线程私有，生命周期和线程一致。描述的是 Java 方法执行的内存模型：每个方法在执行时都会床创建一个栈帧(Stack Frame)用于存储局部变量表、操作数栈、动态链接、方法出口等信息。每一个方法从调用直至执行结束，就对应着一个栈帧从虚拟机栈中入栈到出栈的过程。                  
  局部变量表:存放了编译期可知的各种基本类型(boolean、byte、char、short、int、float、long、double)、对象引用(reference 类型)和 returnAddress 类型(指向了一条字节码指令的地址)                  
  StackOverflowError：线程请求的栈深度大于虚拟机所允许的深度。                       
  OutOfMemoryError：如果虚拟机栈可以动态扩展，而扩展时无法申请到足够的内存。                 
  (4).本地方法栈:区别于Java虚拟机栈的是，Java虚拟机栈为虚拟机执行Java方法(也就是字节码)服务，而本地方法栈则为虚拟机使用到的Native方法服务。也会有StackOverflowError和OutOfMemoryError异常。                
  (5).程序计数器:内存空间小，线程私有。字节码解释器工作是就是通过改变这个计数器的值来选取下一条需要执行指令的字节码指令，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖计数器完成。                                    
  如果线程正在执行一个Java方法，这个计数器记录的是正在执行的虚拟机字节码指令的地址；如果正在执行的是Native方法，这个计数器的值则为(Undefined)。
> 此内存区域是唯一一个在Java虚拟机规范中没有规定任何OutOfMemoryError情况的区域。                                          
> ![java_run_data_area2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/java_run_data_area2.png)                                              
> (6).运行时常量池:属于方法区一部分，用于存放编译期生成的各种字面量和符号引用。编译器和运行期(String 的 intern() )都可以将常量放入池中。内存有限，无法申请时抛出 OutOfMemoryError。                 
  (7).直接内存:非虚拟机运行时数据区的部分                        
  在JDK1.4中新加入NIO(New Input/Output)类，引入了一种基于通道(Channel)和缓存(Buffer)的I/O方式，它可以使用Native函数库直接分配堆外内存，然后通过一个存储在Java堆中的DirectByteBuffer对象作为这块内存的引用进行操作。可以避免在Java堆和Native 堆中来回的数据耗时操作。
  OutOfMemoryError：会受到本机内存限制，如果内存区域总和大于物理内存限制从而导致动态扩展时出现该异常。                                                                               
                                      

> 参考 https://blog.csdn.net/qq_41701956/article/details/81664921