package com.java.topic;

public class ClassLoaderOrder extends Father {
    public static void main(String[] args) {
        ClassLoaderOrder c = new ClassLoaderOrder();
    }

    static String c = "child";

    static {
        System.out.println(c);
        System.out.println("child static 1");
        c = "body";
        System.out.println(c);
    }

    static {
        System.out.println("child static 2");
        System.out.println(c);
    }

    private int val = 100;

    {
        System.out.println("child val " + val);
        System.out.println("child block");
    }

    public ClassLoaderOrder() {
        System.out.println("child construct");
    }
}

class Father {
    static int f = 2;

    static {
        System.out.println(f);
        System.out.println("father static 1");
        f = 9;
        System.out.println(f);
    }

    static {
        System.out.println("father static 2");
        System.out.println(f);
    }

    private String val = "hello";

    {
        System.out.println("father val " + val);
        System.out.println("father block");
    }

    public Father() {
        System.out.println("father construct");
    }
}