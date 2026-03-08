package com.example.payments;

import java.util.Objects;

/**
 * Adapter: bridges FastPayClient → PaymentGateway.
 *
 * FastPayClient speaks: payNow(custId, amountCents) → String
 * PaymentGateway speaks: charge(customerId, amountCents) → String
 *
 * This adapter translates between the two without touching either side.
 */
public class FastPayAdapter implements PaymentGateway {

    private final FastPayClient client;

    public FastPayAdapter(FastPayClient client) {
        this.client = Objects.requireNonNull(client, "FastPayClient must not be null");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        // Delegate to FastPayClient's incompatible method
        return client.payNow(customerId, amountCents);
    }
}
