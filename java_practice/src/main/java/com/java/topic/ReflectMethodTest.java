package com.java.topic;

import java.lang.reflect.Method;

public class ReflectMethodTest {
    public static void main(String[] args) throws Exception {
        Class clazz = null;
        clazz = Class.forName("com.java.topic.Student");

        Method[] methodArray = clazz.getMethods();
        for (Method m : methodArray) {
            System.out.println(m);
        }

        methodArray = clazz.getDeclaredMethods();
        for (Method m : methodArray) {
            System.out.println(m);
        }

        Method m = clazz.getMethod("show1", String.class);
        System.out.println(m);

        Object obj = clazz.getConstructor().newInstance();
        m.invoke(obj, "Adfafd");

        m = clazz.getDeclaredMethod("show4", int.class);
        System.out.println(m);

        m.setAccessible(true);

        Object res = m.invoke(obj, 20);
        System.out.println(res);
    }
}
