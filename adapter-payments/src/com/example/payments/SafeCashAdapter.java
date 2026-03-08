package com.example.payments;

import java.util.Objects;

/**
 * Adapter: bridges SafeCashClient → PaymentGateway.
 *
 * SafeCashClient speaks: createPayment(amount, user) → SafeCashPayment →
 * confirm() → String
 * PaymentGateway speaks: charge(customerId, amountCents) → String
 *
 * Note the argument-order mismatch: SafeCashClient takes (amount, user)
 * while PaymentGateway takes (customerId, amountCents) — the adapter handles
 * the swap.
 */
public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient client;

    public SafeCashAdapter(SafeCashClient client) {
        this.client = Objects.requireNonNull(client, "SafeCashClient must not be null");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        // Translate: gateway(customerId, amountCents) → SDK(amount, user)
        SafeCashPayment payment = client.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}
