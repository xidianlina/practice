### 51.init和clinit区别
>(1).init和clinit方法执行目的不同                    
 init是根据对象构造器传递的参数对非静态变量解析初始化。                  
 clinit是class类构造器，对静态变量以及静态代码块进行初始化。                    
>       
>(2).init和clinit方法执行时机不同                                
 init是对象构造器方法，在程序执行new关键字创建对象时，调用该类的构造方法时才会执行init方法。                          
 clinit是类构造器方法，在JVM进行类加载——验证——准备——解析等初始化阶段会调用clinit方法。                              