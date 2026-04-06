package cache;

import java.util.HashMap;
import java.util.Map;

public class MockDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> data;

    public MockDatabase() {
        this.data = new HashMap<>();
    }

    @Override
    public V get(K key) {
        System.out.println("Fetching key " + key + " from database...");
        return data.get(key);
    }

    @Override
    public void put(K key, V value) {
        System.out.println("Writing key " + key + " to database...");
        data.put(key, value);
    }
}
