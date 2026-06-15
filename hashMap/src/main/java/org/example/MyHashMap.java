package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> {
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 16;
    private int size = 0;

    private Node<K, V>[] buckets = new Node[capacity];

    // ================================================================================

    public int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(K key, V value) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Node<K, V> head = buckets[index];

        for (; head != null; head = head.next) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> head = buckets[index];

        for (; head != null; head = head.next) {
            if (head.key.equals(key)) {
                return head.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        Node<K, V> head = buckets[index];

        for (; head != null; head = head.next) {
            if (head.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Node<K, V> head = buckets[index];
        Node<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = head.next;
                } else {
                    prev.next = head.next;
                }

                size--;
                return head.value;
            }

            prev = head;
            head = head.next;
        }

        return null;
    }

    public List<K> keySet() {
        List<K> keys = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            for (Node<K, V> head = buckets[i]; head != null; head = head.next) {
                keys.add(head.key);
            }
        }

        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            for (Node<K, V> head = buckets[i]; head != null; head = head.next) {
                values.add(head.value);
            }
        }

        return values;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newBuckets = new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> head = buckets[i];

            while (head != null) {
                Node<K, V> nextNode = head.next;

                int newIndex = 0;

                if (head.key != null) {
                    newIndex = (head.key.hashCode() & 0x7fffffff) % newCapacity;
                }

                head.next = newBuckets[newIndex];
                newBuckets[newIndex] = head;

                head = nextNode;
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }
}
