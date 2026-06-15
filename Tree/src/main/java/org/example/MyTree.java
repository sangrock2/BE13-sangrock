package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class MyTree {
    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;


    //========================================

    public void insert(int value) {
        root = insertNode(root, value);
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    public int getHeight() {
        return getHeight(root);
    }

    public int getNodeCount() {
        return getNodeCount(root);
    }

    public int getLeafCount() {
        return getLeafCount(root);
    }

    public boolean search(int value) {
        return search(root, value);
    }

    public void bfs() {
        if (root == null) return;

        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            Node node = q.poll();
            System.out.print(node.value + " ");

            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }

        System.out.println();
    }

    //======================================

    private Node insertNode(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        }

        return node;
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getNodeCount(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + getNodeCount(node.left) + getNodeCount(node.right);
    }

    private int getLeafCount(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        }

        return getLeafCount(node.left) + getLeafCount(node.right);
    }

    private boolean search(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (value == node.value) {
            return true;
        }

        if (value < node.value) {
            return search(node.left, value);
        } else {
            return search(node.right, value);
        }

    }
}
