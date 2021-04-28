package com.java.topic.design_pattern;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape create() {
        return new Rectangle();
    }
}
