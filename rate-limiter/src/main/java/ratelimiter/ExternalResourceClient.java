package ratelimiter;

public class ExternalResourceClient {
    private final String resourceName;

    public ExternalResourceClient(String resourceName) {
        this.resourceName = resourceName;
    }

    public String call(String payload) {
        System.out.println("  [ExternalResource:" + resourceName + "] Calling with payload: " + payload);
        return "response_for_" + payload;
    }
}
