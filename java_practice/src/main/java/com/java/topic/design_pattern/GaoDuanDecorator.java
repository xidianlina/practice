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
