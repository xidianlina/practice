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
