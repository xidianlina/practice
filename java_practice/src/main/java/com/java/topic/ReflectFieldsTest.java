package com.java.topic;

import java.lang.reflect.Field;
import java.nio.channels.FileChannel;

/*
 * 获取成员变量并调用：
 *
 * 1.批量的
 * 		1).Field[] getFields():获取所有的"公有字段"
 * 		2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
 * 2.获取单个的：
 * 		1).public Field getField(String fieldName):获取某个"公有的"字段；
 * 		2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
 *
 * 	 设置字段的值：
 * 		Field --> public void set(Object obj,Object value):
 * 					参数说明：
 * 					1.obj:要设置的字段所在的对象；
 * 					2.value:要为字段设置的值；
 *
 */
public class ReflectFieldsTest {
    public static void main(String[] args) throws Exception {
        //1.获取Class对象
        Class personClass = Class.forName("com.java.topic.Person");

        //2.获取字段
        System.out.println("************获取所有公有的字段********************");
        Field[] filedArray = personClass.getFields();
        for (Field f : filedArray) {
            System.out.println(f);
        }

        System.out.println("\n************获取所有的字段(包括私有、受保护、默认的)********************");
        filedArray = personClass.getDeclaredFields();
        for (Field f : filedArray) {
            System.out.println(f);
        }

        System.out.println("\n*************获取公有字段**并调用***********************************");
        Field f = personClass.getField("name");
        System.out.println(f);

        //获取一个对象
        Object obj = personClass.getConstructor().newInstance();//产生Person对象 --> Person person=new Person();
        //为字段设置值
        f.set(obj, "大话");
        //验证
        Person person = (Person) obj;
        System.out.println("name = " + person.name);

        System.out.println("\n**************获取私有字段****并调用********************************");
        f = personClass.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);
        f.set(obj, "3456789");
        System.out.println("phone: " + person);
    }
}

























