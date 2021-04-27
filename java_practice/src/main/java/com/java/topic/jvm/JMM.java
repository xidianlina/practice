package com.java.topic.jvm;

public class JMM {
    public static void main(String[] args) {
        int i = 2;  // 基本类型赋值操作，能保证原子性。
        int j = i;  // 先读取i的值，再赋值到j，两步操作，不能保证原子性。
        ++i;        // 先读取i的值，再+1，最后赋值到i，三步操作，不能保证原子性。
        i = i + 1;  // 先读取i的值，再+1，最后赋值到i，三步操作，不能保证原子性。
    }
}
