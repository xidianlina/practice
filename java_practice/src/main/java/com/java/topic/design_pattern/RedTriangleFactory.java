package com.java.topic.design_pattern;

public class RedTriangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Triangle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
