### 4.final关键字
> final关键字可以修饰的对象有三个:一是修饰变量、二是修饰方法、三是修饰类            
  (1).final关键字修饰变量              
  1).final修饰成员变量                
  final关键字在修饰基本数据类型时必须对变量赋予初始值，因此,final关键字也经常和static关键字一起用来声明常量值，final正好限制了必须赋值、static声明了静态变量。              
  fianl最常见的用法是用来修饰成员变量,成员变量分为静态变量与普通变量.             
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