package com.java.topic.design_pattern;

public class SimpleFactoryClient {
    public static void main(String[] args) {
        Shape shape;
        try {
            SimpleFactory factory = new SimpleFactory();
            shape = factory.GetShape(ShapeType.CIRCLE);
            shape.sayHello();
            shape = factory.GetShape(ShapeType.TRIANGLE);
            shape.sayHello();
            shape = factory.GetShape(ShapeType.RECTANGLE);
            shape.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
