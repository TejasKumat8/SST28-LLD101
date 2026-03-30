package com.elevator.models;

import com.elevator.enums.Direction;

public class DirectionalButton extends Button {

    private final Direction direction;
    private final int floor;

    public DirectionalButton(Direction direction, int floor) {
        super();
        this.direction = direction;
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    protected void handlePress() {
        System.out.println("[Floor " + floor + "] External " + direction + " button pressed.");
    }
}
