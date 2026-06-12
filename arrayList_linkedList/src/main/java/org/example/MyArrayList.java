package org.example;

import java.util.Arrays;

public class MyArrayList {
    private String[] arr = new String[10];
    private int size = 0;

    void addLast(String data) {
        if (size == arr.length) {
            ensureCapacity();
        }

        arr[size++] = data;
    }

    String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return arr[index];
    }

    int size() {
        return size;
    }

    private void ensureCapacity() {
        arr = Arrays.copyOf(arr, arr.length * 2);
    }

    void addFirst(String data) {
        if (size == arr.length) {
            ensureCapacity();
        }

        for (int i = size; i > 0; i--) {
            arr[i] = arr[i-1];
        }

        arr[0] = data;
        size++;
    }

    void insert(int index, String data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (size == arr.length) {
            ensureCapacity();
        }

        for (int i = size; i > index; i--) {
            arr[i] = arr[i-1];
        }

        arr[index] = data;
        size++;
    }

    void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i+1];
        }

        size--;
    }
}
