package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ArrayToList {
    public static void main(String[] args) {
        String[] strArray = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = Arrays.asList(strArray);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
