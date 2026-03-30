package com.elevator.models;

import com.elevator.enums.Direction;

public abstract class Display {

    protected int currentFloor;
    protected Direction direction;

    public Display() {
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
    }

    public void updateFloor(int floor) {
        this.currentFloor = floor;
    }

    public void updateDirection(Direction direction) {
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public abstract void show();
}
