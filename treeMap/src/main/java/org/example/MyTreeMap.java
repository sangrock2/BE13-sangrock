package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyTreeMap<K extends Comparable<K>, V> {
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;
    private int size;

    // =========================================================

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    public V get(K key) {
        Node<K, V> node = root;

        while (node != null) {
            int cmp = key.compareTo(node.key);

            if (cmp == 0) {
                return node.value;
            }

            if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }

    public void inOrder() {
        inOrder(root);
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        Node<K, V> node = root;

        while (node != null) {
            int cmp = key.compareTo(node.key);

            if (cmp == 0) {
                return true;
            }

            if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return false;
    }

    public K firstKey() {
        if (root == null) {
            return null;
        }

        Node<K, V> node = root;

        while (node.left != null) {
            node = node.left;
        }

        return node.key;
    }

    public K lastKey() {
        if (root == null) {
            return null;
        }

        Node<K, V> node = root;

        while (node.right != null) {
            node = node.right;
        }

        return node.key;
    }

    public void remove(K key) {
        V value = get(key);
        if (value == null) {
            return;
        }

        root = remove(root, key);
        size--;
    }

    public K ceilingKey(K key) {
        Node<K, V> node = root;
        K ceilingKey = null;

        while (node != null) {
            int cmp = key.compareTo(node.key);

            if (cmp == 0) {
                return node.key;
            }

            if (cmp < 0) {
                node = node.left;
                ceilingKey = node.key;
            } else {
                node = node.right;
            }
        }

        return ceilingKey;
    }

    public K floorKey(K key) {
        Node<K, V> node = root;
        K floorKey = null;

        while (node != null) {
            int cmp = key.compareTo(node.key);

            if (cmp == 0) {
                return node.key;
            }

            if (cmp > 0) {
                node = node.right;
                floorKey = node.key;
            } else {
                node = node.left;
            }
        }

        return floorKey;
    }

    public List<K> headMap(K key) {
        List<K> keys = new ArrayList<K>();
        headMap(root, key, keys);
        return keys;
    }

    // =========================================================

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<K, V>(key, value);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

    private void inOrder(Node<K, V> node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.println(node.key + " : " + node.value);
        inOrder(node.right);
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            Node<K, V> target = node.right;

            while (target.left != null) {
                target = target.left;
            }

            node.key = target.key;
            node.value = target.value;
            node.right = remove(node.right, target.key);
        }

        return node;
    }

    private void headMap(Node<K, V> node, K key, List<K> keys) {
        if (node == null) {
            return;
        }

        int cmp = key.compareTo(node.key);

        headMap(node.left, key, keys);

        if (cmp > 0) {
            keys.add(node.key);
            headMap(node.right, key, keys);
        }
    }
}