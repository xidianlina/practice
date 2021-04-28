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
