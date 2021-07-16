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
> try-catch-finally 中，如果catch中 return 了，finally 还会执行吗？              
> 会执行，在 return 前执行。         
```java
package com.java.topic;

/**
 * 如果catch里面有return语句，finally里面的代码还会执行吗？
 */
public class FinallyDemo {
    public static void main(String[] args) {
        //执行结果是 30
        System.out.println(getInt());

        //执行结果是 40
        System.out.println(getInt2());
    }

    public static int getInt() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，a=40
             * 再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
            return a;
        } finally {
            a = 40;
        }

        return a;
    }

    public static int getInt2() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，a=40
             * 再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
            return a;
        } finally {
            a = 40;
            //finally中有return就又重新形成了一条返回路径，由于只能通过1个return返回，所以这里直接返回40
            return a;
        }
    }
}
``` 
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