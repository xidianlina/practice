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
# 创建型                                                              
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
## 五.生成器(建造者)模式
> 生成器模式(Builder Pattern)：也叫创建者模式，它将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。                     
> 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，用户不需要知道内部的具体构建细节。建造者模式属于对象创建型模式。   
> ![builder](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/builder.png)                                   
> 建造者模式的结构              
> ![builder2](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/builder2.png)                                         
> 要建造的产品：房子         
```java
package com.java.topic.design_pattern;

//产品对象:房子
public class House {
    // 打地基
    private String foundation;

    // 盖框架
    private String frame;

    // 浇筑
    private String pouring;

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getPouring() {
        return pouring;
    }

    public void setPouring(String pouring) {
        this.pouring = pouring;
    }

    @Override
    public String toString() {
        return "House{" +
                "foundation='" + foundation + '\'' +
                ", frame='" + frame + '\'' +
                ", pouring='" + pouring + '\'' +
                '}';
    }
}
```  
> 抽象建造者         
```java
package com.java.topic.design_pattern;

//抽象建造者
public interface HouseBuilder {
    // 打地基
    void doFoundation();

    // 盖框架
    void doFrame();

    // 浇灌
    void dpPouring();

    // 房子建成
    House getHouse();
}
```    
> 具体建造者：建造一个平房      
```java
package com.java.topic.design_pattern;

//具体建造者:盖平房
public class PingFangBuilder implements HouseBuilder {
    private House house = new House();

    @Override
    public void doFoundation() {
        house.setFoundation("盖平房的地基");
    }

    @Override
    public void doFrame() {
        house.setFrame("盖平房的框架");
    }

    @Override
    public void dpPouring() {
        house.setPouring("盖平房不用浇灌，直接人工手刷就可以");
    }

    @Override
    public House getHouse() {
        return house;
    }
}
```    
> 具体建造者：建造一个楼房          
```java
package com.java.topic.design_pattern;

//具体建造者:盖楼房
public class LouFangBuilder implements HouseBuilder {
    private House house = new House();

    @Override
    public void doFoundation() {
        house.setFoundation("盖楼房的地基就打十米深");
    }

    @Override
    public void doFrame() {
        house.setFrame("楼房的框架要使用非常坚固钢筋混凝土");
    }

    @Override
    public void dpPouring() {
        house.setPouring("楼房拿个罐车把框架拿混凝土灌满即可");
    }

    @Override
    public House getHouse() {
        return house;
    }
}
```   
> 指挥者：指挥要建造一个什么样式的房子
```java
package com.java.topic.design_pattern;

//指挥者
public class HouseDirector {

    public void buildHouse(HouseBuilder houseBuilder) {
        houseBuilder.doFoundation();
        houseBuilder.doFrame();
        houseBuilder.dpPouring();
    }
}
```    
> 建造房子的测试类
```java
package com.java.topic.design_pattern;

//测试类
public class HouseBuilderClient {
    public static void main(String[] args) {
        // 方式1 客户自己盖房子，亲力亲为
        System.out.println("========客户自己建房子，必须知道盖房的细节========");
        House house = new House();
        house.setFoundation("用户自己建造房子：打地基");
        house.setFrame("用户自己建造房子：盖框架");
        house.setPouring("用户自己建造房子：浇筑");
        System.out.println(house);

        // 方式2 客户找一个建造者盖房子(充当包工头角色)，但是要知道如何盖房子(调用建造者盖房子的顺序)
        System.out.println("========客户直接找盖房子的工人(建造者)，客户要调用建造者方法去盖房子，客户必须得知道房子如何造========");
        HouseBuilder houseBuilder = new PingFangBuilder();
        houseBuilder.doFoundation();
        houseBuilder.doFrame();
        houseBuilder.dpPouring();
        House house2 = houseBuilder.getHouse();
        System.out.println(house2);

        // 方式三、使用建造者模式，找一个设计师(设计师拉一帮建造者去干活)，告诉他我想要什么样的房子，最后客户只问设计师要房子即可
        System.out.println("========客户直接找一个设计师，设计师统一指挥建造者盖房子，房子杂盖，客户不关心，最后只是找设计师要房子即可========");
        HouseBuilder pingFangBuilder = new PingFangBuilder();
        HouseDirector pingFangDirector = new HouseDirector();
        pingFangDirector.buildHouse(pingFangBuilder);
        House house3 = pingFangBuilder.getHouse();
        System.out.println(house3);

        HouseBuilder louFangBuilder = new LouFangBuilder();
        HouseDirector louFangDirector = new HouseDirector();
        louFangDirector.buildHouse(louFangBuilder);
        House house4 = louFangBuilder.getHouse();
        System.out.println(house4);
    }
}
```  
> 优点：               
  (1).客户端不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。            
  (2).每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象 。                
  (3).增加新的具体建造者无须修改原有类库的代码，指挥者类针对抽象建造者类编程，系统扩展方便，符合“开闭原则”。              
  (4).可以更加精细地控制产品的创建过程 。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程。                 
  缺点：               
  (1).建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。                
  (2).如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。               
> 建造者模式的使用场景                                    
  (1).如果一个对象有非常复杂的内部结构(这些产品通常有很多属性)，那么使用建造者模式                                 
  (2).如果想把复杂对象的创建和使用分离开来，那么使用建造者模式(使用相同的创建步骤可以创建不同的产品)              
  建造者模式简单工厂模式的比较                
  相似点:                  
  它们都属于创建型模式(都是创建产品的)               
  区别:               
  (1).创建对象的粒度不同                 
  工厂模式创建的对象都是一个样子，而建造者模式创建的是一个复合产品，由各个复杂的部件组成，部件不同所构成的产品也不同。                             
  (2).关注点不同             
  工厂模式注重只要把这个对象创建出来就可以了，不关心这个产品的组成部分；而建造者模式不仅要创造出这个产品，还要知道这个产品的组成部分。   
>                   
> 参考 https://juejin.cn/post/6844903518449434638             
## 六.原型模式
# 结构型
## 五.装饰者模式
> 装饰模式(Decorator Pattern)不改变原有对象的前提下，动态地给一个对象增加一些额外的功能。         
> ![decorator](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/decorator.png)         
> 抽象的被装饰的对象,装饰类和被装饰类都需要继承           
```java
package com.java.topic.design_pattern;

// 定义抽象的被装饰的对象
public interface AbstractHouse {
    // 取得房子的风格
    public String getCategory();
}
```               
> 具体的被装饰对象          
```java
package com.java.topic.design_pattern;

// 定义具体的被装饰对象简约房子
public class JianYiHouse implements AbstractHouse {

    @Override
    public String getCategory() {
        return "简易风格的房子";
    }
}
``` 
> 具体的被装饰对象          
```java
package com.java.topic.design_pattern;

//定义具体的被装饰对象欧美房子
public class OuMeiHouse implements AbstractHouse {
    @Override
    public String getCategory() {
        return "欧美风格的房子";
    }
}
```
> 抽象装饰者,需要继承抽象的被装饰的对象,定义具体装饰者要实现的方法                    
```java
package com.java.topic.design_pattern;

// 抽象装饰者，定义具体装饰者要实现的代码
public interface Decorator extends AbstractHouse {
    public String getCost();
}
```
> 具体装饰者,实现抽象的被装饰者和抽象装饰者的方法
```java
package com.java.topic.design_pattern;

//定义简约风格房子具体装饰者
public class DiDiaoDecorator implements Decorator {
    private AbstractHouse abstractHouse;

    public DiDiaoDecorator(AbstractHouse abstractHouse) {
        this.abstractHouse = abstractHouse;
    }

    @Override
    public String getCost() {
        return " 10万元装修";
    }

    @Override
    public String getCategory() {
        return this.abstractHouse.getCategory() + " 低调奢华有内涵";
    }
}
```
                
```java
package com.java.topic.design_pattern;

//定义欧美风格房子具体装饰者
public class GaoDuanDecorator implements Decorator {
    private AbstractHouse abstractHouse;

    public GaoDuanDecorator(AbstractHouse abstractHouse) {
        this.abstractHouse = abstractHouse;
    }

    @Override
    public String getCost() {
        return " 50万元装修";
    }

    @Override
    public String getCategory() {
        return this.abstractHouse.getCategory() + " 高端大气上档次";
    }
}
```
> 测试装饰者模式的客户端
```java
package com.java.topic.design_pattern;

public class DecoratorClient {
    public static void main(String[] args) {
        AbstractHouse jianYiHouse = new JianYiHouse();
        Decorator didiaoDecorator = new DiDiaoDecorator(jianYiHouse);
        System.out.println(didiaoDecorator.getCategory() + didiaoDecorator.getCost());

        AbstractHouse ouMeiHouse = new OuMeiHouse();
        Decorator ouMeiDecorator = new GaoDuanDecorator(ouMeiHouse);
        System.out.println(ouMeiDecorator.getCategory() + ouMeiDecorator.getCost());
    }
}
```
> 优点：               
  (1).装饰模式与继承关系的目的都是要扩展对象的功能，但是装饰模式可以提供比继承更多的灵活性。
> 不同于在编译期起作用的继承；装饰者模式可以在运行时扩展一个对象的功能。另外也可以通过配置文件在运行时选择不同的装饰器，
> 从而实现不同的行为。也可以通过不同的组合，可以实现不同效果。                               
  (2).符合“开闭原则”：装饰者和被装饰者可以独立变化。用户可以根据需要增加新的装饰类，在使用时再对其进行组合，原有代码无须改变。             
> (3).通过使用不同的具体装饰类以及这些装饰类的排列组合，设计师可以创造出很多不同行为的组合。               
  缺点：                   
> 装饰者模式需要创建一些具体装饰类，会增加系统的复杂度。               
# 行为型
## 九.策略模式
> 策略模式(Strategy Pattern)定义一系列算法类，将每一个算法封装起来，并让它们可以相互替换，策略模式让算法独立于使用它的客户而变化，也称为政策模式(Policy)。策略模式是一种对象行为型模式。              
> ![strategy](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/strategy.png)               
> Strategy: 策略接口或者策略抽象类，并且策略执行的接口               
  ConcreateStrategy：实现策略接口的具体策略类                  
  Context：上下文类，持有具体策略类的实例，并负责调用相关的算法
```java
package com.java.topic.design_pattern;

//策略接口，定义策略执行接口
public interface Strategy {
    public int calculate(int a, int b);
}
```                                                  
                
```java
package com.java.topic.design_pattern;

//具体策略类，实现策略接口，提供具体算法,做加法
public class AddStrategy implements Strategy {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
```
                  
```java
package com.java.topic.design_pattern;

////具体策略类，实现策略接口，提供具体算法,做减法
public class SubtractStrategy implements Strategy {
    @Override
    public int calculate(int a, int b) {
        return a - b;
    }
}

```
                
```java
package com.java.topic.design_pattern;

////具体策略类，实现策略接口，提供具体算法,做乘法
public class MultiplyStrategy implements Strategy {
    @Override
    public int calculate(int a, int b) {
        return a * b;
    }
}
```
                
```java
package com.java.topic.design_pattern;

//持有具体策略类的实例，负责调用具体算法
public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    // 动态替换算法(策略)
    public void replaceStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(int a, int b) {
        return this.strategy.calculate(a, b);
    }
}
```
                
```java
package com.java.topic.design_pattern;

//策略模式测试类
public class StrategyPatternClient {
    public static void main(String[] args) {
        Strategy addStrategy = new AddStrategy();
        StrategyContext addContext = new StrategyContext(addStrategy);
        System.out.println(addContext.calculate(2, 3));

        Strategy subStrategy = new SubtractStrategy();
        StrategyContext subContext = new StrategyContext(subStrategy);
        System.out.println(subContext.calculate(2, 3));

        Strategy multiplyStrategy = new MultiplyStrategy();
        StrategyContext multiplyContext = new StrategyContext(multiplyStrategy);
        System.out.println(multiplyContext.calculate(2, 3));
    }
}
```
> 所有的策略类完全暴露给了客户端，这是策略模式的缺点。                
> 往往策略模式不会单独使用，会和其他设计模式一起使用，比如和简单工厂模式一起使用就可以解决这个对外暴露对象的问题。              
```java
package com.java.topic.design_pattern;

//策略工厂
public class StrategyFactory {
    public static Strategy GetStrategy(String type) {
        switch (type) {
            case "add":
                return new AddStrategy();
            case "sub":
                return new SubtractStrategy();
            case "mul":
                return new MultiplyStrategy();
            default:
                return new AddStrategy();
        }
    }
}
```
                    
```java
package com.java.topic.design_pattern;

public class StrategyFactoryClient {
    public static void main(String[] args) {
        Strategy factory = StrategyFactory.GetStrategy("add");
        System.out.println(factory.calculate(5, 2));

        Strategy factory2 = StrategyFactory.GetStrategy("sub");
        System.out.println(factory2.calculate(5, 2));

        Strategy factory3 = StrategyFactory.GetStrategy("mul");
        System.out.println(factory3.calculate(5, 2));
    }
}
```
> 优点：           
  (1).策略模式提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法或行为，也可以灵活地增加新的算法或行为。              
  (2).策略模式提供了管理相关的算法族的办法。策略类的等级结构定义了一个算法或行为族，恰当使用继承可以把公共的代码移到抽象策略类中，从而避免重复的代码。                  
  (3).策略模式提供了一种可以替换继承关系的办法。如果不使用策略模式，那么使用算法的环境类就可能会有一些子类，每一个子类提供一种不同的算法。
> 但是，这样一来算法的使用就和算法本身混在一起，不符合“单一职责原则”，决定使用哪一种算法的逻辑和该算法本身混合在一起，从而不可能再独立演化；
> 而且使用继承无法实现算法或行为在程序运行时的动态切换。           
  (4).使用策略模式可以避免多重条件选择语句。多重条件选择语句不易维护，它把采取哪一种算法或行为的逻辑与算法或行为本身的实现逻辑混合在一起，
> 将它们全部硬编码(Hard Coding)在一个庞大的多重条件选择语句中，比直接继承环境类的办法还要原始和落后。          
  (5).策略模式提供了一种算法的复用机制，由于将算法单独提取出来封装在策略类中，因此不同的环境类可以方便地复用这些策略类。             
  缺点：                   
  (1).客户端必须知道所有的策略类，并自行决定使用哪一个策略类。这就意味着客户端必须理解这些算法的区别，以便适时选择恰当的算法。换言之，策略模式只适用于客户端知道所有的算法或行为的情况。             
  (2).策略模式将造成系统产生很多具体策略类，任何细小的变化都将导致系统要增加一个新的具体策略类。                 
  (3).无法同时在客户端使用多个策略类，也就是说，在使用策略模式时，客户端每次只能使用一个策略类，不支持使用一个策略类完成部分功能后再使用另一个策略类来完成剩余功能的情况。                    