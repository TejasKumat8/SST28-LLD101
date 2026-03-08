package com.example.payments;

/**
 * Third-party SDK — SafeCash.
 * Incompatible signature: createPayment(amount, user) → SafeCashPayment.
 * This class must NOT be modified (simulates an external library).
 */
public class SafeCashClient {
    public SafeCashPayment createPayment(int amount, String user) {
        return new SafeCashPayment(amount, user);
    }
}
