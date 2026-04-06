package ratelimiter;

public class InternalService {
    private final RateLimiter rateLimiter;
    private final ExternalResourceClient externalClient;

    public InternalService(RateLimiter rateLimiter, ExternalResourceClient externalClient) {
        this.rateLimiter = rateLimiter;
        this.externalClient = externalClient;
    }

    public String handleRequest(String customerId, String payload, boolean needsExternalCall) {
        System.out.println("[InternalService] Handling request for customer: " + customerId);

        if (!needsExternalCall) {
            System.out.println("[InternalService] No external call needed. Returning cached/local result.");
            return "local_result";
        }

        String rateLimitKey = "customer:" + customerId;
        if (!rateLimiter.isAllowed(rateLimitKey)) {
            System.out.println("[InternalService] Rate limit EXCEEDED for key: " + rateLimitKey + ". Request DENIED.");
            return "RATE_LIMITED";
        }

        System.out.println("[InternalService] Rate limit OK. Forwarding to external resource...");
        return externalClient.call(payload);
    }
}
