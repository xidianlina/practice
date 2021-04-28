package com.java.topic.design_pattern;

public class RedRetangleFactory implements ShapeColorFactory{
    @Override
    public Shape createShape() {
        return new Rectangle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
