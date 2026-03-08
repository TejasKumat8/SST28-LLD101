package com.example.payments;

/**
 * Target interface that OrderService depends on.
 * All payment providers must be adapted to this contract.
 */
public interface PaymentGateway {
    String charge(String customerId, int amountCents);
}
