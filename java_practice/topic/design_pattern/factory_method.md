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
