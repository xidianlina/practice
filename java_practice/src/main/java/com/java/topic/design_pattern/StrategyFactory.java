package com.java.topic.design_pattern;

//策略工厂
public class StrategyFactory {
    public static Strategy GetStrategy(String type) {
        switch (type) {
            case "add":
                return new AddStrategy();
            case "sub":
                return new SubtractStrategy();
            case "mul":
                return new MultiplyStrategy();
            default:
                return new AddStrategy();
        }
    }
}
