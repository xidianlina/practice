package com.java.topic.design_pattern;

public class Singleton2 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton2() {
    }

    private static Singleton2 instance;

    // 该方法是获取本类实例的唯一途径
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }

        return instance;
    }
}
