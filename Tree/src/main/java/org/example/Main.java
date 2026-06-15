package org.example;

public class Main {
    public static void main(String[] args) {
        MyTree tree = new MyTree();

        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int v : values) {
            tree.insert(v);
        }

        // 1. 순회 (Traversals) 테스트
        tree.preOrder();    // 50 30 20 40 70 60 80
        tree.inOrder();     // 20 30 40 50 60 70 80 (정렬된 결과)
        tree.postOrder();   // 20 40 30 60 80 70 50
        tree.bfs();         // 50 30 70 20 40 60 80 (층별 순회)

        // 2. 트리 상태 및 통계
        System.out.println("Tree Height : " + tree.getHeight());       // 3
        System.out.println("Total Nodes : " + tree.getNodeCount());    // 7
        System.out.println("Leaf Nodes  : " + tree.getLeafCount());    // 4

        // 3. 탐색 (Search) 테스트
        System.out.println("Search 40 : " + tree.search(40)); // true (존재하는 값)
        System.out.println("Search 99 : " + tree.search(99)); // false (없는 값)
    }
}