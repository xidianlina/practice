package com.java.topic.design_pattern;

public class SimpleFactory {
    public Shape GetShape(ShapeType shapeType) throws Exception {
        switch (shapeType) {
            case CIRCLE:
                return new Circle();
            case TRIANGLE:
                return new Triangle();
            case RECTANGLE:
                return new Rectangle();
            default:
                throw new Exception("ShapeType Exception");
        }
    }
}
