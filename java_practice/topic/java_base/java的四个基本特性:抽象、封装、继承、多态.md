### 39.java的四个基本特性:抽象、封装、继承、多态
>(1).抽象是将一类对象的共同特征总结出来构造类的过程，包括数据抽象和行为抽象。抽象只关注对象有哪些属性和行为，并不关注行为的细节。             
 (2).继承是从已有类得到继承信息创造新类的过程。提供继承信息的类称为父类、基类或者超类。得到继续信息的类称为子类或者派生类。
 继承让变化的软件系统有了一定的延续性，同时继承也是封装过程中可变因素的重要手段。                                              
 (3).封装是把数据和操作数据的方法绑定起来，对数据的访问只能通过已定义的接口。面向对象的本质就是将现实世界描绘成一系列完全自治、封闭的对象。
 在类中编写方法就是对实现细节的一种封装，编写一个类就是对数据和数据操作的封装。可以说封装就是隐藏一切可隐藏的东西，只向外界提供最简单的编程接口。                    
 (4).多态是指允许不同子类型的对象对同一消息作出不同的响应。                
 对态有方法重载和方法重写两种实现方式：                        
 方法重载是编译时的多态性(前绑定)。         
 方法重写是运行时的多态性(后绑定)。运行时多态时面向对象最精髓的东西。            
 多态的实现:             
 [1].方法重写(子类继承父类方法并重写父类中的已有抽象方法)                
 [2].对象造型(用父类型引用子类型对象，同样的引用调用同样的方法就会根据子类对象的不同而表现出不同的行为)                 
