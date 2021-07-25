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
