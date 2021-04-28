package com.java.topic.design_pattern;

public class WhiteTriangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Triangle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
