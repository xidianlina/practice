package com.java.topic;

public class AbstractTest {
    private String name;

    public AbstractTest(String name) {
        this.name = name;
        System.out.println(this.name);
    }

    public static void main(String[] args) {
        System.out.println("hello");
        AbstractTest ab = new AbstractTest("stes");
    }
}
