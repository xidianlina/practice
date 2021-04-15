package com.java.topic;

import java.io.*;

public class ObjectSerializeAndDeserializeTest {
    public static void main(String[] args) throws Exception {
        ObjectSerializeAndDeserializeTest objectTest = new ObjectSerializeAndDeserializeTest();
        objectTest.SerializeObject();
        PersonSeriablizable person = objectTest.DeserializeObject();
        System.out.println(person);
    }

    public void SerializeObject() throws Exception {
        PersonSeriablizable person = new PersonSeriablizable();
        person.setName("nancy");
        person.setAge(30);
        person.setSex("男");
        // ObjectOutputStream对象输出流，将PersonSeriablizable对象存储到磁盘Person.txt文件中，完成对Person对象的序列化操作
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("/Users/lina/practice/java_practice/person.txt")));
        objectOutputStream.writeObject(person);
        System.out.println("对象序列化成功");
        objectOutputStream.close();
    }

    public PersonSeriablizable DeserializeObject() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("/Users/lina/practice/java_practice/person.txt")));
        PersonSeriablizable person = (PersonSeriablizable) objectInputStream.readObject();
        System.out.println("对象反序列化成功");
        return person;
    }
}
