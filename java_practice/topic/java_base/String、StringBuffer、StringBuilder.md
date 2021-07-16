### 10.java中操作字符串都有哪些类？它们之间有什么区别？   String、StringBuffer、StringBuilder
> 操作字符串的类有:String、StringBuffer、StringBuilder。                   
  String和StringBuffer、StringBuilder的区别在于String声明的是不可变的对象，在JDK中String类被声明为一个final类，
> 每次操作都会生成新的String对象，然后将指针指向新的String对象，而StringBuffer、StringBuilder可以在原有对象的基础上进行操作，
> 所以在经常改变字符串内容的情况下最好不要使用String。             
  StringBuffer和StringBuilder最大的区别在于，StringBuffer是线程安全的，而StringBuilder是非线程安全的，线程安全会带来额外的系统开销，
> StringBuilder的性能却高于StringBuffer，所以在单线程环境下推荐使用StringBuilder，多线程环境下推荐使用StringBuffer。        
> String s=new String("hello,world");可能创建两个对象也可能创建一个对象，如果静态区中有"hello,world"字符串常量，则仅在堆中创建一个对象;
> 如果静态区中没有"hello,world"对象，则静态区和堆上都需要创建对象。     
>                       
> String str="i"与 String str=new String("i")内存的分配方式不一样。String str="i"的方式，java虚拟机会将其分配到常量池中；
>而String str=new String("i")则会被分到堆内存中。                                
