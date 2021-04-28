package com.java.topic.design_pattern;

public class Singleton4 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton4() {
    }

    private static class LazyHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    // 该方法是获取本类实例的唯一途径
    public static final Singleton4 getInstance() {
        return LazyHolder.INSTANCE;
    }
}
