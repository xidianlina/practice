package com.java.topic.fanxing;

import java.util.ArrayList;
import java.util.List;

public class FanXing {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("aaa");
        list.add(100);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
