package com.java.topic.design_pattern;

// 定义具体的被装饰对象简约房子
public class JianYiHouse implements AbstractHouse {

    @Override
    public String getCategory() {
        return "简易风格的房子";
    }
}
