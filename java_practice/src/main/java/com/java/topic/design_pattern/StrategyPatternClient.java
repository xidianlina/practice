package com.java.topic.design_pattern;

//策略模式测试类
public class StrategyPatternClient {
    public static void main(String[] args) {
        Strategy addStrategy = new AddStrategy();
        StrategyContext addContext = new StrategyContext(addStrategy);
        System.out.println(addContext.calculate(2, 3));

        Strategy subStrategy = new SubtractStrategy();
        StrategyContext subContext = new StrategyContext(subStrategy);
        System.out.println(subContext.calculate(2, 3));

        Strategy multiplyStrategy = new MultiplyStrategy();
        StrategyContext multiplyContext = new StrategyContext(multiplyStrategy);
        System.out.println(multiplyContext.calculate(2, 3));
    }
}
