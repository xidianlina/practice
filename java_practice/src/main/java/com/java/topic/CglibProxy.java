package com.java.topic;

public class CglibProxy {
    public static void main(String[] args) {
        //目标对象
        PersonDao target = new PersonDao();
        System.out.println(target.getClass());
        //代理对象
        PersonDao proxy = (PersonDao) new ProxyPersonFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        //执行代理对象方法
        proxy.save();
    }
}
/*
 * class com.java.topic.PersonDao
 * class com.java.topic.PersonDao$$EnhancerByCGLIB$$8ee64bd9
 * 开启事务
 * 保存数据
 * 关闭事务
 */