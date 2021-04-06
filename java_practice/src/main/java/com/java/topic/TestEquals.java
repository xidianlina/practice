package com.java.topic;

public class TestEquals {
    public static void main(String[] args) {
        String x = "string";
        String y = "string";
        String z = new String("string");
        System.out.println(x == y);        //true
        System.out.println(x == z);       //false
        System.out.println(x.equals(y));  //true
        System.out.println(x.equals(z));  //true

        Cat c1 = new Cat("cat");
        Cat c2 = new Cat("cat");
        System.out.println(c1.equals(c2)); // false

        String s1 = new String("test");
        String s2 = new String("test");
        System.out.println(s1.equals(s2)); //true
    }
}

class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}