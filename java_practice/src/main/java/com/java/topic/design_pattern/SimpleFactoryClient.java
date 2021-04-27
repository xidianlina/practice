package com.java.topic.design_pattern;

public class SimpleFactoryClient {
    public static void main(String[] args) {
        Product product;
        try {
            SimpleFactory factory = new SimpleFactory();
            product = factory.GetProduct(ProductType.A);
            product.ICar();
            product = factory.GetProduct(ProductType.B);
            product.ICar();
            product = factory.GetProduct(ProductType.C);
            product.ICar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
