package com.java.topic;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerTest {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("22222");
        BigInteger b = new BigInteger("55555");
        String add = a.add(b).toString();
        String subtract = a.subtract(b).toString();
        String multiply = a.multiply(b).toString();
        String divide = b.divide(a).toString();
        System.out.println(add);
        System.out.println(subtract);
        System.out.println(multiply);
        System.out.println(divide);

        BigDecimal da = new BigDecimal("11.22");
        BigDecimal db = new BigDecimal("44.55");
        String dadd = da.add(db).toString();
        String dsubtract = da.subtract(db).toString();
        String dmultiply = da.multiply(db).toString();
        String ddivide = db.divide(da, BigDecimal.ROUND_HALF_UP).toString();
        System.out.println(dadd);
        System.out.println(dsubtract);
        System.out.println(dmultiply);
        System.out.println(ddivide);
    }
}
