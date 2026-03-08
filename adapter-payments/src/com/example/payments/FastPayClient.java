package com.example.payments;

/**
 * Third-party SDK — FastPay.
 * Incompatible signature: payNow(custId, amountCents) → String txId.
 * This class must NOT be modified (simulates an external library).
 */
public class FastPayClient {
    public String payNow(String custId, int amountCents) {
        return "FP#" + custId + ":" + amountCents;
    }
}
