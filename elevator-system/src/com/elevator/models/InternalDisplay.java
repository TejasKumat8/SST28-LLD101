package com.elevator.models;

public class InternalDisplay extends Display {

    private int currentPassengers;
    private final int maxPassengers;
    private double currentWeight;
    private final double maxWeight;

    public InternalDisplay(int maxPassengers, double maxWeight) {
        super();
        this.currentPassengers = 0;
        this.maxPassengers = maxPassengers;
        this.currentWeight = 0.0;
        this.maxWeight = maxWeight;
    }

    public void updateCapacity(int passengers, double weight) {
        this.currentPassengers = passengers;
        this.currentWeight = weight;
    }

    @Override
    public void show() {
        System.out.println("  [Internal Display] Floor: " + currentFloor
                + " | Direction: " + direction
                + " | Passengers: " + currentPassengers + "/" + maxPassengers
                + " | Weight: " + String.format("%.1f", currentWeight) + "/" + maxWeight + " kg");
    }
}
