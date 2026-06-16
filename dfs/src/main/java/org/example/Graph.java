package org.example;

import java.util.LinkedList;

public class Graph {
    private LinkedList<Integer>[] adjacencyList;

    public Graph(int vertex) {
        adjacencyList = new LinkedList[vertex + 1];

        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }

    public void DFS(int s) {
        boolean[] visited = new boolean[adjacencyList.length];
        DFS(s, visited);
    }

    public void findPath(int s, int t) {
        boolean[] visited = new boolean[adjacencyList.length];

        LinkedList<Integer> path = new LinkedList<>();

        if (findPath(s, t, visited, path)) {
            System.out.println(path);
        } else {
            System.out.println("No Path");
        }
    }

    // =====================================

    private void DFS(int s, boolean[] visited) {
        visited[s] = true;

        for (int abj : adjacencyList[s]) {
            if (!visited[abj]) {
                DFS(abj, visited);
            }
        }
    }

    private boolean findPath(int curr, int t, boolean[] visited, LinkedList<Integer> path) {
        visited[curr] = true;
        path.add(curr);

        if (curr == t) {
            return true;
        }

        for (int abj : adjacencyList[curr]) {
            if (!visited[abj]) {
                if (findPath(abj, t, visited, path)) {
                    return true;
                }
            }
        }

        path.removeLast();
        return false;
    }
}
