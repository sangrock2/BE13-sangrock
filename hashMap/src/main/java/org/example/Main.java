package org.example;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // put & get
        map.put("apple", 1);
        map.put("banana", 2);
        System.out.println(map.get("apple"));   // 1
        System.out.println(map.get("cherry")); // null

        // containKey, remov
        System.out.println("Has banana? " + map.containsKey("banana")); // true
        System.out.println("Remove banana: " + map.remove("banana"));   // 2
        System.out.println("Has banana now? " + map.containsKey("banana")); // false

        // keySet, values
        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
    }
}