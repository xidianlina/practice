package com.java.topic.design_pattern;

public class Singleton5 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton5() {
    }

    private static final Singleton5 instance = new Singleton5();

    // 该方法是获取本类实例的唯一途径
    public static Singleton5 getInstance() {
        return instance;
    }
}
