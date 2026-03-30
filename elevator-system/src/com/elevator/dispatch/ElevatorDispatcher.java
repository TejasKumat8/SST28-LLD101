package com.elevator.dispatch;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.models.ElevatorCar;
import com.elevator.models.ElevatorRequest;

import java.util.List;

public class ElevatorDispatcher {

    private final int maxFloors;

    public ElevatorDispatcher(int maxFloors) {
        this.maxFloors = maxFloors;
    }

    public ElevatorCar selectOptimalElevator(List<ElevatorCar> elevators, ElevatorRequest request) {
        ElevatorCar bestElevator = null;
        int bestCost = Integer.MAX_VALUE;

        for (ElevatorCar elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) {
                continue;
            }
            int cost = calculateCost(elevator, request);
            if (cost < bestCost) {
                bestCost = cost;
                bestElevator = elevator;
            }
        }

        System.out.println("[Dispatcher] Assigned " + request + " -> Elevator "
                + (bestElevator != null ? bestElevator.getId() : "NONE")
                + " (cost=" + bestCost + ")");

        return bestElevator;
    }

    private int calculateCost(ElevatorCar elevator, ElevatorRequest request) {
        int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());

        if (elevator.getState() == ElevatorState.IDLE) {
            return distance;
        }

        if (elevator.getDirection() == Direction.UP) {
            if (request.getFloor() >= elevator.getCurrentFloor()
                    && request.getDirection() == Direction.UP) {
                return distance;
            }
            return maxFloors * 2 + distance;
        }

        if (elevator.getDirection() == Direction.DOWN) {
            if (request.getFloor() <= elevator.getCurrentFloor()
                    && request.getDirection() == Direction.DOWN) {
                return distance;
            }
            return maxFloors * 2 + distance;
        }

        return maxFloors * 2 + distance;
    }
}
