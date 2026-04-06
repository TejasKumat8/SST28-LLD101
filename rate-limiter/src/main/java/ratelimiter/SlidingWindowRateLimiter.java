package ratelimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final RateLimiterConfig config;
    private final Map<String, Deque<Long>> requestTimestamps = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(RateLimiterConfig config) {
        this.config = config;
    }

    @Override
    public synchronized boolean isAllowed(String key) {
        long now = System.currentTimeMillis();
        long windowStart = now - config.getWindowDurationMillis();

        requestTimestamps.putIfAbsent(key, new ArrayDeque<>());
        Deque<Long> timestamps = requestTimestamps.get(key);

        while (!timestamps.isEmpty() && timestamps.peekFirst() <= windowStart) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < config.getLimit()) {
            timestamps.addLast(now);
            return true;
        }

        return false;
    }
}
