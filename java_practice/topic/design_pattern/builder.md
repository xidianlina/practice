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
