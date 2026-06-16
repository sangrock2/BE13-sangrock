package org.example;

import java.util.LinkedList;

public class Graph {
    LinkedList<Integer>[] adjacencyList;

    private int[] dist;
    private int[] parent;

    public Graph(int vertices) {
        adjacencyList = new LinkedList[vertices+1];

        for (int i = 0; i <= vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public LinkedList<Integer>[] getAdjacencyList() {
        return adjacencyList;
    }

    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }

    public void printGraph() {
        for (int i = 0; i < adjacencyList.length; i++) {
            System.out.print("Vertexs " + i + " : ");

            for (Integer v : adjacencyList[i]) {
                System.out.print(v + " ");
            }

            System.out.println();
        }
    }
}
