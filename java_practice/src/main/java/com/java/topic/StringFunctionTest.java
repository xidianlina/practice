package com.java.topic;

public class StringFunctionTest {
    public static void main(String[] args) {
        String str = "abcdefghaifa";
        int index = str.indexOf('d');
        System.out.println(index);

        char ch = str.charAt(2);
        System.out.println(ch);

        String replaceString = str.replace('a', 'z');
        System.out.println(str);
        System.out.println(replaceString);

        String s = " afda  ";
        System.out.println(s + s.length());
        String res = s.trim();
        System.out.println(res + res.length());
    }
}
