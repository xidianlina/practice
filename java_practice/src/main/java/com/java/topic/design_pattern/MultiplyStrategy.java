package com.java.topic.design_pattern;

////具体策略类，实现策略接口，提供具体算法,做乘法
public class MultiplyStrategy implements Strategy {
    @Override
    public int calculate(int a, int b) {
        return a * b;
    }
}
