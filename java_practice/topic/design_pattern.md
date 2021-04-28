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
> 优点：                   
  用户只需要关心其所需产品对应的具体工厂是哪一个即可，不需要关心产品的创建细节，也不需要知道具体产品类的类名。                
  当系统中加入新产品时，不需要修改抽象工厂和抽象产品提供的接口，也无须修改客户端和其他的具体工厂和具体产品，而只要添加一个具体工厂和与其对应的具体产品就可以了，符合了开闭原则。               
> 缺点：                   
  当系统中加入新产品时，除了需要提供新的产品类之外，还要提供与其对应的具体工厂类。因此系统中类的个数将成对增加，增加了系统的复杂度。                 
## 三.抽象工厂模式
> 
## 四.单例模式
## 五.策略模式