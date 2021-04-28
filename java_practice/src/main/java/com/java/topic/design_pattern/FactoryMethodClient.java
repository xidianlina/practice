package com.java.topic.design_pattern;

public class FactoryMethodClient {
    public static void main(String[] args) {
        ShapeFactory circle = new CircleFactory();
        circle.create().sayHello();

        ShapeFactory triangle = new TriangleFactory();
        triangle.create().sayHello();

        ShapeFactory rectangle = new RectangleFactory();
        rectangle.create().sayHello();
    }
}
