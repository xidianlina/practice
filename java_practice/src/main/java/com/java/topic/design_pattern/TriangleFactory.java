package com.java.topic.design_pattern;

public class TriangleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Triangle();
    }
}
