package org.example;

// 변수 : 값을 저장할 수 있는 메모리상의 공간
// age 공간 20 값 -> 주소
// 변수 타입 : 변수에 저정할 값이 어떤 타입인지를 저장하는 것
// 변수 이름 : 변수에 붙인 이름
// 변수 초기화 : 변수를 사용하기 전에 처음으로 값을 저장하는 것

// 변수의 명명 규칙
// 1. 대소문자가 구분되며 길이에 제한이 없다.
// 2. 예약어를 사용해서는 안된다.

public class B_variable_method {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int minus(int a, int b) {
        return a - b;
    }

    public static <T> void printResult(T result) {
        System.out.println(result);
    }

    public static void main(String[] args) {
        byte myByte = 127;
        System.out.println("byte = " + myByte);

        short myShort = 32767;
        System.out.println("short = " + myShort);

        int age = 20;
        System.out.println("age = " + age);

        int num1 = 100;
        System.out.println("num1 = " + num1);

        num1 = 200;
        System.out.println("num1 = " + num1);

        num1 = 300 + 200;
        System.out.println("num1 = " + num1);

        long myLong = 1234567890123456789L;
        System.out.println("long = " + myLong);

        char myChar = 'A';
        System.out.println("char = " + myChar);

        myChar = 66;
        System.out.println("char = " + myChar);

        boolean myBoolean = true;
        System.out.println("myBoolean = " + myBoolean);

        myBoolean = false;
        System.out.println("myBoolean = " + myBoolean);

        // 상수
        final float PI = 3.14f;
        System.out.println("PI = " + PI);

        final double PI2 = Math.PI;
        System.out.println("PI2 = " + PI2);

        // 참조형
        String str = "Hello World";
        System.out.println("str = " + str);

        // 함수
        int result = add(10, 20);
        System.out.println("result = " + result);

        printResult(result);
        printResult(PI);
        printResult(str);
        printResult(myLong);
        printResult(myChar);
    }
}
