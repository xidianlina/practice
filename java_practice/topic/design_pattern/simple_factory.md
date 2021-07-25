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
