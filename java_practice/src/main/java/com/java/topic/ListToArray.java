package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ListToArray {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "cd", "dfdd");
        String[] strArray = list.toArray(new String[list.size()]);
        for (String s : strArray) {
            System.out.println(s);
        }
    }
}
