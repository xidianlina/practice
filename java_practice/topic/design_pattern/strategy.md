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
