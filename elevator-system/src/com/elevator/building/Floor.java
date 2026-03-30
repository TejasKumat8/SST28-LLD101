package com.elevator.building;

import com.elevator.panels.ExternalPanel;

public class Floor {

    private final int floorNumber;
    private final ExternalPanel externalPanel;

    public Floor(int floorNumber, int totalElevators) {
        this.floorNumber = floorNumber;
        this.externalPanel = new ExternalPanel(floorNumber, totalElevators);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ExternalPanel getExternalPanel() {
        return externalPanel;
    }
}
