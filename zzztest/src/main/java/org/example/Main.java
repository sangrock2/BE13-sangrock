package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] s1 = {"a", "b", "c"};
        String[] s2 = {"com", "b", "d", "p", "c"};

        ArrayList<Integer> answer = new ArrayList<>();
        answer.add(arr[0]);

        for (int i = 0; i < arr.length; i++) {
            int n = arr[i];

            if (answer.get(answer.size() - 1) != n) {
                answer.add(n);
            }
        }

        int[] ans = answer.stream().mapToInt(i -> i).toArray();


        System.out.println("Hello, World!");
    }
}