package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheNode<K, V> {
    private final int capacity;
    private final Map<K, V> storage;
    private final EvictionPolicy<K> evictionPolicy;

    public CacheNode(int capacity, EvictionPolicy<K> evictionPolicy) {
        this.capacity = capacity;
        this.storage = new ConcurrentHashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public synchronized V get(K key) {
        if (!storage.containsKey(key)) {
            return null;
        }
        evictionPolicy.keyAccessed(key);
        return storage.get(key);
    }

    public synchronized void put(K key, V value) {
        if (capacity == 0) return;

        if (storage.containsKey(key)) {
            storage.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }

        if (storage.size() >= capacity) {
            K keyToEvict = evictionPolicy.evictKey();
            if (keyToEvict != null) {
                storage.remove(keyToEvict);
            }
        }

        storage.put(key, value);
        evictionPolicy.keyAccessed(key);
    }
    
    public synchronized void remove(K key) {
        storage.remove(key);
        evictionPolicy.removeKey(key);
    }
}
