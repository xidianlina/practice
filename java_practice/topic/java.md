java基础题目
======
## 问题列表
### 1.JDK和JRE有什么区别
### 2.==和equals的区别是什么？
### 3.equals与hashcode的区别和联系？
### 4.final关键字
### 5.static关键字
### 6.final、finally 和 finalize
### 7.类加载顺序
### 8.构造器是静态方法吗？
### 9.Java的基本数据类型
### 10.java中操作字符串都有哪些类？它们之间有什么区别？
### 11.String类的常用方法都有那些？
### 12.抽象类必须要有抽象方法吗？抽象类能使用final修饰吗？普通类和抽象类有哪些区别？接口和抽象类有什么区别？
### 13.Files的常用方法都有哪些？
### 14.java中IO流分为几种？BIO、NIO、AIO 有什么区别?
### 15.java常用的集合容器都有哪些？
### 16.Collection和Collections有什么区别？
### 17.List、Set、Map之间的区别是什么？
### 18.HashMap和Hashtable有什么区别？
### 19.如何决定使用HashMap还是TreeMap？
### 20.HashMap的实现原理？
### 21.HashSet的实现原理？
### 22.ArrayList和LinkedList的区别是什么？
### 23.如何实现数组和List之间的转换？
### 24.ArrayList和Vector的区别是什么？
### 25.Array和ArrayList有何区别？
### 26.在Queue中poll()和remove()有什么区别？
### 27.哪些集合类是线程安全的？
### 28.写时复制(CopyOnWrite)
### 29.迭代器Iterator是什么？Iterator怎么使用？有什么特点？
### 30.Iterator和ListIterator有什么区别？

## 问题答案
### 1.JDK和JRE有什么区别？
> JDK:Java Development Kit的简称，java开发工具包，提供了java的开发环境和运行环境。                         
  JRE:Java Runtime Environment的简称，java运行环境，为java的运行提供了所需环境。             
  JDK其实包含了JRE，同时还包含了编译java源码的编译器javac，还包含了很多java 程序调试和分析的工具。如果运行java程序，只需安装JRE就可以了，如果需要编写java程序，需要安装JDK。            
### 2.==和equals的区别是什么？   
> 对于基本类型和引用类型 == 的作用效果是不同的:             
  基本类型时，==比较的是值是否相同         
  引用类型时，==比较的是引用是否相同            
  代码示例:         
```java
package com.java.topic;

public class TestEquals {
    public static void main(String[] args) {
        String x = "string";
        String y = "string";
        String z = new String("string");
        System.out.println(x == y);        //true
        System.out.println(x == z);       //false
        System.out.println(x.equals(y));  //true
        System.out.println(x.equals(z));  //true
    }
}
```
> 因为 x 和 y 指向的是同一个引用，所以 == 也是 true，而 new String()方法则重写开辟了内存空间，所以 == 结果为 false，而 equals 比较的一直是值，所以结果都为 true。             
>                   
> equals本质上就是==，只不过String和Integer等重写了equals方法，把它变成了值比较。             
> 默认情况下 equals 比较一个有相同值的对象，代码如下：         
```java
package com.java.topic;

public class TestEquals {
    public static void main(String[] args) {
        Cat c1 = new Cat("cat");
        Cat c2 = new Cat("cat");
        System.out.println(c1.equals(c2)); // false
    }
}

class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```           
equals源码如下：
```java
public class Object {
    public boolean equals(Object obj) {
            return (this == obj);
    }
}
```
equals 本质上就是 ==             
如果是两个值相同的String对象，则返回true
```java
public class TestEquals {
    public static void main(String[] args) {
        String s1 = new String("test");
        String s2 = new String("test");
        System.out.println(s1.equals(s2)); //true
    }
}
```
String的equals方法源码如下：
```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
}
```     
> String重写了Object的equals方法，把引用比较改成了值比较。             
>                     
> ==对于基本类型来说是值比较，对于引用类型来说是比较的是引用；而equals默认情况下是引用比较，只是很多类重新了equals方法，比如String、Integer等把它变成了值比较，所以一般情况下equals比较的是值是否相等。               
### 3.equals与hashcode的区别和联系？  
> hashcode是为了算法快速定位数据而存在的，而equals是为了对比真实值而存在的。              
  两个对象的hashcode值相等时，两个对象的equals不一定相等，两个对象的equals值相等时，两个对象的hashcode一定相等。             
  equals和hashCode同时存在的意义:equals保证比较对象是否是绝对相等的(保证可靠);hashCode保证在最快的时间内判断两个对象是否相等，可能有误差值(保证性能)。                             
>               
> hashCode()的作用:                                
  public native int hashCode();                 
  hashCode()是一个native方法，而且返回值类型是整形；实际上，该native方法将对象在内存中的地址作为哈希码返回，可以保证不同对象的返回值不同。               
  总的来说，hashCode()在哈希表中起作用，如HashSet、HashMap等。                
  当向哈希表(如HashSet、HashMap等)中添加对象object时，首先调用hashCode()方法计算object的哈希码，
> 通过哈希码可以直接定位object在哈希表中的位置(一般是哈希码对哈希表大小取余)。如果该位置没有对象，可以直接将object插入该位置；
> 如果该位置有对象(可能有多个，通过链表实现)，则调用equals()方法比较这些对象与object是否相等，如果相等，则不需要保存object；如果不相等，则将该对象加入到链表中。                    
>               
> 实现equals()方法应该遵守的约定:          
  (1).自反性:x.equals(x)必须返回true。              
  (2).对称性:x.equals(y)与y.equals(x)的返回值必须相等。              
  (3).传递性:x.equals(y)为true，y.equals(z)也为true，那么x.equals(z)必须为true。              
  (4).一致性:如果对象x和y在equals()中使用的信息都没有改变，那么x.equals(y)值始终不变。               
  (5).非null:x不是null，y为null，则x.equals(y)必须为false。                
>                   
> 每个覆盖了equals方法的类中，必须覆盖hashCode。如果不这么做，就违背了hashCode的通用约定。
> 进而导致该类无法结合所有与散列的集合(HashMap、HashSet、HashTable、ConcurrentHashMap)一起正常运作。                 
  不重写HashCode对于散列表的影响:              
  两个所有属性都相等的对象，它们的地址不同。没重写hashCode时，hashCode一定不相等。但是逻辑上这两个对象是相等的，并且equals也是相等的。
> 这就会导致，HashMap里面本来有这个key，但是你告诉我没有，导致了put操作成功。逻辑上是不符合规范的，get时取出来的也可能是自己另一个的value。                       
>                       
> 参考 https://segmentfault.com/a/1190000024478811            
> https://segmentfault.com/a/1190000022735238                           
### 4.final关键字
> final关键字可以修饰的对象有三个:一是修饰变量、二是修饰方法、三是修饰类            
  (1).final关键字修饰变量              
  1).final修饰成员变量                
  final关键字在修饰基本数据类型时必须对变量赋予初始值，因此,final关键字也经常和static关键字一起用来声明常量值，final正好限制了必须赋值、static声明了静态变量。              
  fianl最常见的用法时用来修饰成员变量,成员变量分为静态变量与普通变量.             
  对于final修饰的变量,不是不能被赋值,是其值不能被改变,可以理解成只能赋一次值.可以在定义时赋值,也可以在定义后在另外赋值,但无论何种方式只能被赋值一次.           
  修饰静态变量            
  修饰静态变量时,可以选择以下两种方式赋值:         
  在定义时赋值            
  静态初始化块内赋值         
```java
package com.java.topic;

public class TestFinal {
    final static int a = 6;
    final static int b;

    static {
        b = 6;
    }
}
```
> 修饰普通成员变量              
  修饰普通成员变量时,可以选择以下三种方式赋值:           
  定义时赋值             
  初始化块内赋值               
  构造方法赋值                
```java
package com.java.topic;

public class TestFinal {
    final int c = 1;
    final int d;
    final int e;

    {
        d = 2;
    }

    public TestFinal() {
        e = 3;
    }
}
```
> 2).final修饰局部变量                            
  final修饰局部变量其实也分两种,一种是修饰形参,一种是修饰方法内部的局部变量              
  修饰形参                      
  形参的值不能改变     
```java
package com.java.topic;

public class TestFinal {
    public void f(final int a) {
        a = 3;//报错.
    }
}
```  
> 修饰局部变量                
  修饰局部变量时可以定义时赋值,也可以在定义后在赋值(仅一次)            
```java
package com.java.topic;

public class TestFinal {
    public void f() {
        final int a = 3;
        final int b;
        b = 2;
    }
}
```       
> final修饰引用变量                   
  final数组的元素可以被改变，final对象的值也可以被改变               
  因为数组是引用类型,final修饰引用类型时,只能保证这个变量永远"指向"那一段内存空间,保存的仅仅是一个引用,但是那段内存空间的值是可以改变的.修饰对象时也是一样的道理.                    
>           
> (2).final关键字修饰方法              
  final修饰的方法不能被重写,当然,不能"配合"private"使用",因为private把方法变成了私有,相当于对子类不可见,子类都不知道父类"还有这玩意",就可以进行所谓的"重写"了.               
```java
class A
{
    private final void f(){}
}
class B extends A
{
    public final void f(){}//没毛病
}
```
> B类的f()是属于B类的,不是从A类继承过来的
> 通过 final 关键字修饰的方法是不能被子类的方法重写的，相当于一个方法的功能已经被定了不能进行重写修改。
> 一般情况下，如果一个方法的功能已经确定好不再改变了则可以使用 final 关键字修饰一下，
> 因为 final 关键字修饰以后的方法是在程序编译的时候就被动态绑定了不用等到程序运行的时候被动态绑定，这样就大大的提高了执行的效率。                   
>               
> (3).final关键字修饰类                       
  final修饰类时表示该类不能被继承 
> final类的功能一般是比较完整的。因此，Jdk 中有很多类都是用 final 关键字进行修饰的，它们不需要被继承。
> 其中，最常见的 String 类就是 fianl 关键字修饰的类，还有其他的装饰类等等。                      
```java
final class A{}
class B extends A{}//出错
```       
>             
> 在匿名类中所有变量都必须是final变量                  
> 接口中声明的所有变量本身是final的                   
> final和abstract这两个关键字是反相关的，final类就不可能是abstract的                                   
> 没有在声明时初始化final变量的称为空白final变量(blank final variable)，它们必须在构造器中初始化，或者调用this()初始化，否则编译器会报错final变量(变量名)需要进行初始化             
  按照Java代码惯例，final变量就是常量，而且通常常量名要大写             
  对于集合对象声明为 final指的是引用不能被更改                     
>                                 
> final关键字的好处:              
> final关键字提高了性能，JVM和Java应用都会缓存final变量               
> final 变量可以安全的在多线程环境下进行共享，而不需要额外的同步开销              

> 参考  https://segmentfault.com/a/1190000020803203               
> https://segmentfault.com/a/1190000020815352                        
### 5.static关键字
> static可以修饰变量、方法、代码块、类。            
  (1).static修饰变量                
  static关键字表示的概念是全局的、静态的，用它修饰的变量被称为静态变量或类变量。            
  静态变量是属于这个类所有的。static关键字只能定义在类的{}中，而不能定义在任何方法中,不能修饰局部变量。               
  定义在构造方法、代码块、方法外的变量被称为实例变量或成员变量，实例变量的副本数量和实例的数量一样                  
  定义在方法、构造方法、代码块内的变量被称为局部变量         
  定义在方法参数中的变量被称为形式参数                
  (2).static修饰方法            
  被static修饰的方法被称为静态方法。随着类的加载而加载,可以通过"类.静态方法"的方式进行调用。                
  static方法就是没有this/super的方法，在static内部不能调用非静态方法，反过来是可以的。而且可以在没有创建任何对象的前提下，仅仅通过类本身来调用static方法，这实际上是static方法的主要用途。             
  静态的方法内不能使用this/super这两个需要基于当前对象的关键字(编译不通过)。               
  (3).static修饰代码块               
  代码块分为两种，一种是使用{}代码块；一种是static{}静态代码块。static修饰的代码块被称为静态代码块。静态代码块可以置于类中的任何地方，类中可以有多个static块，在类初次被加载的时候，会按照static代码块的顺序来执行，每个static修饰的代码块只能执行一次。          
  (4).static用作静态内部类                 
  内部类的使用场景比较少，但是内部类还有具有一些比较有用的。             
  内部类的分类:普通内部类、局部内部类、静态内部类、匿名内部类                
  静态内部类就是用static修饰的内部类，静态内部类可以包含静态成员，也可以包含非静态成员，但是在非静态内部类中不可以声明静态成员。            
  静态内部类有许多作用，由于非静态内部类的实例创建需要有外部类对象的引用，所以非静态内部类对象的创建必须依托于外部类的实例；
> 而静态内部类的实例创建只需依托外部类，并且由于非静态内部类对象持有了外部类对象的引用，因此非静态内部类可以访问外部类的非静态成员；而静态内部类只能访问外部类的静态成员。              
  内部类需要脱离外部类对象来创建实例。            
  (5).静态导包          
  静态导入就是使用 import static 用来导入某个类或者某个包中的静态方法或者静态变量。                        
  static所修饰的属性和方法都属于类的，不会属于任何对象；它们的调用方式都是类名.属性名/方法名，而实例变量和局部变量都是属于具体的对象实例。          
  static修饰的变量存储在JVM方法区中。                
  static变量的生命周期与类的生命周期相同,随类的加载而创建,随类的销毁而销毁,类变量的加载早于对象的创建。               
  声明为static和transient类型的变量不能被序列化，因为static修饰的变量保存在方法区中，只有堆内存才会被序列化。而transient关键字的作用就是防止对象进行序列化操作。                
  static选定标准:               
  选定类变量             
  属性可以被多个对象共享,不会随着对象发生变化                
  选定静态方法                
  操作静态属性的方法通常也设为static的                 
  工具类中的方法,一般也声明为static的(如Math/Arrays/Collections)  
>               
> 静态修饰的特点:                  
  被static修饰的变量/方法是独立与类所创建的对象而存在的，当创建多个对象实例时，static修饰的变量/方法是被它们所共享的，即不随着不同对象的创建而发生改变。                
  static在类加载时就创建好了内存空间，但是它的内容是可以改变的，可以通过不同的对象给它赋值，但是它的值取决于你最终给它的赋值。             
  即便不创建对象，static修饰的变量或方法也是可以存在的，当一个类加载完毕后，类中static修饰的变量和方法就可以进行访问。              
  静态修饰的优点:              
  方便在没有创建对象时，调用类中的方法/变量             
  static可以用来修饰类的成员方法、成员变量或编写static代码块，能够有效地优化程序性能                                    
>                                                         
> 参考 https://segmentfault.com/a/1190000022794717                
> https://segmentfault.com/a/1190000025188850
### 6.final、finally 和 finalize 
> final修饰的变量被赋值后,是其值不能被改变               
  final修饰的方法不能被重写                   
  final修饰类时表示该类不能被继承 
>                                                              
> finally是保证程序一定执行的机制，一般来讲，finally一般不会单独使用，它一般和try 块一起使用。
> finally块在离开try块执行完成后或者try块未执行完成但是接下来是控制转移语句时（return/continue/break/throw）在控制转移语句之前执行。               
> 当finally有返回值时，会直接返回。不会再去返回try或者catch中的返回值。 
> 在finally中没有return时，栈中最后存储的数据是try/catch中操作后数据。即finally操作后的数据存储到其他槽中，而后再加载try/catch操作后的数据。
  而在finally中含有return时，栈中最后存储的数据是finally中操作后的数据。即finally操作后的数据存储到其他槽中，而后加载的是其他槽（finally）中的数据。                                   
> 在执行finally语句之前，控制转移语句会将返回值存在本地变量中 
>                           
> finally不会执行的情况:                            
> 在try语句中如果调用System.exit方法               
  调用Runtime.getRuntime().halt(exitStatus)方法             
  JVM宕机         
  如果JVM在try或catch块中达到了无限循环（或其他不间断，不终止的语句）               
  操作系统强行终止了JVM进程。例如在 UNIX上执行kill -9 pid         
  如果主机系统死机；例如电源故障，硬件错误，操作系统死机等不会执行          
  如果finally块由守护程序线程执行，那么所有非守护线程在finally调用之前退出。          
>                                                                                             
> finalize是根父类Object类的一个方法，它的设计目的是保证对象在垃圾收集前完成特定资源的回收。finalize现在已经不再推荐使用，在JDK1.9中已经明确的被标记为deprecated。                   
>               
> 参考 https://segmentfault.com/a/1190000037687672
### 7.类加载顺序        
> 加载父类的静态字段 -> 父类的静态代码块 -> 子类静态字段 -> 子类静态代码块 -> 父类成员变量（非静态字段）-> 父类非静态代码块 -> 父类构造器 -> 子类成员变量 -> 子类非静态代码块 -> 子类构造器        
```java
package com.java.topic;

public class ClassLoaderOrder extends Father {
    public static void main(String[] args) {
        ClassLoaderOrder c = new ClassLoaderOrder();
    }

    static String c = "child";

    static {
        System.out.println(c);
        System.out.println("child static 1");
        c = "body";
        System.out.println(c);
    }

    static {
        System.out.println("child static 2");
        System.out.println(c);
    }

    private int val = 100;

    {
        System.out.println("child val " + val);
        System.out.println("child block");
    }

    public ClassLoaderOrder() {
        System.out.println("child construct");
    }
}

class Father {
    static int f = 2;

    static {
        System.out.println(f);
        System.out.println("father static 1");
        f = 9;
        System.out.println(f);
    }

    static {
        System.out.println("father static 2");
        System.out.println(f);
    }

    private String val = "hello";

    {
        System.out.println("father val " + val);
        System.out.println("father block");
    }

    public Father() {
        System.out.println("father construct");
    }
}
```                    
### 8.构造器是静态方法吗？
> 构造器不是静态方法             
> 静态方法中不能使用this，而构造器中可以使用this关键字。this是指调用当前方法的对象，而静态方法不属于任何对象。从这个角度来看，构造器不是静态的。             
  从类的方法这个角度来看，通过类.方法名而不需要新创建对象就能够访问静态方法，所以从这个角度来看，构造器也不是静态的。                
  invokestatic指令是专门用来执行static方法的指令,invokespecial是专门用来执行实例方法的指令。在调用static方法时是使用的invokestatic指令，new对象调用的是invokespecial指令，从这个角度来讲，构造器也不是静态的。                    
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
> 包装类的对象都是对象, 所以都有引用和实际对象, 引用自然是在调用时的局部变量方法栈中,对象都在堆内存中, 包装类的对象也都是在堆内存中。             
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
### 10.java中操作字符串都有哪些类？它们之间有什么区别？
> 操作字符串的类有:String、StringBuffer、StringBuilder。                   
  String和StringBuffer、StringBuilder的区别在于String声明的是不可变的对象，在JDK中String类被声明为一个final类，
> 每次操作都会生成新的String对象，然后将指针指向新的String对象，而StringBuffer、StringBuilder可以在原有对象的基础上进行操作，
> 所以在经常改变字符串内容的情况下最好不要使用String。             
  StringBuffer和StringBuilder最大的区别在于，StringBuffer是线程安全的，而StringBuilder是非线程安全的，线程安全会带来额外的系统开销，
> StringBuilder的性能却高于StringBuffer，所以在单线程环境下推荐使用StringBuilder，多线程环境下推荐使用StringBuffer。        
>                       
> String str="i"与 String str=new String("i")内存的分配方式不一样。String str="i"的方式，java虚拟机会将其分配到常量池中；而String str=new String("i")则会被分到堆内存中。                                
### 11.String类的常用方法都有那些？
> indexOf()：返回指定字符/字符串的索引。          
  charAt()：返回指定索引处的字符。          
  replace()：字符串替换。          
  trim()：去除字符串两端空白。                 
  split()：分割字符串，返回一个分割后的字符串数组。              
  getBytes()：返回字符串的 byte 类型数组。              
  length()：返回字符串长度。             
  toLowerCase()：将字符串转成小写字母。             
  toUpperCase()：将字符串转成大写字符。             
  substring()：截取字符串。                
  equals()：字符串比较。                   
  toString()：返回字符串本身。               
  startsWith()：判断字符串是否以某个前缀开头。              
  endsWith()：判断字符串是否以某个后缀结尾。            
  lastIndexOf()：返回指定字符/字符串在此字符串中最后一次出现处的索引。         
  concat()：连接的字符串。              
  contains()：判断字符串是否包含某字串。          
  join()：返回具有给定的元素和指定的分隔符组成的一个新字符串。         
  toCharArray()：将字符串转成字符数组。         
  valueOf()：将Object对象、char[]、boolean、char、int、long、float、double转换成字符串。              
### 12.抽象类必须要有抽象方法吗？抽象类能使用final修饰吗？普通类和抽象类有哪些区别？接口和抽象类有什么区别？
> 抽象类不一定非要有抽象方法。            
>           
> 定义抽象类就是让其他类继承的，如果定义为final该类就不能被继承，这样彼此就会产生矛盾，所以final不能修饰抽象类。
> final和abstract这两个关键字是反相关的，final类就不可能是abstract的                
>               
> 普通类不能包含抽象方法，抽象类可以包含抽象方法。              
  抽象类不能直接实例化，普通类可以直接实例化。 
  抽象方法必须为public或者protected（因为如果为private，则不能被子类继承，子类便无法实现该方法），缺省情况下默认为public。                           
  如果一个类继承于一个抽象类，则子类必须实现父类的抽象方法。如果子类没有实现父类的抽象方法，则必须将子类也定义为abstract类。                                
>               
> 抽象方法必须用abstract关键字进行修饰。如果一个类含有抽象方法，则称这个类为抽象类，抽象类必须在类前用abstract关键字修饰。
> 因为抽象类中含有无具体实现的方法，所以不能用抽象类创建对象。抽象类可以拥有成员变量和普通的成员方法。                   
> 接口中的变量会被隐式地指定为public static final变量，并且只能是public static final变量，用private修饰会报编译错误，
> 而方法会被隐式地指定为public abstract方法且只能是public abstract方法，用其他关键字，
> 比如private、protected、static、 final等修饰会报编译错误，并且接口中所有的方法不能有具体的实现，也就是说，接口中的方法必须都是抽象方法。                        
>                        
> 抽象类和接口的区别:                
 语法层面上的区别:              
 (1).一个类只能继承一个抽象类，而一个类却可以实现多个接口；            
 (2).抽象类中的成员变量可以是各种类型的，而接口中的成员变量只能是public static final类型的；              
 (3).接口中不能含有静态代码块以及静态方法，而抽象类可以有静态代码块和静态方法；              
 (4).抽象类可以提供成员方法的实现细节，而接口中只能存在public abstract 方法；               
 (5).抽象类的抽象方法可以是public，protected，default类型，而接口的方法只能是public。 
 (6).抽象类可以有构造函数；接口不能有。 
 (7).抽象类可以有main方法，并且我们能运行它；接口不能有main方法。                                  
 设计层面上的区别:              
 (1).抽象类是对一种事物的抽象，即对类抽象，而接口是对行为的抽象。抽象类是对整个类整体进行抽象，包括属性、行为，但是接口却是对类局部（行为）进行抽象。                              
 继承是一个 "是不是"的关系，而 接口 实现则是 "有没有"的关系。                                      
 (2).设计层面不同，抽象类作为很多子类的父类，它是一种模板式设计。而接口是一种行为规范，它是一种辐射式设计。                                  
### 13.Files的常用方法都有哪些？
> Files.exists()：检测文件路径是否存在。                    
  Files.createFile()：创建文件。              
  Files.createDirectory()：创建文件夹。                
  Files.delete()：删除一个文件或目录。             
  Files.copy()：复制文件。                
  Files.move()：移动文件。                
  Files.size()：查看文件个数。              
  Files.read()：读取文件。                
  Files.write()：写入文件。 
```java
package com.java.topic;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class FilesTest {
    //1、File类:文件和目录路径名的抽象表示形式，创建File对象后，仅仅是一个路径的表示，不代码具体的事物一定是存在的。

    /*
     * 2、构造方法:
     * public File(String pathname)：根据一个路径得到File对象
     * public File(String parent,String child)：根据一个目录和一个子文件/目录得到File对象
     * public File(File parent,String child)：根据一个父File对象和一个子文件/目录得到File对象
     */
    @Test
    public void testFile() {
        //File(String pathname)：根据一个路径得到File对象
        File file = new File("/Users/lina/practice/java_practice/file.txt");

        //File(String parent, String child):根据一个目录和一个子文件/目录得到File对象
        File file2 = new File("/Users/lina/practice/java_practice", "file2.txt");

        //File(File parent, String child):根据一个父File对象和一个子文件/目录得到File对象
        File file3 = new File("/Users/lina/practice/java_practice");
        File file4 = new File(file3, "file3.txt");

        //以上三种方式其实效果一样，常用第一种
    }

    /*
     * 3、创建功能
     * public boolean createNewFile()：当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
     * public boolean mkdir()：创建此抽象路径名指定的目录。
     * public boolean mkdirs()：创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
     */
    @Test
    public void testFile2() {
        //需求：在某目录下创建一个文件夹test
        File file = new File("/Users/lina/practice/java_practice/test");
        //第一次执行返回true，第二次执行返回false,如果存在这样的文件夹就不创建了
        System.out.println("mkdir:" + file.mkdir());

        // 需求:在某目录test文件夹下创建一个文件a.txt
        File file2 = new File("/Users/lina/practice/java_practice/test/a.txt");
        try {
            System.out.println("createNewFile: " + file2.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 需求：在某目录demo文件夹下创建一个文件b.txt
        // Exception in thread "main" java.io.IOException: 系统找不到指定的路径。
        // 注意：要想在某个目录下创建内容，该目录首先必须存在。

        // mkdirs()：创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        File file3 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        System.out.println("mkdirs:" + file3.mkdirs());
    }

    /**
     * 4、删除功能
     * public boolean delete()
     * 要删除一个文件夹，请注意该文件夹内不能包含文件或者文件夹
     */
    @Test
    public void testFile3() {
        // 创建文件
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile: " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除功能：删除a.txt这个文件
        File file2 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        System.out.println("delete a.txt: " + file2.delete());

        // 删除功能：删除demo这个文件夹
        File file3 = new File("/Users/lina/practice/java_practice/demo/a.txt");

        // 创建文件
        File file4 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile: " + file4.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除功能：删除test文件夹
        File file5 = new File("/Users/lina/practice/java_practice/demo");
        System.out.println("delete test文件夹: " + file5.delete());
        //test文件夹下面有文件或文件夹，不能删除
    }

    /**
     * 5、重命名功能
     * public boolean renameTo(File dest)
     * 如果路径名相同，就是改名。
     * 如果路径名不同，就是改名并剪切。
     */
    @Test
    public void testFile4() {
        // 创建一个文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/b.txt");
        try {
            System.out.println("createNewFile: " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 需求：修改这个文件的名称为"c.txt"
        File file2 = new File("/Users/lina/practice/java_practice/demo/c.txt");
        System.out.println("renameTo: " + file.renameTo(file2));
    }

    /*
     * 6、判断功能
     * public boolean isDirectory()：判断是否是目录
     * public boolean isFile()：判断是否是文件
     * public boolean exists()：判断是否存在
     * public boolean canRead()：判断是否可读
     * public boolean canWrite()：判断是否可写
     * public boolean isHidden()：判断是否隐藏
     */
    @Test
    public void testFile5() {
        // 创建文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile:" + file.createNewFile());//true
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("isDirectory:" + file.isDirectory());// false
        System.out.println("isFile:" + file.isFile());// true
        System.out.println("exists:" + file.exists());// true
        System.out.println("canRead:" + file.canRead());// true
        System.out.println("canWrite:" + file.canWrite());// true
        System.out.println("isHidden:" + file.isHidden());// false
    }

    /*
     * 7、基本获取功能
     * public String getAbsolutePath()：获取绝对路径
     * public String getPath()：获取相对路径
     * public String getName()：获取名称
     * public long length()：获取长度。字节数
     * public long lastModified()：获取最后一次的修改时间，毫秒值
     */
    @Test
    public void testFile6() {
        // 创建文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        //public String getAbsolutePath()：获取绝对路径
        System.out.println("getAbsolutePath:" + file.getAbsolutePath());
        //public String getPath():获取相对路径
        System.out.println("getPath:" + file.getPath());
        //public String getName():获取名称
        System.out.println("getName:" + file.getName());
        //public long length():获取长度。字节数
        System.out.println("length:" + file.length());
        //public long lastModified():获取最后一次的修改时间，毫秒值
        System.out.println("lastModified:" + file.lastModified());
        Date d = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);
        System.out.println(s);
    }

    /*
     * 8、高级获取功能
     * public String[] list()：获取指定目录下的所有文件或者文件夹的名称数组
     * public File[] listFiles()：获取指定目录下的所有文件或者文件夹的File数组
     */
    @Test
    public void testFile7() {
        // 指定一个目录
        File file = new File("/Users/lina/practice/java_practice/demo");

        //public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
        String[] strArray = file.list();
        for (String s : strArray) {
            System.out.println(s);
        }
        System.out.println("------------");

        // public File[] listFiles():获取指定目录下的所有文件或者文件夹的File数组
        File[] fileArray = file.listFiles();
        for (File f : fileArray) {
            System.out.println(f.getName());
        }
    }

    /*
     * 9、练习题：判断某目录下是否有后缀名为.txt的文件，如果有，就输出此文件名称
     */
    @Test
    public void exerFile() {
        // 封装判断目录
        File file = new File("/Users/lina/practice/java_practice/demo");
        // 获取该目录下所有文件或者文件夹的File数组
        File[] fileArray = file.listFiles();
        // 遍历该File数组，得到每一个File对象，然后判断
        for (File f : fileArray) {
            // 是否是文件
            if (f.isFile()) {
                // 继续判断是否以.txt结尾
                if (f.getName().endsWith(".txt")) {
                    // 就输出该文件名称
                    System.out.println(f.getName());
                }
            }
        }
    }

    /*
     * 10、练习题：列出指定目录中所有的子文件名与所有的子目录名
     */
    @Test
    public void exerFile2() {
        // 创建File对象，表示这个目录
        File dir = new File("/Users/lina/practice/java_practice/demo");

        //通过listFiles方法得到所包含的所有子目录与子文件名称
        File[] names = dir.listFiles();

        //分别显示文件名与文件夹名
        for (int i = 0; i < names.length; ++i) {
            File file = names[i];
            if (file.isFile()) {
                System.out.println(("子文件："));
                System.out.println("\t" + file.getName());
            } else if (file.isDirectory()) {
                System.out.println(("子目录："));
                System.out.println("\t" + file.getName());
            }
        }
    }

    /*
     * 11、练习题：列出指定目录中所有的子文件名与所有的子目录名，要求目录名与文件名分开列出
     */
    @Test
    public void exerFile3() {
        //1创建File对象表示这个目录
        File dir = new File("/Users/lina/practice/java_practice/demo");

        //通过listFiles方法得到所包含的所有子目录与子文件名称
        File[] names = dir.listFiles();

        //得到所有的文件名集合，与所有的文件夹名集合
        List<File> filesList = new ArrayList<File>();
        List<File> dirsList = new ArrayList<File>();

        for (int i = 0; i < names.length; i++) {
            File file = names[i];
            if (file.isFile()) {
                filesList.add(file);
            } else if (file.isDirectory()) {
                dirsList.add(file);
            }
        }

        //分别显示文件名与文件夹名
        System.out.println("子文件：");
        for (int i = 0; i < filesList.size(); i++) {
            System.out.println("\t" + filesList.get(i).getName());
        }

        System.out.println("子目录：");
        for (int i = 0; i < dirsList.size(); i++) {
            System.out.println("\t" + dirsList.get(i).getName());
        }
    }

    /*
     * Java7中文件IO发生了很大的变化，专门引入了很多新的类:
     * import java.nio.file.DirectoryStream;
     * import java.nio.file.FileSystem;
     * import java.nio.file.FileSystems;
     * import java.nio.file.Files;
     * import java.nio.file.Path;
     * import java.nio.file.Paths;
     * import java.nio.file.attribute.FileAttribute;
     * import java.nio.file.attribute.PosixFilePermission;
     * import java.nio.file.attribute.PosixFilePermissions;
     * ......等等，来取代原来的基于java.io.File的文件IO操作方式.
     */

    //1. Path就是取代File的。Path用于来表示文件路径和文件。可以有多种方法来构造一个Path对象来表示一个文件路径，或者一个文件
    @Test
    public void testPaths() {
        //1.1 final类Paths的两个static方法从一个路径字符串来构造Path对象
        Path path = Paths.get("/Users/lina/practice/java_practice/demo", "a.txt");
        Path path2 = Paths.get("/Users/lina/practice/java_practice/demo/b.txt");
        URI u = URI.create("file:////Users/lina/practice/java_practice/test");//不要Missing scheme file:///
        Path path3 = Paths.get(u);

        //1.2 FileSystems构造
        Path path4 = FileSystems.getDefault().getPath("/Users/lina/practice/java_practice/demo", "c.txt");

        //1.3 File和Path之间的转换
        File file = new File("/Users/lina/practice/java_practice/demo/d.txt");
        Path path5 = file.toPath();
        File file2 = path5.toFile();

        //1.4 File和URI之间的转换
        URI uri = file.toURI();

        //1.5 创建一个文件
        Path target = Paths.get("/Users/lina/practice/java_practice/demo/e.txt");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");

        try {
            if (!Files.exists(target)) {
                Files.createFile(target);
                Files.setPosixFilePermissions(target, perms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.6 Files.newBufferedReader读取文件
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/lina/practice/java_practice/demo/e.txt"), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.7 Files.newBufferedWriter写文件
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("/Users/lina/practice/java_practice/demo/e.txt"), StandardCharsets.UTF_8);
            writer.write("你好java");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.8 遍历一个文件夹
        Path dir = Paths.get("/Users/lina/practice/java_practice/demo");
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(dir);
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Stream<Path> s = Files.list(Paths.get("/Users/lina/practice/java_practice/demo"));
            Iterator<Path> ite = s.iterator();
            while (ite.hasNext()) {
                Path p = ite.next();
                System.out.println(p.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //1.9 使用Files.walkFileTree遍历整个文件目录
        Path startDir = Paths.get("/Users/lina/practice/java_practice");
        List<Path> result = new LinkedList<>();
        try {
            Files.walkFileTree(startDir, new FindJavaVistor(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("size: " + result.size());
        for (Path path : result) {
            System.out.println(path);
        }
    }

    private static class FindJavaVistor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVistor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }

    //2. 强大的java.nio.file.Files
    //2.1 创建目录和文件,创建目录和文件Files.createDirectories 和 Files.createFile不能混用，必须先有目录，才能在目录中创建文件。
    @Test
    public void testFiles() {
        //先创建目录，再创建文件
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test");
            Files.createDirectory(path);
            if (Files.exists(path)) {
                Files.createFile(Paths.get("/Users/lina/practice/java_practice/test/a.txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //在不存在的目录下创建文件失败 java.nio.file.NoSuchFileException: /Users/lina/practice/java_practice/test2/a.txt
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test2/a.txt");
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 2.2 文件复制
     * 从文件复制到文件：Files.copy(Path source, Path target, CopyOption options);
     * 从输入流复制到文件：Files.copy(InputStream in, Path target, CopyOption options);
     * 从文件复制到输出流：Files.copy(Path source, OutputStream out);
     */
    @Test
    public void testFiles2() {
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test");
            Path sourceFile = Paths.get("/Users/lina/practice/java_practice/test/b.txt");
            Path targetFile = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
            Path targetFile2 = Paths.get("/Users/lina/practice/java_practice/test/target2.txt");
            Files.createDirectory(path);
            if (Files.exists(path)) {
                Files.createFile(sourceFile);
                BufferedWriter writer = Files.newBufferedWriter(sourceFile, StandardCharsets.UTF_8);
                writer.write("hello world");
                writer.flush();
                writer.close();

                //从文件复制到文件
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);

                //从输入流复制到文件
                Files.copy(System.in, targetFile2);

                //从文件复制到输出流
                Files.copy(sourceFile, System.out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.3 读取文件属性
    @Test
    public void testFiles3() {
        Path zip = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
        try {
            System.out.println(Files.getLastModifiedTime(zip));
            System.out.println(Files.size(zip));
            System.out.println(Files.isSymbolicLink(zip));
            System.out.println(Files.isDirectory(zip));
            System.out.println(Files.readAttributes(zip, "*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.4 读取和设置文件权限
    @Test
    public void testFiles4() {
        Path profile = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
        PosixFileAttributes attrs = null;// 读取文件的权限
        try {
            attrs = Files.readAttributes(profile, PosixFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<PosixFilePermission> posixPermissions = attrs.permissions();
        posixPermissions.clear();
        String owner = attrs.owner().getName();
        String perms = PosixFilePermissions.toString(posixPermissions);
        System.out.format("%s %s%n", owner, perms);

        posixPermissions.add(PosixFilePermission.OWNER_READ);
        posixPermissions.add(PosixFilePermission.GROUP_READ);
        posixPermissions.add(PosixFilePermission.OTHERS_READ);
        posixPermissions.add(PosixFilePermission.OWNER_WRITE);

        try {
            Files.setPosixFilePermissions(profile, posixPermissions);    // 设置文件的权限
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```             
> 参考  https://www.cnblogs.com/devilwind/p/8623098.html          
> https://www.cnblogs.com/xidian2014/p/10326381.html            
### 14.java中IO流分为几种？BIO、NIO、AIO 有什么区别?
> 按功能/数据流向来分：输入流（input）、输出流（output）。             
  按数据类型来分：字节流和字符流。                
  字节流和字符流的区别是：字节流按8位传输，以字节为单位输入输出数据，字符流按16位传输，以字符为单位输入输出数据。              
  ![io1](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/io1.png)
> BIO、NIO、AIO有什么区别              
> BIO：Block IO 同步阻塞式IO，就是平常使用的传统IO，它的特点是模式简单使用方便，并发处理能力低。           
  NIO：New IO 同步非阻塞IO，是传统IO的升级，客户端和服务器端通过Channel（通道）通讯，实现了多路复用。              
  AIO：Asynchronous IO 是NIO的升级，也叫NIO2，实现了异步非堵塞IO ，异步IO的操作基于事件和回调机制。
> IO 流读写数据的特点：              
  顺序读写。读写数据时，大部分情况下都是按照顺序读写，读取时从文件开头的第一个字节到最后一个字节，写出时也是也如此（RandomAccessFile 可以实现随机读写）                              
  字节数组。读写数据时本质上都是对字节数组做读取和写出操作，即使是字符流，也是在字节流基础上转化为一个个字符，所以字节数组是IO流读写数据的本质。                       
> ![io2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/io2.png)                                                                        
> ![io3](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/io3.png)             
> 看到Stream就是字节流，看到Reader/Writer就是字符流。                   
  Java IO 提供了字节流转换为字符流的转换类，称为转换流。                 
> ![io4](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/io4.png)                                                                                                        
>                                           
> 参考 https://zhuanlan.zhihu.com/p/94121909                     
> https://www.cnblogs.com/LUA123/p/11389692.html                        
> https://github.com/crisxuan/bestJavaer/blob/master/java-basic/java-io.md              
### 15.java常用的集合容器都有哪些？
> ![collection](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/collection.png)               
>                                                                                                                                       
> ![map](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/map.png)                                                                                                         
### 16.Collection和Collections有什么区别？
> java.util.Collection是一个集合接口。它提供了对集合对象进行基本操作的通用接口方法。Collection接口在Java类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式。                           
  List，Set，Queue接口都继承Collection。                            
  直接实现该接口的类只有AbstractCollection类，该类只是一个抽象类，提供了对集合类操作的一些基本实现。List和Set的具体实现类基本上都直接或间接的继承了该类。                  
  java.util.Collections是一个包装类。它包含有各种有关集合操作的静态方法（对集合的搜索、排序、线程安全化等），大多数方法都是用来处理线性表的。此类不能实例化，是一个工具类，服务于Java的Collection框架。              
### 17.List、Set、Map之间的区别是什么？
> ![list_set_map_diff](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/list_set_map_diff.png)                                                                                                         
### 18.HashMap和Hashtable有什么区别？
> Hashmap是一个数组和链表的结合体（在数据结构称“链表散列“）
> ![hashmap](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap.png)                     
> 当向hashmap中put元素的时候，先根据key的hash值得到这个元素在数组中的位置（即下标），然后就可以把这个元素放到对应的位置中了。如果这个元素所在的位子上已经存放有其他元素了，那么在同一个位子上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。                
>               
> (1).继承不同              
  public class Hashtable extends Dictionary implements Map              
  public class HashMap  extends AbstractMap implements Map          
  (2).Hashtable线程安全，HashMap线程不安全                
  Hashtable中的方法是同步的，而HashMap中的方法在缺省情况下是非同步的。在多线程并发的环境下，可以直接使用Hashtable，但是要使用HashMap的话就要自己增加同步处理了。               
  (3).Hashtable中，key和value都不允许出现null值；HashMap中，key和value都允许出现null值          
  Hashtable中，key和value都不允许出现null值。          
  在HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。当get()方法返回null值时，即可以表示 HashMap中没有该键，
> 也可以表示该键所对应的值为null。因此，在HashMap中不能由get()方法来判断HashMap中是否存在某个键， 而应该用containsKey()方法来判断。               
  (4).两者遍历方式的内部实现上不同。               
  Hashtable、HashMap都使用了Iterator。而由于历史原因，Hashtable还使用了Enumeration的方式。            
  (5).哈希值的使用不同          
  HashTable直接使用对象的hashCode          
  HashMap重新计算hash值          
  (6).Hashtable和HashMap内部实现方式的数组的初始大小和扩容的方式不同               
  HashTable中hash数组默认大小是11，增加的方式是old*2+1。HashMap中hash数组的默认大小是16，而且一定是2的指数。                                                                                                                     
### 19.如何决定使用HashMap还是TreeMap？
> TreeMap<K,V>的Key值是要求实现java.lang.Comparable，所以迭代的时候TreeMap默认是按照Key值升序排序的；TreeMap的实现是基于红黑树结构。适用于按自然顺序或自定义顺序遍历键（key）。                              
  HashMap<K,V>的Key值实现散列hashCode()，分布是散列的、均匀的，不支持排序；数据结构主要是桶(数组)，链表或红黑树。适用于在Map中插入、删除和定位元素。              
>           
> 对于在Map中插入、删除和定位元素这类操作，HashMap是最好的选择。然而，假如需要对一个有序的key集合进行遍历，TreeMap是更好的选择。                                           
### 20.HashMap的实现原理？
> (1).HashMap的数据结构              
> HashMap基于哈希表实现，实际上是一个数组和链表的结合体。使用HashMap要求添加的键类明确定义了hashCode()和equals()(可以重写hashCode()和equals())，为了优化HashMap空间的使用，您可以调优初始容量和负载因子。                             
  HashMap(): 构建一个空的哈希映像             
  HashMap(Map m): 构建一个哈希映像，并且添加映像m的所有映射             
  HashMap(int initialCapacity): 构建一个拥有特定容量的空的哈希映像               
  HashMap(int initialCapacity, float loadFactor): 构建一个拥有特定容量和加载因子的空的哈希映像  
> ![hashmap2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap2.png)               
> 当新建一个hashmap的时候，就会初始化一个数组。            
> ![hashmap3](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap3.png)           
> Entry就是数组中的元素，它持有一个指向下一个元素的引用，这就构成了链表。                    
  当往hashmap中put元素时，先根据key的hash值得到这个元素在数组中的位置（即下标），然后就可以把这个元素放到对应的位置中了。
> 如果这个元素所在的位置上已经存放有其他元素了，那么在同一个位置上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。
> 从hashmap中get元素时，首先计算key的hashcode，找到数组中对应位置的某一元素，然后通过key的equals方法在对应位置的链表中找到需要的元素。
> 如果每个位置上的链表只有一个元素，那么hashmap的get效率将是最高的。                        
>                   
> (2).hash算法                    
> 在hashmap中要找到某个元素，需要根据key的hash值来求得对应数组中的位置。hashmap的数据结构是数组和链表的结合，
> 希望这个hashmap里面的元素位置尽量的分布均匀些，尽量使得每个位置上的元素数量只有一个，那么当用hash算法求得这个位置的时候，
> 马上就可以知道对应位置的元素就是想要的，而不用再去遍历链表。                        
  首先想到的就是把hashcode对数组长度取模运算，这样一来，元素的分布相对来说是比较均匀的。但是，“模”运算的消耗还是比较大的。
> 所以Java中是这样做的：首先算得key的hashcode值，然后跟数组的长度-1做一次“与”运算（&）。                                     
> static int indexFor(int h, int length) {                       
         return h & (length-1);                     
  }                 
  看上去很简单，其实比较有玄机。比如数组的长度是2的4次方，那么hashcode就会和2的4次方-1做“与”运算。                          
  为什么hashmap的数组初始化大小都是2的次方大小时，hashmap的效率最高？             
  以2的4次方举例，来解释一下为什么数组大小为2的幂时hashmap访问的性能最高。             
  看下图，左边两组是数组长度为16（2的4次方），右边两组是数组长度为15。两组的hashcode均为8和9，但是很明显，当它们和1110“与”的时候，
> 产生了相同的结果，也就是说它们会定位到数组中的同一个位置上去，这就产生了碰撞，8和9会被放到同一个链表上，那么查询的时候就需要遍历这个链表，
> 得到8或者9，这样就降低了查询的效率。当数组长度为15的时候，hashcode的值会与14（1110）进行“与”，那么最后一位永远是0，
> 而0001，0011，0101，1001，1011，0111，1101这几个位置永远都不能存放元素了，空间浪费相当大，更糟的是这种情况中，
> 数组可以使用的位置比数组长度小了很多，这意味着进一步增加了碰撞的几率，减慢了查询的效率！  
> ![hashmap4](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap4.png)           
> 所以当数组长度为2的n次幂的时候，不同的key算得得index相同的几率较小，那么数据在数组上分布就比较均匀，也就是说碰撞的几率小，相对的，查询的时候就不用遍历某个位置上的链表，这样查询效率也就较高了。                              
  在存储大容量数据的时候，最好预先指定hashmap的size为2的整数次幂次方。就算不指定的话，也会以大于且最接近指定值大小的2次幂来初始化的。              
>                   
> (3).HashMap的resize                    
> 当hashmap中的元素越来越多时，碰撞的几率也就越来越高（因为数组的长度是固定的），所以为了提高查询的效率，就要对hashmap的数组进行扩容，
> 在hashmap数组扩容之后，最消耗性能的点就出现了：原数组中的数据必须重新计算其在新数组中的位置，并放进去，这就是resize。             
  那么hashmap什么时候进行扩容呢？当hashmap中的元素个数超过数组大小*loadFactor时，就会进行数组扩容，loadFactor的默认值为0.75，
> 也就是说，默认情况下，数组大小为16，那么当hashmap中元素个数超过16*0.75=12的时候，就把数组的大小扩展为2*16=32，即扩大一倍，
> 然后重新计算每个元素在数组中的位置，而这是一个非常消耗性能的操作，所以如果已经预知hashmap中元素的个数，那么预设元素的个数能够有效的提高hashmap的性能。
> 比如说，有1000个元素new HashMap(1000), 但是理论上来讲new HashMap(1024)更合适，即使是1000，hashmap也自动会将其设置为1024。
> 但是new HashMap(1024)还不是更合适的，因为0.75*1000 < 1000, 也就是说为了让0.75 * size > 1000, 必须这样new HashMap(2048)才最合适，既考虑了&的问题，也避免了resize的问题。                           
>                                   
> (4)key的hashcode与equals方法改写                        
  hashmap的get方法的过程：首先计算key的hashcode，找到数组中对应位置的某一元素，然后通过key的equals方法在对应位置的链表中找到需要的元素。
> 所以，hashcode与equals方法对于找到对应元素是两个关键方法。                  
  Hashmap的key可以是任何类型的对象，但一定要是不可变对象。                 
  在改写equals方法的时候，需要满足以下三点：                      
  自反性：就是说a.equals(a)必须为true。                            
  对称性：就是说a.equals(b)=true的话，b.equals(a)也必须为true。                    
  传递性：就是说a.equals(b)=true，并且b.equals(c)=true的话，a.equals(c)也必须为true。                         
  通过改写key对象的equals和hashcode方法，可以将任意的业务对象作为map的key(前提是你确实有这样的需要)。 
>                                      
> (5).JDK1.8中对HashMap的优化                
> [1].HashMap是数组+链表+红黑树（JDK1.8增加了红黑树部分）实现的                  
> 当链表长度太长（TREEIFY_THRESHOLD默认超过8）时，链表就转换为红黑树，利用红黑树快速增删改查的特点提高HashMap的性能,从原来的O(n)到O(logn)。当长度小于（UNTREEIFY_THRESHOLD默认为6），就会退化成链表。
  HashMap 中关于红黑树的三个关键参数             
> ![hashmap5](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap5.png)                   
> [2].扩容机制              
> 使用的是2次幂的扩展(指长度扩为原来2倍)，所以元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置。                    
> 图（a）表示扩容前的key1和key2两种key确定索引位置的示例，图（b）表示扩容后key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果。              
> ![hashmap6](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap6.png)                                          
> 元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：            
> ![hashmap7](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap7.png)           
> 因此，在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，
> 是1的话索引变成“原索引+oldCap”，可以看看下图为16扩充为32的resize示意图：                    
> ![hashmap8](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/hashmap8.png)               
> 这个设计确实非常的巧妙，既省去了重新计算hash值的时间，而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，
> 均匀的把之前的冲突的节点分散到新的bucket了。这一块就是JDK1.8新增的优化点。有一点注意区别，JDK1.7中rehash的时候，旧链表迁移新链表的时候，
> 如果在新表的数组索引位置相同，则链表元素会倒置，但是从上图可以看出，JDK1.8不会倒置。                                          
>                                     
> 参考 https://www.cnblogs.com/xidian2014/p/10466611.html                 
### 21.HashSet的实现原理？
> HashSet中不允许有重复元素，这是因为HashSet是基于HashMap实现的，HashSet中的元素都存放在HashMap的key上面，
> 而value中的值都是统一的一个private static final Object PRESENT = new Object();。HashSet跟HashMap一样，都是一个存放链表的数组。                
>               
> HashSet底层由HashMap实现               
  HashSet的值存放于HashMap的key上              
  HashMap的value统一为PRESENT               
### 22.ArrayList和LinkedList的区别是什么？
> (1).从数据结构上看，ArrayList是实现了基于动态数组的结构，而LinkedList则是基于实现链表的数据结构。              
  (2).对于随机访问get和set，ArrayList要优于LinkedList，因为LinkedList要移动指针。               
  (3).对于添加和删除操作add和remove，一般认为LinkedList要比ArrayList快，因为ArrayList要移动数据。
> 但是实际情况并非这样，对于添加或删除，LinkedList和ArrayList并不能明确说明谁快谁慢。                             
  当插入的数据量很小时，两者区别不太大，当插入的数据量大时，大约在容量的1/10之前，LinkedList会优于ArrayList，在其后就劣与ArrayList，且越靠近后面越差。                
  所以，一般首选用ArrayList，由于LinkedList可以实现栈、队列以及双端队列等数据结构，所以当特定需要时候，使用LinkedList。数据量小的时候，两者差不多，
> 视具体情况去选择使用；当数据量大的时候，如果只需要在靠前的部分插入或删除数据，那也可以选用LinkedList，反之选择ArrayList反而效率更高。                      
### 23.如何实现数组和List之间的转换？
> 数组转List ，使用JDK中java.util.Arrays工具类的asList方法               
```java
package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ArrayToList {
    public static void main(String[] args) {
        String[] strArray = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = Arrays.asList(strArray);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
```
>                           
> List转数组，使用 List 的toArray方法。（无参toArray方法返回Object数组，传入初始化长度的数组对象，返回该对象数组）               
```java
package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ListToArray {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "cd", "dfdd");
        String[] strArray = list.toArray(new String[list.size()]);
        for (String s : strArray) {
            System.out.println(s);
        }
    }
}
```
### 24.ArrayList和Vector的区别是什么？
> (1).Vector是多线程安全的，而ArrayList不是，Vector类中的方法很多有synchronized进行修饰，这样就导致了Vector在效率上无法与ArrayList相比                                
  (2).两个都是采用的线性连续空间存储元素，但是当空间不足的时候，两个类的增加方式是不同的，ArrayList在底层数组不够用时在原来的基础上扩展0.5倍，
> Vector如果设置了增长因子，扩容的容量是(oldCapacity+capacityIncrement)；Vector如果没有设置增长因子，扩容后的容量就是(oldCapacity+oldCapacity)，就是原来容量的二倍。                   
> (3).Vector可以设置增长因子，而ArrayList不可以              
>                   
> ArrayList有三个构造方法：                 
  public ArrayList(int initialCapacity)//构造一个具有指定初始容量的空列表。                  
  public ArrayList()//构造一个初始容量为10的空列表。              
  public ArrayList(Collection<? extends E> c)//构造一个包含指定 collection 的元素的列表               
  Vector有四个构造方法：                
  public Vector()//使用指定的初始容量和等于零的容量增量构造一个空向量。               
  public Vector(int initialCapacity)//构造一个空向量，使其内部数据数组的大小，其标准容量增量为零。                
  public Vector(Collection<? extends E> c)//构造一个包含指定 collection 中的元素的向量             
  public Vector(int initialCapacity,int capacityIncrement)//使用指定的初始容量和容量增量构造一个空的向量              
  Vector比Arraylist多一个构造方法public Vector(int initialCapacity,int capacityIncrement)，capacityIncrement就是增长因子，ArrayList中是没有的。           
>                                  
> 在迭代的时候对列表进行改变，应该使用CopyOnWriteArrayList。     
>               
> 参考 https://blog.csdn.net/tayanxunhua/article/details/10037403                       
### 25.Array和ArrayList有何区别？
> (1).数据类型方面:               
  Array数组可以包含基本类型和对象类型;ArrayList只能包含对象类型。               
  Array数组在存放的时候一定是同种类型的元素，ArrayList存入对象时，抛弃类型信息，所有对象屏蔽为Object，编译时不检查类型，但是运行时会报错。                
  (2).容量方面:             
  Array容量是静态固定的。                
  ArrayList是动态变化的。              
  (3).支持操作:             
  ArrayList提供更多操作，添加全部addAll()、删除全部removeAll()、返回迭代器iterator()等。                
  Array提供的更多是依赖于Arrays提供的方法。                
  使用建议:         
  当集合长度固定时，使用数组；当集合的长度不固定时，使用ArrayList。但如果长度增长频繁，应考虑预设ArrayList的长度或者使用链表LinkedList代替，ArrayList每次扩容都要进行数组的拷贝。                
  由于ArrayList不支持基本数据类型，所以保存基本数据类型时需要装箱处理，对比数组性能会下降。这种情况尽量使用数组。              
  数组支持的操作方法很少，但内存占用少，如果只需对集合进行随机读写，选数组；如果需要进行插入和删除，使用数组的话，需要手动编写移动元素的代码，ArrayList中内置了这些操作，开发更方便。                
  如果单纯只是想要以数组的形式保存数据，而不对数据进行增加等操作，只是方便进行查找的话，那么就选择ArrayList。                
  如果需要对元素进行频繁的移动或删除，或者是处理的是超大量的数据，使用ArrayList就真的不是一个好的选择，因为它的效率很低，使用数组进行这样的动作也很麻烦，那么，可以考虑选择LinkedList。              
### 26.在Queue中poll()和remove()有什么区别？
> Queue中remove()和poll()都是用来从队列头部删除一个元素。                         
  在队列元素为空的情况下，remove()方法会抛出NoSuchElementException异常，poll()方法只会返回null。                   
### 27.哪些集合类是线程安全的？
> Vector:就比Arraylist多了个同步化机制（线程安全）。                 
  Stack:栈也是线程安全的，继承于Vector。             
  Hashtable:就比Hashmap多了个线程安全。               
  java.util.concurrent包下所有的集合类ArrayBlockingQueue、ConcurrentHashMap、ConcurrentLinkedQueue、ConcurrentLinkedDeque等。                
  CopyOnWriteArrayList和CopyOnWriteArraySet是加了写锁的ArrayList和ArraySet，锁住的是整个对象，但读操作可以并发执行。                 
  Collections包装的方法：                 
  Vector和HashTable被弃用后，它们被ArrayList和HashMap代替，但它们不是线程安全的，所以Collections工具类中提供了相应的包装方法把它们包装成线程安全的集合                          
  List<E> synArrayList = Collections.synchronizedList(new ArrayList<E>());                            
  Set<E> synHashSet = Collections.synchronizedSet(new HashSet<E>());                                
  Map<K,V> synHashMap = Collections.synchronizedMap(new HashMap<K,V>());                                                                   
### 28.写时复制(CopyOnWrite)
> CopyOnWrite容器即写时复制的容器。CopyOnWrite是计算机程序设计领域中的一种优化策略。其核心思想是，如果有多个调用者（Callers）同时要求相同的资源（如内存或者是磁盘上的数据存储），
> 他们会共同获取相同的指针指向相同的资源，直到某个调用者试图修改资源内容时，系统才会真正复制一份专用副本（private copy）给该调用者，而其他调用者所见到的最初的资源仍然保持不变。
> 这过程对其他的调用者都是透明的（transparently）。此做法主要的优点是如果调用者没有修改资源，就不会有副本（private copy）被创建，因此多个调用者只是读取操作时可以共享同一份资源。              
>                   
> CopyOnWriteArrayList特点:                                                           
  CopyOnWriteArrayList是线程安全容器(相对于ArrayList)，底层通过复制数组的方式来实现。                          
  CopyOnWriteArrayList在遍历的使用不会抛出ConcurrentModificationException异常，并且遍历的时候就不用额外加锁。                             
  元素可以为null。                            
  CopyOnWriteArrayList底层就是数组，加锁就交由ReentrantLock(在jdk11的时候采用了synchronized来替换ReentrantLock)来完成。                              
  在修改时，复制出一个新数组，修改的操作在新数组中完成，最后将新数组交由array变量指向。                      
  写加锁，读不加锁。         
>                                        
> CopyOnWriteArrayList缺点:                              
  内存占用:如果CopyOnWriteArrayList经常要增删改里面的数据，经常要执行add()、set()、remove()的话，那是比较耗费内存的。
> 因为每次add()、set()、remove()这些增删改操作都要复制一个数组出来。如果这个数组对象占用的内存较大的话，频繁的进行写入就会造成频繁的Yong GC和Full GC。                    
  数据一致性:CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
>                                        
> CopyOnWriteArraySet的底层实现就是CopyOnWriteArrayList。               
>                   
> 应用场景:                 
  CopyOnWrite并发容器适用于读多写少的并发场景，比如黑白名单、国家城市等基础数据缓存、系统配置等。             
>               
> 参考 https://segmentfault.com/a/1190000016931825                
> https://segmentfault.com/a/1190000038751180               
### 29.迭代器Iterator是什么？Iterator怎么使用？有什么特点？
> 迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。                
  Iterator提供了遍历操作集合元素的统一接口，Collection接口实现了Iterable接口，每个集合都通过实现Iterable接口中的iterator()方法返回Iterator接口的实例，然后对集合的元素进行迭代操作。                   
  迭代器可以在迭代过程中删除底层集合的元素，但是不可以直接调用集合的remove(Object obj)删除，可以通过迭代器的remove()方法删除。                   
>                        
> Java中的Iterator功能比较简单，并且只能单向移动：                    
  (1).使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。                                   
  (2).使用next()获得序列中的下一个元素。                                   
  (3).使用hasNext()检查序列中是否还有元素。                                  
  (4).使用remove()将迭代器新返回的元素删除。                                  
  Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。                                      
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
  ListIterator的方法如下：                
  hasNext()：如果返回true，则确认集合中有更多元素。               
  next()：返回列表的下一个元素。                
  nextIndex()：返回列表中下一个元素的索引。                
  hasPrevious()：如果集合中有相反的元素，则返回true。                
  previous()：返回集合中的上一个元素。               
  previousIndex()：返回集合中上一个元素的索引。                
  remove()：从集合中删除元素。                
  set()：修改集合中的元素。               
  add()：在集合中添加新元素。              
