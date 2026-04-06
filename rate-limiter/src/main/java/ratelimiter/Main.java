package ratelimiter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiterConfig config = new RateLimiterConfig(5, 60_000);
        ExternalResourceClient externalClient = new ExternalResourceClient("PaidAnalyticsAPI");

        System.out.println("==============================");
        System.out.println(" Demo 1: Fixed Window Rate Limiter (5 req/min)");
        System.out.println("==============================");
        RateLimiter fixedWindowLimiter = new FixedWindowRateLimiter(config);
        InternalService serviceFixed = new InternalService(fixedWindowLimiter, externalClient);

        for (int i = 1; i <= 7; i++) {
            System.out.println("\n--- Request #" + i + " ---");
            String result = serviceFixed.handleRequest("T1", "data_" + i, true);
            System.out.println("Result: " + result);
        }

        System.out.println("\n\n==============================");
        System.out.println(" Demo 2: Sliding Window Rate Limiter (5 req/min)");
        System.out.println("==============================");
        RateLimiter slidingWindowLimiter = new SlidingWindowRateLimiter(config);
        InternalService serviceSliding = new InternalService(slidingWindowLimiter, externalClient);

        for (int i = 1; i <= 7; i++) {
            System.out.println("\n--- Request #" + i + " ---");
            String result = serviceSliding.handleRequest("T1", "data_" + i, true);
            System.out.println("Result: " + result);
        }

        System.out.println("\n\n==============================");
        System.out.println(" Demo 3: No external call needed - no rate limiting applied");
        System.out.println("==============================");
        RateLimiter limiter = new FixedWindowRateLimiter(new RateLimiterConfig(1, 60_000));
        InternalService serviceNoExternal = new InternalService(limiter, externalClient);

        for (int i = 1; i <= 3; i++) {
            System.out.println("\n--- Request #" + i + " (no external call) ---");
            String result = serviceNoExternal.handleRequest("T1", "data_" + i, false);
            System.out.println("Result: " + result);
        }
    }
}
