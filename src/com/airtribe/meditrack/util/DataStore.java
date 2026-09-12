package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic DataStore class for storing and managing entities of any type.
 * Demonstrates Java Generics, parameterized types, and type safety.
 * 
 * @param <T> The type of entity stored in this DataStore
 */
public class DataStore<T> {
    
    private Map<String, T> store;
    private String storeName;
    
    public DataStore(String storeName) {
        this.store = new HashMap<>();
        this.storeName = storeName;
    }
    
    public void add(String key, T entity) {
        if (key == null || entity == null) {
            throw new IllegalArgumentException("Key and entity cannot be null");
        }
        store.put(key, entity);
    }
    
    public T get(String key) {
        return store.get(key);
    }
    
    public boolean contains(String key) {
        return store.containsKey(key);
    }
    
    public T remove(String key) {
        return store.remove(key);
    }
    
    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }
    
    public int size() {
        return store.size();
    }
    
    public boolean isEmpty() {
        return store.isEmpty();
    }
    
    public void clear() {
        store.clear();
    }
    
    public boolean update(String key, T entity) {
        if (!store.containsKey(key)) {
            return false;
        }
        store.put(key, entity);
        return true;
    }
    
}
