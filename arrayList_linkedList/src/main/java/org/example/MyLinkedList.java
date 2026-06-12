package org.example;

public class MyLinkedList {
    static class Node {
        String data;
        Node prev;   // 앞 노드
        Node next;   // 뒤 노드

        Node(String data) {
            this.data = data;
        }
    }

    private Node head;   // 첫 노드
    private Node tail;   // 마지막 노드
    private int size;

    void addLast(String data) {
        Node node = new Node(data);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }

        size++;
    }

    void printLinks() {
        Node cur = head;
        while (cur != null) {
            String p = (cur.prev == null) ? "null" : cur.prev.data;
            String n = (cur.next == null) ? "null" : cur.next.data;
            System.out.print("[" + p + " <- " + cur.data + " -> " + n + "] ");
            cur = cur.next;
        }
        System.out.println();
    }

    void addFirst(String data) {
        Node node = new Node(data);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;
    }

    private Node nodeAt(int index) {
        Node cur = head;

        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return cur;
    }

    String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return nodeAt(index).data;
    }

    void insert(int index, String data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(data);
        } else if (index == size) {
            addLast(data);
        } else {
            Node next = nodeAt(index);
            Node prev = next.prev;

            Node node = new Node(data);
            node.prev = prev;
            node.next = next;

            prev.next = node;
            next.prev = node;

            size++;
        }
    }

    void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node node = nodeAt(index);

        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
    }

    int size() { return size; }
}
