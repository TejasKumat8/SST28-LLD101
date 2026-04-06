package cache;

import java.util.LinkedHashSet;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> accessedKeys;

    public LRUEvictionPolicy() {
        this.accessedKeys = new LinkedHashSet<>();
    }

    @Override
    public void keyAccessed(K key) {
        if (accessedKeys.contains(key)) {
            accessedKeys.remove(key);
            accessedKeys.add(key);
        } else {
            accessedKeys.add(key);
        }
    }

    @Override
    public K evictKey() {
        if (accessedKeys.isEmpty()) {
            return null;
        }
        K firstKey = accessedKeys.iterator().next();
        accessedKeys.remove(firstKey);
        return firstKey;
    }

    @Override
    public void removeKey(K key) {
        accessedKeys.remove(key);
    }
}
