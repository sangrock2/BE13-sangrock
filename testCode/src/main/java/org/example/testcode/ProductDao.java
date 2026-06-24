package org.example.testcode;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ProductDao {
    private HashMap<String, Product> store = new HashMap<>();

    public void add(Product product) {
        if (store.containsKey(product.getId()))
            throw new IllegalStateException("Already exist id: " + product.getId());
        store.put(product.getId(), product);
    }

    public Product get(String id) {
        Product found = store.get(id);
        if (found == null) throw new NoSuchElementException("Not found id: " + id);
        return found;
    }

    public int getCount() { return store.size(); }
    public void deleteAll() { store.clear(); }
}
