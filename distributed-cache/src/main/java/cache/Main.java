package cache;

public class Main {
    public static void main(String[] args) {
        Database<String, String> db = new MockDatabase<>();
        db.put("key1", "val1");
        db.put("key2", "val2");
        db.put("key3", "val3");
        
        DistributionStrategy<String> moduloStrategy = new ModuloDistributionStrategy<>();
        
        // 3 Nodes, each with capacity 2
        DistributedCache<String, String> cache = new DistributedCache<>(3, 2, moduloStrategy, db);
        
        System.out.println("--- Test 1: Cache Miss followed by Cache Hit ---");
        System.out.println("Result: " + cache.get("key1")); // Miss, fetched from DB
        System.out.println("Result: " + cache.get("key1")); // Hit
        
        System.out.println("\n--- Test 2: Put and Get ---");
        cache.put("key4", "val4");
        System.out.println("Result: " + cache.get("key4")); // Hit
        
        System.out.println("\n--- Test 3: DB Fallback ---");
        System.out.println("Result: " + cache.get("key2")); // Miss
        System.out.println("Result: " + cache.get("key3")); // Miss
    }
}
