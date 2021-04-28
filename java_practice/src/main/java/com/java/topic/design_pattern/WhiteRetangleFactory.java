package com.java.topic.design_pattern;

public class WhiteRetangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Rectangle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
