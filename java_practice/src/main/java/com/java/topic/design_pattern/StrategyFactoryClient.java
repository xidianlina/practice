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
