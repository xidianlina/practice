package com.java.topic.design_pattern;

public class WhiteCircleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Circle();
    }

    @Override
    public Color createColor() {
        return new White();
    }
}
