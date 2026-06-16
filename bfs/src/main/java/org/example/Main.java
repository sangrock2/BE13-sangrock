package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        boolean[] visited = new boolean[9 + 1];
        int[] dist = new int[9 + 1];
        int[] parent = new int[9 + 1];

        Graph graph = new Graph(9);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 8);
        graph.addEdge(6, 9);
        graph.addEdge(7, 9);

        int startVertex = 1;
        Queue<Integer> queue = new LinkedList<>();

        visited[startVertex] = true;
        dist[startVertex] = 0;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int abj : graph.getAdjacencyList()[vertex]) {
                if (!visited[abj]) {
                    visited[abj] = true;
                    dist[abj] = dist[vertex] + 1;
                    parent[abj] = vertex;
                    queue.add(abj);
                }
            }
        }

        int target = 5;
        System.out.println("\nDistance to " + target + " : " + dist[target]);
        Stack<Integer> stack = new Stack<>();

        int curr = target;

        while (curr != 0) {
            stack.push(curr);
            curr = parent[curr];
        }

        System.out.print("Path to " + target + " : ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
            if (!stack.isEmpty()) {
                System.out.print(" -> ");
            }
        }
    }
}