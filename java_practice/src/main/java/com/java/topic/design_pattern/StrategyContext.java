package com.java.topic.design_pattern;

//持有具体策略类的实例，负责调用具体算法
public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    // 动态替换算法(策略)
    public void replaceStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(int a, int b) {
        return this.strategy.calculate(a, b);
    }
}
