package com.elevator.panels;

import com.elevator.enums.Direction;
import com.elevator.models.DirectionalButton;
import com.elevator.models.ExternalDisplay;

import java.util.ArrayList;
import java.util.List;

public class ExternalPanel {

    private final int floor;
    private final DirectionalButton upButton;
    private final DirectionalButton downButton;
    private final List<ExternalDisplay> elevatorDisplays;

    public ExternalPanel(int floor, int totalElevators) {
        this.floor = floor;
        this.upButton = new DirectionalButton(Direction.UP, floor);
        this.downButton = new DirectionalButton(Direction.DOWN, floor);
        this.elevatorDisplays = new ArrayList<>();
        for (int i = 1; i <= totalElevators; i++) {
            elevatorDisplays.add(new ExternalDisplay(i, floor));
        }
    }

    public DirectionalButton getUpButton() {
        return upButton;
    }

    public DirectionalButton getDownButton() {
        return downButton;
    }

    public List<ExternalDisplay> getElevatorDisplays() {
        return elevatorDisplays;
    }

    public ExternalDisplay getDisplayForElevator(int elevatorId) {
        return elevatorDisplays.get(elevatorId - 1);
    }

    public void showAllDisplays() {
        System.out.println("[Floor " + floor + " Panel Displays]");
        for (ExternalDisplay display : elevatorDisplays) {
            display.show();
        }
    }

    public int getFloor() {
        return floor;
    }
}
