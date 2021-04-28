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
