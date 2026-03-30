package com.elevator.models;

import com.elevator.enums.Direction;
import com.elevator.enums.DoorState;
import com.elevator.enums.ElevatorState;

import java.util.Collections;
import java.util.TreeSet;

public class ElevatorCar {

    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private final Door door;
    private final InternalDisplay internalDisplay;
    private final TreeSet<Integer> upQueue;
    private final TreeSet<Integer> downQueue;
    private int currentPassengers;
    private double currentWeight;

    public static final int MAX_PASSENGERS = 8;
    public static final double MAX_WEIGHT_KG = 680.0;

    public ElevatorCar(int id) {
        this.id = id;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.door = new Door();
        this.internalDisplay = new InternalDisplay(MAX_PASSENGERS, MAX_WEIGHT_KG);
        this.upQueue = new TreeSet<>();
        this.downQueue = new TreeSet<>(Collections.reverseOrder());
        this.currentPassengers = 0;
        this.currentWeight = 0.0;
    }

    public void addDestination(int floor) {
        if (floor == currentFloor) {
            openDoor();
            return;
        }
        if (floor > currentFloor) {
            upQueue.add(floor);
        } else {
            downQueue.add(floor);
        }
        if (direction == Direction.IDLE) {
            direction = (floor > currentFloor) ? Direction.UP : Direction.DOWN;
        }
        if (state == ElevatorState.IDLE) {
            state = ElevatorState.MOVING;
        }
    }

    public void step() {
        if (!hasDestinations()) {
            state = ElevatorState.IDLE;
            direction = Direction.IDLE;
            syncDisplays();
            return;
        }

        int nextFloor;

        if (direction == Direction.UP) {
            if (!upQueue.isEmpty()) {
                nextFloor = upQueue.first();
                upQueue.remove(nextFloor);
            } else {
                direction = Direction.DOWN;
                nextFloor = downQueue.first();
                downQueue.remove(nextFloor);
            }
        } else {
            if (!downQueue.isEmpty()) {
                nextFloor = downQueue.first();
                downQueue.remove(nextFloor);
            } else {
                direction = Direction.UP;
                nextFloor = upQueue.first();
                upQueue.remove(nextFloor);
            }
        }

        currentFloor = nextFloor;
        System.out.println("[Elevator " + id + "] Arrived at Floor " + currentFloor
                + " (traveling " + direction + ")");

        if (!hasDestinations()) {
            direction = Direction.IDLE;
            state = ElevatorState.IDLE;
        }

        syncDisplays();
        door.open(ElevatorState.IDLE);
        door.close();
    }

    private void syncDisplays() {
        internalDisplay.updateFloor(currentFloor);
        internalDisplay.updateDirection(direction);
        internalDisplay.show();
    }

    public void openDoor() {
        door.open(ElevatorState.IDLE);
    }

    public void closeDoor() {
        door.close();
    }

    public boolean boardPassenger(double weight) {
        if (currentPassengers < MAX_PASSENGERS && (currentWeight + weight) <= MAX_WEIGHT_KG) {
            currentPassengers++;
            currentWeight += weight;
            internalDisplay.updateCapacity(currentPassengers, currentWeight);
            System.out.println("[Elevator " + id + "] Passenger boarded. Count: "
                    + currentPassengers + "/" + MAX_PASSENGERS);
            return true;
        }
        System.out.println("[Elevator " + id + "] Full capacity. Cannot board passenger.");
        return false;
    }

    public void exitPassenger(double weight) {
        if (currentPassengers > 0) {
            currentPassengers--;
            currentWeight = Math.max(0, currentWeight - weight);
            internalDisplay.updateCapacity(currentPassengers, currentWeight);
            System.out.println("[Elevator " + id + "] Passenger exited. Count: "
                    + currentPassengers + "/" + MAX_PASSENGERS);
        }
    }

    public boolean hasDestinations() {
        return !upQueue.isEmpty() || !downQueue.isEmpty();
    }

    public void displayStatus() {
        System.out.println("  Elevator " + id
                + " | Floor: " + currentFloor
                + " | Direction: " + direction
                + " | State: " + state
                + " | Passengers: " + currentPassengers + "/" + MAX_PASSENGERS
                + " | Door: " + door.getDoorState());
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public DoorState getDoorState() {
        return door.getDoorState();
    }

    public InternalDisplay getInternalDisplay() {
        return internalDisplay;
    }
}
