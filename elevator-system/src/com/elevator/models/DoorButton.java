package com.elevator.models;

import com.elevator.enums.DoorButtonAction;

public class DoorButton extends Button {

    private final DoorButtonAction action;
    private final int elevatorId;

    public DoorButton(DoorButtonAction action, int elevatorId) {
        super();
        this.action = action;
        this.elevatorId = elevatorId;
    }

    public DoorButtonAction getAction() {
        return action;
    }

    @Override
    protected void handlePress() {
        System.out.println("[Elevator " + elevatorId + "] Door " + action + " button pressed.");
    }
}
