package com.elevator.controller;

import com.elevator.building.Building;
import com.elevator.building.Floor;
import com.elevator.dispatch.ElevatorDispatcher;
import com.elevator.enums.Direction;
import com.elevator.models.ElevatorCar;
import com.elevator.models.ElevatorRequest;
import com.elevator.models.ExternalDisplay;
import com.elevator.panels.ExternalPanel;

public class ElevatorController {

    private final Building building;
    private final ElevatorDispatcher dispatcher;

    public ElevatorController(int totalFloors, int totalElevators) {
        this.building = new Building(totalFloors, totalElevators);
        this.dispatcher = new ElevatorDispatcher(totalFloors);
    }

    public void pressExternalButton(int floor, Direction direction) {
        System.out.println("\n[Controller] External call -> Floor " + floor + " going " + direction);
        ExternalPanel panel = building.getFloor(floor).getExternalPanel();
        if (direction == Direction.UP) {
            panel.getUpButton().press();
        } else {
            panel.getDownButton().press();
        }
        ElevatorRequest request = new ElevatorRequest(floor, direction);
        ElevatorCar selected = dispatcher.selectOptimalElevator(building.getElevators(), request);
        if (selected != null) {
            selected.addDestination(floor);
        }
    }

    public void pressInternalButton(int elevatorId, int floor) {
        System.out.println("\n[Controller] Internal floor select -> Elevator " + elevatorId + " to Floor " + floor);
        building.getInternalPanel(elevatorId).getFloorButton(floor).press();
        building.getElevator(elevatorId).addDestination(floor);
    }

    public void pressOpenDoor(int elevatorId) {
        System.out.println("\n[Controller] Open door -> Elevator " + elevatorId);
        building.getInternalPanel(elevatorId).getOpenButton().press();
        building.getElevator(elevatorId).openDoor();
    }

    public void pressCloseDoor(int elevatorId) {
        System.out.println("\n[Controller] Close door -> Elevator " + elevatorId);
        building.getInternalPanel(elevatorId).getCloseButton().press();
        building.getElevator(elevatorId).closeDoor();
    }

    public void boardPassenger(int elevatorId, double weight) {
        building.getElevator(elevatorId).boardPassenger(weight);
        updateExternalDisplays(elevatorId);
    }

    public void exitPassenger(int elevatorId, double weight) {
        building.getElevator(elevatorId).exitPassenger(weight);
        updateExternalDisplays(elevatorId);
    }

    public void step() {
        for (ElevatorCar elevator : building.getElevators()) {
            if (elevator.hasDestinations()) {
                elevator.step();
                updateExternalDisplays(elevator.getId());
            }
        }
    }

    private void updateExternalDisplays(int elevatorId) {
        ElevatorCar elevator = building.getElevator(elevatorId);
        for (Floor floor : building.getFloors()) {
            ExternalDisplay display = floor.getExternalPanel().getDisplayForElevator(elevatorId);
            display.updateFloor(elevator.getCurrentFloor());
            display.updateDirection(elevator.getDirection());
        }
    }

    public void displaySystemStatus() {
        System.out.println("\n========== ELEVATOR SYSTEM STATUS ==========");
        for (ElevatorCar elevator : building.getElevators()) {
            elevator.displayStatus();
        }
        System.out.println("============================================");
    }

    public void displayFloorPanel(int floor) {
        building.getFloor(floor).getExternalPanel().showAllDisplays();
    }

    public Building getBuilding() {
        return building;
    }
}
