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
