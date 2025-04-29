package com.hmdp.service.impl;

public class Test {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        System.out.println(a == b);
        System.out.println(a == c);
    }
}
