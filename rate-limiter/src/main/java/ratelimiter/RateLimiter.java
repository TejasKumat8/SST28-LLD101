package ratelimiter;

public interface RateLimiter {
    boolean isAllowed(String key);
}
