java基础题目
======
## 问题列表
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
### 14.java中IO流分为几种？
> 按功能/数据流向来分：输入流（input）、输出流（output）。             
  按数据类型来分：字节流和字符流。                
  字节流和字符流的区别是：字节流按8位传输，以字节为单位输入输出数据，字符流按16位传输，以字符为单位输入输出数据。              
  ![io1](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/io1.png)
            
### 15.
### 16.
### 17.
### 18.
### 19.
### 20.