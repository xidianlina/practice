package com.java.topic;

public class TestFinally {
    static int mayThrowException() {
        int i = 100;
        try {
            return i;
        } finally {
            ++i;
            return i;
        }
    }

    public static void main(String[] args) {
        System.out.println(TestFinally.mayThrowException());
        int a1 = 0;
        try {
            a1 = 1;
        }catch (Exception e){
            a1 = 2;
        }finally {
            a1 = 3;
        }

        System.out.println(a1);
    }
}
