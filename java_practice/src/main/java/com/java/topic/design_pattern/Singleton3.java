package com.java.topic.design_pattern;

public class Singleton3 {
    // 构造方法私有化，外界不能用new关键字创建此实例
    private Singleton3() {
    }

    private static Singleton3 instance;

    // 该方法是获取本类实例的唯一途径
    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }

        return instance;
    }
}
