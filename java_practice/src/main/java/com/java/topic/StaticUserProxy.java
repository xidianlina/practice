package com.java.topic;

public class StaticUserProxy {
    public static void main(String[] args) {
        //目标对象
        IUserDao target = new UserDao();
        //代理对象
        UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();
    }
}
/*
 * 输出结果:
 * 开启事务
 * 保存数据
 * 提交事务
 */