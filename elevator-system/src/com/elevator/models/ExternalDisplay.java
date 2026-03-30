package com.elevator.models;

public class ExternalDisplay extends Display {

    private final int elevatorId;
    private final int floorLocation;

    public ExternalDisplay(int elevatorId, int floorLocation) {
        super();
        this.elevatorId = elevatorId;
        this.floorLocation = floorLocation;
    }

    @Override
    public void show() {
        System.out.println("  [Floor " + floorLocation + " | Elevator " + elevatorId
                + "] At Floor: " + currentFloor + " | Direction: " + direction);
    }
}
