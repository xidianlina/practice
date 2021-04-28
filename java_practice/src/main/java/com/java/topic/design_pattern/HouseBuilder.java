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
