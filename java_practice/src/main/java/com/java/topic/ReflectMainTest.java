package com.java.topic;

import java.lang.reflect.Method;

public class ReflectMainTest {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.java.topic.Student");

        Method methodMain = clazz.getMethod("main", String[].class);

        methodMain.invoke(null, (Object) new String[]{"a", "b", "c"});
    }
}
