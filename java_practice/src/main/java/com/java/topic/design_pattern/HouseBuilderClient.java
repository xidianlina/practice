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
