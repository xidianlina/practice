package com.java.topic.design_pattern;

//定义具体的被装饰对象欧美房子
public class OuMeiHouse implements AbstractHouse {
    @Override
    public String getCategory() {
        return "欧美风格的房子";
    }
}
