package cache;

import java.util.List;

public class ModuloDistributionStrategy<K> implements DistributionStrategy<K> {
    @Override
    public CacheNode<K, ?> getNode(List<CacheNode<K, ?>> nodes, K key) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode) % nodes.size();
        return nodes.get(index);
    }
}
