package cache;

import java.util.ArrayList;
import java.util.List;

public class DistributedCache<K, V> {
    private final List<CacheNode<K, V>> nodes;
    private final DistributionStrategy<K> distributionStrategy;
    private final Database<K, V> database;

    public DistributedCache(int numberOfNodes, int capacityPerNode, DistributionStrategy<K> distributionStrategy, Database<K, V> database) {
        this.nodes = new ArrayList<>();
        // For simplicity, we create the nodes here using LRUEvictionPolicy. 
        // In a more complex setup, a factory could provide nodes to allow pluggable eviction policies per node.
        for (int i = 0; i < numberOfNodes; i++) {
            this.nodes.add(new CacheNode<>(capacityPerNode, new LRUEvictionPolicy<>()));
        }
        this.distributionStrategy = distributionStrategy;
        this.database = database;
    }

    public V get(K key) {
        CacheNode<K, V> targetNode = getTargetNode(key);
        V value = targetNode.get(key);

        if (value != null) {
            System.out.println("Cache HIT for key: " + key);
            return value;
        }

        System.out.println("Cache MISS for key: " + key);
        value = database.get(key);

        if (value != null) {
            targetNode.put(key, value);
        }

        return value;
    }

    public void put(K key, V value) {
        CacheNode<K, V> targetNode = getTargetNode(key);
        targetNode.put(key, value);
        
        // As per requirements: "assume the database is also updated as needed"
        database.put(key, value);
    }

    @SuppressWarnings("unchecked")
    private CacheNode<K, V> getTargetNode(K key) {
        // We ensure type safety through the list management
        return (CacheNode<K, V>) distributionStrategy.getNode((List) nodes, key);
    }
}
