package org.example;

import java.util.*;

public class GoldRanking {
    private Map<String, Integer> board = new HashMap<String, Integer>();

    public synchronized void put(String name, Integer gold) {
        board.put(name, board.getOrDefault(name, 0) + gold);
    }

    public void print() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(board.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("\n==========================");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
