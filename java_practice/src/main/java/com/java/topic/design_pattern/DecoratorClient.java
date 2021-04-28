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
