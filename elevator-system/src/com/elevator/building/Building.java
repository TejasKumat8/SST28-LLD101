package com.elevator.building;

import com.elevator.models.ElevatorCar;
import com.elevator.panels.InternalPanel;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private final int totalFloors;
    private final int totalElevators;
    private final List<Floor> floors;
    private final List<ElevatorCar> elevators;
    private final List<InternalPanel> internalPanels;

    public Building(int totalFloors, int totalElevators) {
        this.totalFloors = totalFloors;
        this.totalElevators = totalElevators;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();
        this.internalPanels = new ArrayList<>();

        for (int i = 1; i <= totalFloors; i++) {
            floors.add(new Floor(i, totalElevators));
        }
        for (int i = 1; i <= totalElevators; i++) {
            elevators.add(new ElevatorCar(i));
            internalPanels.add(new InternalPanel(i, totalFloors));
        }
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public int getTotalElevators() {
        return totalElevators;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<ElevatorCar> getElevators() {
        return elevators;
    }

    public List<InternalPanel> getInternalPanels() {
        return internalPanels;
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber - 1);
    }

    public ElevatorCar getElevator(int elevatorId) {
        return elevators.get(elevatorId - 1);
    }

    public InternalPanel getInternalPanel(int elevatorId) {
        return internalPanels.get(elevatorId - 1);
    }
}
