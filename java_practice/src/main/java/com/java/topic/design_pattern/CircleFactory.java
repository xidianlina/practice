package com.java.topic.design_pattern;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Circle();
    }
}
