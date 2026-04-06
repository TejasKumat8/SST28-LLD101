package cache;

import java.util.List;

public interface DistributionStrategy<K> {
    CacheNode<K, ?> getNode(List<CacheNode<K, ?>> nodes, K key);
}
