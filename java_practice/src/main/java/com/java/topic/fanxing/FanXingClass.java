package com.java.topic.fanxing;

/*
    此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
    在实例化泛型类时，必须指定T的具体类型
 */
public class FanXingClass<T> {
    private T key;//key这个成员变量的类型为T,T的类型由外部指定

    public FanXingClass(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
    }

    public T getKey() { //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return key;
    }

    public static void main(String[] args) {
        FanXingClass<Integer> fanXingClass = new FanXingClass<>(123);
        System.out.println(fanXingClass.getKey());
    }
}
