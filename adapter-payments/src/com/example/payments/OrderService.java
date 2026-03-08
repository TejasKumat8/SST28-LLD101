package com.example.payments;

import java.util.Map;
import java.util.Objects;

/**
 * OrderService depends ONLY on the PaymentGateway abstraction.
 * It has no knowledge of FastPayClient, SafeCashClient, or any SDK.
 * Provider selection is done by looking up the correct adapter in the registry.
 */
public class OrderService {
    private final Map<String, PaymentGateway> gateways;

    public OrderService(Map<String, PaymentGateway> gateways) {
        this.gateways = Objects.requireNonNull(gateways, "gateways");
    }

    public String charge(String provider, String customerId, int amountCents) {
        Objects.requireNonNull(provider, "provider");
        PaymentGateway gw = gateways.get(provider);
        if (gw == null)
            throw new IllegalArgumentException("unknown provider: " + provider);
        return gw.charge(customerId, amountCents);
    }
}
