package com.java.topic.design_pattern;

//指挥者
public class HouseDirector {

    public void buildHouse(HouseBuilder houseBuilder) {
        houseBuilder.doFoundation();
        houseBuilder.doFrame();
        houseBuilder.dpPouring();
    }
}
