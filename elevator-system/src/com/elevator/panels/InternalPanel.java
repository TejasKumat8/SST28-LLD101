package com.elevator.panels;

import com.elevator.enums.DoorButtonAction;
import com.elevator.models.DoorButton;
import com.elevator.models.FloorButton;

import java.util.ArrayList;
import java.util.List;

public class InternalPanel {

    private final int elevatorId;
    private final List<FloorButton> floorButtons;
    private final DoorButton openButton;
    private final DoorButton closeButton;

    public InternalPanel(int elevatorId, int totalFloors) {
        this.elevatorId = elevatorId;
        this.floorButtons = new ArrayList<>();
        for (int i = 1; i <= totalFloors; i++) {
            floorButtons.add(new FloorButton(i, elevatorId));
        }
        this.openButton = new DoorButton(DoorButtonAction.OPEN, elevatorId);
        this.closeButton = new DoorButton(DoorButtonAction.CLOSE, elevatorId);
    }

    public FloorButton getFloorButton(int floor) {
        return floorButtons.get(floor - 1);
    }

    public DoorButton getOpenButton() {
        return openButton;
    }

    public DoorButton getCloseButton() {
        return closeButton;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public List<FloorButton> getAllFloorButtons() {
        return floorButtons;
    }
}
