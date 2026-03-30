package com.elevator.models;

public class FloorButton extends Button {

    private final int floor;
    private final int elevatorId;

    public FloorButton(int floor, int elevatorId) {
        super();
        this.floor = floor;
        this.elevatorId = elevatorId;
    }

    public int getFloor() {
        return floor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    @Override
    protected void handlePress() {
        System.out.println("[Elevator " + elevatorId + "] Floor " + floor + " button pressed.");
    }
}
