设计模式
======
## 软件系统设计的六大原则
### 1.开闭原则
> 一个软件实体应当对扩展开放，对修改关闭。即软件实体应尽量在不修改原有代码的情况下进行扩展。         
> 为了满足开闭原则，需要对系统进行抽象化设计，抽象化是开闭原则的关键。                                 
> 优点：实践开闭原则的优点在于可以在不改动原有代码的前提下给程序扩展功能。增加了程序的可扩展性，同时也降低了程序的维护成本。             
### 2.里氏替换原则            
> 所有引用基类对象的地方能够透明地使用其子类的对象          
> 优点:可以检验继承使用的正确性，约束继承在使用上的泛滥。      
### 3.依赖倒置原则
> 抽象不应该依赖于具体类，具体类应当依赖于抽象。换言之，要针对接口编程，而不是针对实现编程。             
> 优点:通过抽象来搭建框架，建立类和类的关联，以减少类间的耦合性。而且以抽象搭建的系统要比以具体实现搭建的系统更加稳定，扩展性更高，同时也便于维护。
### 4.单一职责原则
> 一个类只负责一个功能领域中的相应职责，或者可以定义为：就一个类而言，应该只有一个引起它变化的原因。                  
> 优点：如果类与方法的职责划分得很清晰，不但可以提高代码的可读性，更实际性地更降低了程序出错的风险，因为清晰的代码会让 bug 无处藏身，也有利于 bug 的追踪，也就是降低了程序的维护成本。
### 5.迪米特法则（最少知道原则）
> 一个软件实体应当尽可能少地与其他实体发生相互作用              
> 优点：实践迪米特法则可以良好地降低类与类之间的耦合，减少类与类之间的关联程度，让类与类之间的协作更加直接。
### 6.接口分离原则
> 使用多个专门的接口，而不使用单一的总接口，即客户端不应该依赖那些它不需要的接口。          
> 优点:避免同一个接口里面包含不同类职责的方法，接口责任划分更加明确，符合高内聚低耦合的思想。
### 合成复用原则（六大之外的）
> 尽量使用对象组合，而不是继承来达到复用的目的            
> 优点:避免复用时滥用继承，合理使用组合关系，增加灵活性。 
## 模式分类                                               
> 设计模式可分为创建型(Creational)，结构型(Structural)和行为型(Behavioral)三种，其中创建型模式主要用于描述如何创建对象，
> 结构型模式主要用于描述如何实现类或对象的组合，行为型模式主要用于描述类或对象怎样交互以及怎样分配职责。           
> 此外，根据某个模式主要是用于处理类之间的关系还是对象之间的关系，设计模式还可以分为类模式和对象模式。
> 经常将两种分类方式结合使用，如单例模式是对象创建型模式，模板方法模式是类行为型模式。            
### 创建型
> 创建型模式(Creational Pattern)对类的实例化过程进行了抽象，能够将模块中对象的创建和对象的使用分离。
> 为了使结构更加清晰，外界对于这些对象只需要知道它们共同的接口，而不清楚其具体的实现细节，使整个系统的设计更加符合单一职责原则。               
> 1.简单工厂模式（Simple Factory Pattern）              
  2.工厂方法模式（Factory Method Pattern）                  
  3.抽象工厂模式（Abstract Factory Pattern）              
  4.单例模式（Singleton Pattern）               
  5.生成器模式（Builder Pattern）                    
  6.原型模式（Prototype Pattern）                                                                          
### 结构型
> 结构型模式(Structural Pattern)描述如何将类或者对象结合在一起形成更大的结构，就像搭积木，可以通过简单积木的组合形成复杂的、功能更为强大的结构。
> 结构型模式可以分为类结构型模式和对象结构型模式：                  
> 类结构型模式关心类的组合，由多个类可以组合成一个更大的系统，在类结构型模式中一般只存在继承关系和实现关系。             
> 对象结构型模式关心类与对象的组合，通过关联关系使得在一个类中定义另一个类的实例对象，然后通过该对象调用其方法。 
> 根据“合成复用原则”，在系统中尽量使用关联关系来替代继承关系，因此大部分结构型模式都是对象结构型模式。                          
> 1.外观模式                  
  2.适配器模式             
  3.桥接模式                      
  4.代理模式          
  5.装饰者模式         
  6.享元模式                                                                
### 行为型
> 行为型模式(Behavioral Pattern)是对在不同的对象之间划分责任和算法的抽象化。行为型模式不仅仅关注类和对象的结构，
> 而且重点关注它们之间的相互作用。通过行为型模式，可以更加清晰地划分类与对象的职责，并研究系统在运行时实例对象之间的交互。                  
> 1.职责链模式         
  2.命令模式          
  3.解释器模式             
  4.迭代器模式             
  5.中介者模式             
  6.备忘录模式             
  7.观察者模式             
  8.状态模式              
  9.策略模式              
  10.模板方法模式                
  11.访问者模式                                                               
## 一.简单工厂模式
> 专门定义一个类（工厂类）来负责创建其他类的实例。可以根据创建方法的参数来返回不同类的实例，被创建的实例通常都具有共同的父类。            
> ![simple_factory_pattern](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/simple_factory_pattern.png)           
抽象产品类代码：            
```java
package com.java.topic.design_pattern;

public interface Shape {
    public void sayHello();
}
``` 
> 具体产品类代码： 
```java
package com.java.topic.design_pattern;

public class Circle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am circle");
    }
}
```       
                
```java
package com.java.topic.design_pattern;

public class Triangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am triangle");
    }
}
```  
            
```java
package com.java.topic.design_pattern;

public class Rectangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am rectangle");
    }
}
```     

```java
package com.java.topic.design_pattern;

public enum ShapeType {
    CIRCLE, TRIANGLE, RECTANGLE
}
```     
> 简单工厂核心代码： 
```java
package com.java.topic.design_pattern;

public class SimpleFactory {
    public Shape GetShape(ShapeType shapeType) throws Exception {
        switch (shapeType) {
            case CIRCLE:
                return new Circle();
            case TRIANGLE:
                return new Triangle();
            case RECTANGLE:
                return new Rectangle();
            default:
                throw new Exception("ShapeType Exception");
        }
    }
}
```
> 客户端调用代码：
```java
package com.java.topic.design_pattern;

public class SimpleFactoryClient {
    public static void main(String[] args) {
        Shape shape;
        try {
            SimpleFactory factory = new SimpleFactory();
            shape = factory.GetShape(ShapeType.CIRCLE);
            shape.sayHello();
            shape = factory.GetShape(ShapeType.TRIANGLE);
            shape.sayHello();
            shape = factory.GetShape(ShapeType.RECTANGLE);
            shape.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```  
> 优点：               
> 使用者只需要给工厂类传入一个正确的约定好的参数，就可以获取你所需要的对象，而不需要知道其创建细节，一定程度上减少系统的耦合。            
  客户端无须知道所创建的具体产品类的类名，只需要知道具体产品类所对应的参数即可，减少开发者的记忆成本。            
  缺点：               
> 如果业务上添加新产品的话，就需要修改工厂类原有的判断逻辑，这其实是违背了开闭原则的。                
  在产品类型较多时，有可能造成工厂逻辑过于复杂。所以简单工厂模式比较适合产品种类比较少而且增多的概率很低的情况。                                                         
## 二.工厂方法模式
> 工厂方法和简单工厂有一些区别，简单工厂是由一个代工厂生产不同的产品，而工厂方法是对工厂进行抽象化，不同产品都由专门的具体工厂来生产。            
> ![factory_method](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/factory_method.png)           
抽象产品类代码：
```java
package com.java.topic.design_pattern;

public interface Shape {
    public void sayHello();
}
```
> 具体产品类代码：          
```java
package com.java.topic.design_pattern;

public class Circle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am circle");
    }
}
``` 
                
```java
package com.java.topic.design_pattern;

public class Triangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am triangle");
    }
}
```
                    
```java
package com.java.topic.design_pattern;

public class Rectangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am rectangle");
    }
}
```
> 抽象工厂代码： 
```java
package com.java.topic.design_pattern;

public interface ShapeFactory {
    public Shape create();
}
```
> 生产产品的工厂代码：        
```java
package com.java.topic.design_pattern;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Circle();
    }
}
```

```java
package com.java.topic.design_pattern;

public class TriangleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Triangle();
    }
}
```

```java
package com.java.topic.design_pattern;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Rectangle();
    }
}
```
> 工厂方法客户端代码:            
```java
package com.java.topic.design_pattern;

public class FactoryMethodClient {
    public static void main(String[] args) {
        ShapeFactory circle = new CircleFactory();
        circle.create().sayHello();

        ShapeFactory triangle = new TriangleFactory();
        triangle.create().sayHello();

        ShapeFactory rectangle = new RectangleFactory();
        rectangle.create().sayHello();
    }
}
```
> 优点：                   
  用户只需要关心其所需产品对应的具体工厂是哪一个即可，不需要关心产品的创建细节，也不需要知道具体产品类的类名。                
  当系统中加入新产品时，不需要修改抽象工厂和抽象产品提供的接口，也无须修改客户端和其他的具体工厂和具体产品，而只要添加一个具体工厂和与其对应的具体产品就可以了，符合了开闭原则。               
> 缺点：                   
  当系统中加入新产品时，除了需要提供新的产品类之外，还要提供与其对应的具体工厂类。因此系统中类的个数将成对增加，增加了系统的复杂度。                 
## 三.抽象工厂模式
> 抽象工厂模式较好的实现了“开放-封闭”原则，是三个模式中较为抽象，并具一般性的模式。                                
> 抽象工厂模式(Abstract Factory Pattern)提供一个创建一系列相关或相互依赖对象的接口，而无须指定它们具体的类。            
> ![abstract_factory](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/abstract_factory.png)           
> 抽象产品类Shape代码：                        
```java
package com.java.topic.design_pattern;

public interface Shape {
    public void sayHello();
}
```
> 具体产品类代码：          
```java
package com.java.topic.design_pattern;

public class Circle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am circle");
    }
}
``` 
                
```java
package com.java.topic.design_pattern;

public class Triangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am triangle");
    }
}
```
                    
```java
package com.java.topic.design_pattern;

public class Rectangle implements Shape {
    @Override
    public void sayHello() {
        System.out.println("I am rectangle");
    }
}
```  
> 抽象产品类Color代码：         
```java
package com.java.topic.design_pattern;

public interface Color {
    public void display();
}
```  
> 具体产品类代码：          
```java
package com.java.topic.design_pattern;

public class Red implements Color {
    @Override
    public void display() {
        System.out.println("i am red");
    }
}
```   
            
```java
package com.java.topic.design_pattern;

public class White implements Color {
    @Override
    public void display() {
        System.out.println("i am white");
    }
}
``` 
> 抽象工厂代码：       
```java
package com.java.topic.design_pattern;

public interface ShapeColorFactory {
    public Shape createShape();

    public Color createColor();
}
```   
> 具体工厂代码：           
```java
package com.java.topic.design_pattern;

public class RedCircleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Circle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
```

```java
package com.java.topic.design_pattern;

public class WhiteCircleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Circle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
```

```java
package com.java.topic.design_pattern;

public class RedTriangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Triangle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
```

```java
package com.java.topic.design_pattern;

public class WhiteTriangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Triangle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
```

```java
package com.java.topic.design_pattern;

public class RedRetangleFactory implements ShapeColorFactory{
    @Override
    public Shape createShape() {
        return new Rectangle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
```

```java
package com.java.topic.design_pattern;

public class WhiteRetangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Rectangle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
```
> 抽象工厂客户端：      
```java
package com.java.topic.design_pattern;

public class AbstractFactoryClient {
    public static void main(String[] args) {
        ShapeColorFactory redCircleFactory = new RedCircleFactory();
        redCircleFactory.createShape().sayHello();
        redCircleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory whiteCircleFactory = new WhiteCircleFactory();
        whiteCircleFactory.createShape().sayHello();
        whiteCircleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory redTriangleFactory = new RedTriangleFactory();
        redTriangleFactory.createShape().sayHello();
        redTriangleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory whiteTriangleFactory = new WhiteTriangleFactory();
        whiteTriangleFactory.createShape().sayHello();
        whiteTriangleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory redRetangleFactory = new RedRetangleFactory();
        redRetangleFactory.createShape().sayHello();
        redRetangleFactory.createColor().display();

        System.out.println("--------------");
        ShapeColorFactory whiteRetangleFactory = new WhiteRetangleFactory();
        whiteRetangleFactory.createShape().sayHello();
        whiteRetangleFactory.createColor().display();
    }
}
```
> 优点：               
  具体产品在应用层代码隔离，不需要关心产品细节。只需要知道自己需要的产品是属于哪个工厂的即可,当一个产品族中的多个对象被设计成一起工作时，
> 它能够保证客户端始终只使用同一个产品族中的对象。这对一些需要根据当前环境来决定其行为的软件系统来说，是一种非常实用的设计模式。               
  缺点：                   
  规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口。                 
## 四.单例模式
> 所有类都有构造方法，不编码则系统默认生成空的构造方法，若有显示定义的构造方法，默认的构造方法就会失效。                              
  单例模式，保证一个类仅有一个实例，并提供一个访问它的全局访问点。                                
  通常可以让一个全局变量使得一个对象被访问，但它不能防止你实例化多个对象。一个最好的办法就是让类自身负责保存它的唯一实例。
> 这个类可以保证没有其他实例可以被创建，并且它可以提供一个访问该实例的方法。 
> ![singleton](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/singleton.png)         
> 懒汉式Singleton类，定义一个getInstance方法，允许客户访问它的唯一实例。         
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
> 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生就是线程安全的。            
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
## 五.策略模式