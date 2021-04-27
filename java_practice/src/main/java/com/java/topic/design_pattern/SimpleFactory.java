package com.java.topic.design_pattern;

public class SimpleFactory {
    public Product GetProduct(ProductType productType) throws Exception {
        switch (productType) {
            case A:
                return new ProductA();
            case B:
                return new ProductB();
            case C:
                return new ProductC();
            default:
                throw new Exception("ProductType Exception");
        }
    }
}
