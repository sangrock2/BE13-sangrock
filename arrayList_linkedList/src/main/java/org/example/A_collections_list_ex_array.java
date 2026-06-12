package org.example;

public class A_collections_list_ex_array {
    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();

        list.addLast("a");
        list.addLast("b");
        list.addLast("c");
        System.out.println("size = " + list.size());

        System.out.println("0,1,2 = " + list.get(0) + ", "
                + list.get(1) + ", "
                + list.get(2));                // 기대: 가, 나, 다

        // --- Step 6 확인 ---
        list.addFirst("d");
        System.out.println("addFirst after 0,1 = " + list.get(0) + ", " + list.get(1)); // 기대: 앞, 가
        System.out.println("size = " + list.size());                 // 기대: 4
    }
}
