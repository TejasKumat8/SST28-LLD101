package ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {

    private final RateLimiterConfig config;

    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    private final Map<String, Long> windowStartTimes = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(RateLimiterConfig config) {
        this.config = config;
    }

    @Override
    public synchronized boolean isAllowed(String key) {
        long now = System.currentTimeMillis();

        windowStartTimes.putIfAbsent(key, now);
        counters.putIfAbsent(key, new AtomicInteger(0));

        long windowStart = windowStartTimes.get(key);

        if (now - windowStart >= config.getWindowDurationMillis()) {
            windowStartTimes.put(key, now);
            counters.put(key, new AtomicInteger(0));
        }

        int currentCount = counters.get(key).incrementAndGet();
        return currentCount <= config.getLimit();
    }
}
