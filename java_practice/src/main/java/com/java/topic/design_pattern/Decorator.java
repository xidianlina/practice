package com.java.topic.design_pattern;

// 抽象装饰者，定义具体装饰者要实现的代码
public interface Decorator extends AbstractHouse {
    public String getCost();
}
