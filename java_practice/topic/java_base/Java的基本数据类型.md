### 9.Java的基本数据类型
> 在Java程序设计中，每个声明的变量都必须分配一个类型。声明一个变量时，应该先声明变量的类型，随后再声明变量的名字。            
  Java中只包含这8中基本数据类型，字符串不是基本数据类型，字符串是一个类，也就是一个引用数据类型。                
  注意：单引号用于字符类型（char），双引号用于字符串类型（String）             
>                                   
> byte（字节型  1个字节） 对应包装类 java.lang.Byte              
  short（短整型  2个字节） 对应包装类 java.lang.Short                
  int（整型  4个字节） 对应包装类 java.lang.Integer             
  long（长整型 8个字节） 对应包装类 java.lang.Long           
  float（单精度浮点型  4个字节）对应包装类 java.lang.Float          
  double（双精度浮点型  8个字节）对应包装类 java.lang.Double            
  boolean（布尔型 如果boolean是单独使用则占4个字节;如果boolean是以“boolean数组”的形式使用则占1个字节） 对应包装类 java.lang.Boolean              
  char（字符型 2个字节） 对应包装类 java.lang.Character              
>                 
> 类型分类:             
  整型 byte short int long            
  浮点型 float double          
  布尔型 boolean(它只有两个值可取 true false)          
  字符型 char          
>                       
> 包装类的对象都是对象, 所以都有引用实际对象, 引用自然是在调用时的局部变量方法栈中,对象都在堆内存中, 包装类的对象也都是在堆内存中。             
  基础类型如果是定义在方法中, 每次调用都会生成一个方法调用栈, 调用完毕销毁,所以, 是在虚拟机栈中。               
  基础类型如果是定义在类中, 则运行中是作为对象的实例变量存储在堆内存中的。         
  包装类型如果是定义在方法中，类引用在栈中, 对象在堆内存中。            
  包装类型如果是定义在类中，属于实例变量, 存在于堆内存。              
  数组的实际内容都有一块连续的空间, 存在于堆内存。         
>                     
> 大数值类型:            
  在Java的java.math包下，有两个类用来表示大数，它们分别是：BigInteger和BigDecimal          
  这两个类都可以对超出基本数据类型表示范围的数进行构造。另外，它们的构造方法都需要传进来一个字符串。             
  对于BigInteger而言，可以进行add, subtract, multiply, divide 等操作；对于 BigDecimal，同样可以进行上述操作，不过最好给定一个 MathContext 保留有效数字。         
>                         
> 数组类型:             
  准确地讲，数组不是一种类型，而是一种数据结构。但是数组是很多其它Java类(如 String、 ArrayList )的基础。在Java里，数组是一种用来存储相同数据类型对象的数据结构。                 
  对Java数组而言：                
  数组被分配出来后，长度不可变                
  只有有length属性，没有length()方法          
  可使用for-each遍历         
  多维数组本质上是数组的数组           