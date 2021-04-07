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
> 参考 https://segmentfault.com/a/1190000022794717                
> https://segmentfault.com/a/1190000025188850
### 6.final、finally 和 finalize 
> 
### 7.类加载顺序        
> 加载父类的静态字段 -> 父类的静态代码块 -> 子类静态字段 -> 子类静态代码块 -> 父类成员变量（非静态字段）-> 父类非静态代码块 -> 父类构造器 -> 子类成员变量 -> 子类非静态代码块 -> 子类构造器                    
### 8.构造器是静态方法吗？
### 9. 