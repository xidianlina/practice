## 四.单例模式
> 所有类都有构造方法，不编码则系统默认生成空的构造方法，若有显示定义的构造方法，默认的构造方法就会失效。                              
  单例模式，保证一个类仅有一个实例，并提供一个访问它的全局访问点。                                
  通常可以让一个全局变量使得一个对象被访问，但它不能防止你实例化多个对象。一个最好的办法就是让类自身负责保存它的唯一实例。
> 这个类可以保证没有其他实例可以被创建，并且它可以提供一个访问该实例的方法。 
> ![singleton](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/singleton.png)         
> 懒汉式Singleton类，定义一个getInstance方法，允许客户访问它的唯一实例。         
>懒汉模式在方法被调用后才创建对象，以时间换空间，在多线程环境下存在风险。            
```java
package com.java.topic.design_pattern;

public class Singleton {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton() {
    }

    private static Singleton instance;

    // 该方法是获取本类实例的唯一途径
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```            
> 单例模式因为Singleton类封装它的唯一实例，这样它可以严格地控制客户怎样访问它以及何时访问它，简单地说就是对唯一实例的受控访问。                    
  单例模式有以下特点：                                
  [1].单例类只能有一个实例。                   
  [2].单例类必须自己创建自己的唯一实例。                 
  [3].单例类必须给所有其他对象提供这一实例。               
  单例模式确保某个类只有一个实例，而且自行实例化并向整个系统提供这个实例。在计算机系统中，线程池、缓存、日志对象、对话框、打印机、显卡的驱动程序对象常被设计成单例。
> 这些应用都或多或少具有资源管理器的功能。每台计算机可以有若干个打印机，但只能有一个工作，以避免两个打印作业同时输出到打印机中。每台计算机可以有若干通信端口，
> 系统应当集中管理这些通信端口，以避免一个通信端口同时被两个请求同时调用。总之，选择单例模式就是为了避免不一致状态，避免政出多头。              
  懒汉式单例的实现是线程不安全的，并发环境下很可能出现多个Singleton实例，要实现线程安全，有以下三种方式，都是对getInstance()这个方法改造，保证了懒汉式单例的线程安全。                                         
> (1).在getInstance方法上加同步            
```java
package com.java.topic.design_pattern;

public class Singleton2 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton2() {
    }

    private static Singleton2 instance;

    // 该方法是获取本类实例的唯一途径
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }

        return instance;
    }
}
```   
> (2).双重检查锁定            
```java
package com.java.topic.design_pattern;

public class Singleton3 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton3() {
    }

    private static Singleton3 instance;

    // 该方法是获取本类实例的唯一途径
    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }

        return instance;
    }
}
```  
> 进行双重检查的原因：当instance为null并且同时有两个线程调用getInstance（）方法时，它们都可以通过第一重instance==null的判断。
> 然后由于synchronized机制，这两个线程则只有一个进入，另一个在外排队等待，必须要其中的一个进入并出来后，另一个才能进入。
> 而此时如果没有第二重的instance是否为null的判断。则第一个线程创建了实例，而第二个线程还是可以继续再创建新的实例，这就没有达到单例的目的。                    
>                       
>DCL失效问题:                   
 INSTANCE  = new SingleTon();在jvm里面的执行分为三步：                            
 1.在堆内存开辟内存空间。              
 2.在堆内存中实例化SingleTon里面的各个参数。                
 3.把对象指向堆内存空间。                  
 由于jvm存在乱序执行功能，所以可能在2还没执行时就先执行了3，如果此时再被切换到线程B上，由于执行了3，INSTANCE 已经非空了，会被直接拿出来用，这样的话，就会出现异常。这个就是著名的DCL失效问题。                   
 不过在JDK1.5之后，官方也发现了这个问题，故而具体化了volatile，即在JDK1.6及以后，只要定义为private volatile static SingleTon  INSTANCE = null;就可解决DCL失效问题。volatile确保INSTANCE每次均在主内存中读取，这样虽然会牺牲一点效率，但也无伤大雅。                             
            
> (3).静态内部类  既实现了线程安全，又避免了同步带来的性能影响         
```java
package com.java.topic.design_pattern;

public class Singleton4 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton4() {
    }

    private static class LazyHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    // 该方法是获取本类实例的唯一途径
    public static final Singleton4 getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```    
>静态内部类的优点:外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。即当Singleton第一次被加载时，
>并不需要去加载LazyHolder类，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE,第一次调用getInstance()方法会导致虚拟机加载LazyHolder类，
>这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。                                     
 静态内部类的缺点:传参的问题，由于是静态内部类的形式去创建单例的，故外部无法传递参数进去。                              
>静态内部类如何实现线程安全:                     
 类加载时机:JAVA虚拟机在有且仅有的5种场景下会对类进行初始化。                      
 1.遇到new、getstatic、setstatic或者invokestatic这4个字节码指令时，对应的java代码场景为：new一个关键字或者一个实例化对象时、读取或设置一个静态字段时(final修饰、已在编译期把结果放入常量池的除外)、调用一个类的静态方法时。                                 
 2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行初始化，需要先调用其初始化方法进行初始化。               
 3.当初始化一个类时，如果其父类还未进行初始化，会先触发其父类的初始化。                   
 4.当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的类)，虚拟机会先初始化这个类。                        
 5.当使用JDK 1.7等动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。                 
 这5种情况被称为是类的主动引用，那么，除此之外的所有引用类都不会对类进行初始化，称为被动引用。静态内部类就属于被动引用的行列。                    
>               
>虚拟机会保证一个类的clinit()方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的clinit()方法，
>其他线程都需要阻塞等待，直到活动线程执行clinit()方法完毕。在实际应用中，这种阻塞往往是很隐蔽的。                 
>getInstance()方法，调用的是LazyHolder.INSTANCE，取的是LazyHolder里的INSTANCE对象，getInstance()方法并没有多次去new对象，
>故不管多少个线程去调用getInstance()方法，取的都是同一个INSTANCE对象，而不用去重新创建。                     
 当getInstance()方法被调用时，LazyHolder才在SingleTon的运行时常量池里，把符号引用替换为直接引用，这时静态对象INSTANCE也真正被创建，然后再被getInstance()方法返回出去。                      
>故而，可以看出INSTANCE在创建过程中是线程安全的，所以说静态内部类形式的单例可保证线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。                                          
>               
> 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，以空间换时间，所以天生就是线程安全的。                  
```java
package com.java.topic.design_pattern;

public class Singleton5 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton5() {
    }

    private static final Singleton5 instance = new Singleton5();

    // 该方法是获取本类实例的唯一途径
    public static Singleton5 getInstance() {
        return instance;
    }
}
``` 
> 饿汉式和懒汉式的区别：饿汉式在类加载时就把单例初始化完成，保证getInstance的时候，单例是已经存在的了，而懒汉式比较懒，只有当调用getInstance的时候，才回去初始化这个单例。               
  从线程安全和资源加载及性能区分饿汉式和懒汉式：                              
  [1].线程安全：             
  饿汉式天生就是线程安全的，可以直接用于多线程而不会出现问题;                                
  懒汉式本身是非线程安全的，为了实现线程安全有几种写法，分别是上面的(1)、(2)、(3)这三种实现在资源加载和性能方面有些区别。              
  [2].资源加载和性能：              
  饿汉式在类创建的同时就实例化一个静态对象出来，不管之后会不会使用这个单例，都会占据一定的内存，但是相应的，在第一次调用时速度也会更快，
> 因为其资源已经初始化完成，而懒汉式顾名思义，会延迟加载，在第一次使用该单例的时候才会实例化对象出来，第一次调用时要做初始化，如果要做的工作比较多，性能上会有些延迟，之后就和饿汉式一样了。             
  至于(1)、(2)、(3)这三种实现又有些区别：                                  
  第(1)种，在方法调用上加了同步，虽然线程安全了，但是每次都要同步，会影响性能，毕竟99%的情况下是不需要同步的；                          
  第(2)种，在getInstance中做了两次null检查，确保了只有第一次调用单例的时候才会做同步，这样也是线程安全的，同时避免了每次都同步的性能损耗;                                            
  第(3)种，利用了classloader的机制来保证初始化instance时只有一个线程，所以也是线程安全的，同时没有性能损耗，所以一般我倾向于使用这一种。                
> 什么是线程安全？                      
  如果一段代码所在的进程中有多个线程在同时运行，而这些线程可能会同时运行这段代码。如果每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。             
> 优点：           
  提供了对唯一实例的受控访问。因为单例类封装了它的唯一实例，所以它可以严格控制客户怎样以及何时访问它。                
  因为该类在系统内存中只存在一个对象，所以可以节约系统资源。             
  缺点：               
  由于单例模式中没有抽象层，因此单例类很难进行扩展。                              
  对于有垃圾回收系统的语言Java来说，如果对象长时间不被利用，则可能会被回收。那么如果这个单例持有一些数据的话，在回收后重新实例化时就不复存在了。                                                         
