package com.example.payments;

import java.util.HashMap;
import java.util.Map;

/**
 * Application entry point for the Adapter demo.
 *
 * The registry maps provider names → PaymentGateway adapters.
 * OrderService knows NOTHING about FastPayClient or SafeCashClient —
 * it only talks to the PaymentGateway interface.
 *
 * To add a third provider (e.g. "stripe"), just:
 * 1. Write StripeAdapter implements PaymentGateway
 * 2. Register: gateways.put("stripe", new StripeAdapter(new StripeClient()))
 * OrderService requires zero changes.
 */
public class App {
    public static void main(String[] args) {
        // Build the registry of adapters
        Map<String, PaymentGateway> gateways = new HashMap<>();
        gateways.put("fastpay", new FastPayAdapter(new FastPayClient()));
        gateways.put("safecash", new SafeCashAdapter(new SafeCashClient()));

        OrderService svc = new OrderService(gateways);

        String id1 = svc.charge("fastpay", "cust-1", 1299);
        String id2 = svc.charge("safecash", "cust-2", 1299);

        System.out.println(id1);
        System.out.println(id2);
    }
}
