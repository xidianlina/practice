package com.java.topic.design_pattern;

public class AbstractFactoryClient {
    public static void main(String[] args) {
        ShapeColorFactory redCircleFactory = new RedCircleFactory();
        redCircleFactory.createShape().sayHello();
        redCircleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory whiteCircleFactory = new WhiteCircleFactory();
        whiteCircleFactory.createShape().sayHello();
        whiteCircleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory redTriangleFactory = new RedTriangleFactory();
        redTriangleFactory.createShape().sayHello();
        redTriangleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory whiteTriangleFactory = new WhiteTriangleFactory();
        whiteTriangleFactory.createShape().sayHello();
        whiteTriangleFactory.createColor().display();
        System.out.println("--------------");

        ShapeColorFactory redRetangleFactory = new RedRetangleFactory();
        redRetangleFactory.createShape().sayHello();
        redRetangleFactory.createColor().display();

        System.out.println("--------------");
        ShapeColorFactory whiteRetangleFactory = new WhiteRetangleFactory();
        whiteRetangleFactory.createShape().sayHello();
        whiteRetangleFactory.createColor().display();
    }
}
