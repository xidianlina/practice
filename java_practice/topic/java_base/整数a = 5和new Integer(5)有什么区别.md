### 45.整数a = 5和new Integer(5)有什么区别？
>Java5引入自动装箱和自动拆箱,Integer a = 5;变量a为Integer类型，而5为int类型，且Integer和int之间并无继承关系，按照Java的一般处理方法，这行代码应该报错。             
 但因为自动装箱机制的存在，在为Integer类型的变量赋int类型值时，Java会自动将int类型转换为Integer类型，即Integer a = Integer.valueOf(1);             
 valueOf()方法返回一个Integer类型值，并将其赋值给变量a。这就是int的自动装箱。               
 自动装箱会将int转换Integer类型值并返回，Java中两个new出来的对象是不同的实例，无论如何==都会返回fasle。                
 Integer a = 5;                 
 Integer b = 5;                 
 System.out.println(a == b);// true                      
 以上输出true是因为自动装箱并不一定new出新的对象。               
 如果int型参数i在IntegerCache.low(-128)和IntegerCache.high(127)范围内，则直接由IntegerCache返回；否则new一个新的对象返回。               
 Integer.class在装载（Java虚拟机启动）时，其内部类型IntegerCache的static块即开始执行，实例化并暂存数值在-128到127之间的Integer类型对象。               
 当自动装箱int型值在-128到127之间时，即直接返回IntegerCache中暂存的Integer类型对象。               
 为什么Java这么设计？                   
 出于效率考虑，因为自动装箱经常遇到，尤其是小数值的自动装箱；而如果每次自动装箱都触发new，在堆中分配内存，就显得太慢了；
>所以不如预先将那些常用的值提前生成好，自动装箱时直接拿出来返回。哪些值是常用的？就是-128到127了。               
