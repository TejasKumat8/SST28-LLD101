package ratelimiter;

public class RateLimiterConfig {
    private final int limit;
    private final long windowDurationMillis;

    public RateLimiterConfig(int limit, long windowDurationMillis) {
        this.limit = limit;
        this.windowDurationMillis = windowDurationMillis;
    }

    public int getLimit() {
        return limit;
    }

    public long getWindowDurationMillis() {
        return windowDurationMillis;
    }
}
