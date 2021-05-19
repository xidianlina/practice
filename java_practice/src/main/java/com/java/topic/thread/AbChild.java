package com.java.topic.thread;

public class AbChild extends AbTest {
    public static void main(String[] args) {
        AbChild abChild = new AbChild();
        abChild.sayHello();
    }

    @Override
    void sayHello() {
        System.out.println("hello");
    }
}
