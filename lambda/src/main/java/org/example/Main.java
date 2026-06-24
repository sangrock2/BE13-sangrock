package org.example;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n========== 1. Anonymous Class vs Lambda ==========");
        Operation add1 = new Operation() {
            @Override
            public int apply(int a, int b) {
                return a+b;
            }
        };

        System.out.println(add1.apply(1, 2));

        Operation add2 = (a, b) -> a+b;
        System.out.println(add2.apply(1, 2));

        System.out.println("\n========== 2. Lambda Operation ==========");

        Operation add3 = (int a, int b) -> { return a+b; };
        Operation add4 = (a, b) -> { return a+b; };
        Operation add5 = (a, b) -> a+b;

        Operation sub = (a, b) -> a-b;
        Operation mul = (a, b) -> a*b;

        System.out.println("add : " + add5.apply(5, 10));
        System.out.println("sub : " + sub.apply(5, 10));
        System.out.println("mul : " + mul.apply(5, 10));

        System.out.println("\n========== 3. Parameters Number ==========");

        Runnable print1 = () -> System.out.println("0 Parameters");
        print1.run();

        Printer print2 = p -> System.out.println("1 Parameters");

        Operation print3 = (a, b) -> a+b;
        System.out.println("2 Parameters" + print3.apply(5, 10));

        System.out.println("\n========== 4. Sorting Lists With Comparator ==========");

        ArrayList<String> list = new ArrayList<>();

        list.add("ABC");
        list.add("A");
        list.add("DE");

        System.out.println("Before Sorting : " + list);

        list.sort((String a, String b) -> a.length() - b.length());
        System.out.println("After Sorting DESC: " + list);

        list.sort((String a, String b) -> b.length() - a.length());
        System.out.println("After Sorting ASC: " + list);

        list.sort((String a, String b) -> a.compareTo(b));
        System.out.println("After Sorting Alphabet: " + list);

        System.out.println("\n========== 5. Predicate ==========");
        Predicate<String> predicate = (s) -> s.isEmpty();
        System.out.println(predicate.test(""));
        System.out.println(predicate.test("HELLO"));

        System.out.println("\n========== 6. :: ==========");
        list.forEach(System.out::println);



    }
}