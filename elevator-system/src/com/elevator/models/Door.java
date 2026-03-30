package com.elevator.models;

import com.elevator.enums.DoorState;
import com.elevator.enums.ElevatorState;

public class Door {

    private DoorState doorState;

    public Door() {
        this.doorState = DoorState.CLOSED;
    }

    public boolean open(ElevatorState elevatorState) {
        if (elevatorState == ElevatorState.IDLE) {
            this.doorState = DoorState.OPEN;
            System.out.println("  >> Door OPENED.");
            return true;
        }
        System.out.println("  >> Cannot open door: elevator is in motion.");
        return false;
    }

    public void close() {
        if (this.doorState == DoorState.OPEN) {
            this.doorState = DoorState.CLOSED;
            System.out.println("  >> Door CLOSED.");
        }
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public boolean isOpen() {
        return doorState == DoorState.OPEN;
    }
}
