package com.elevator;

import com.elevator.controller.ElevatorController;
import com.elevator.enums.Direction;

public class Main {

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("   ELEVATOR SYSTEM LLD - SIMULATION");
        System.out.println("   Floors: 15  |  Elevators: 3");
        System.out.println("============================================");

        ElevatorController controller = new ElevatorController(15, 3);

        System.out.println("\n--- SCENARIO 1: Passenger on Floor 1 calls elevator going UP ---");
        controller.pressExternalButton(1, Direction.UP);
        controller.step();
        controller.boardPassenger(1, 70.0);
        controller.pressInternalButton(1, 7);
        controller.step();
        controller.exitPassenger(1, 70.0);

        System.out.println("\n--- SCENARIO 2: Multiple passengers from different floors simultaneously ---");
        controller.pressExternalButton(5, Direction.UP);
        controller.pressExternalButton(10, Direction.DOWN);
        controller.pressExternalButton(3, Direction.UP);
        controller.step();
        controller.step();
        controller.step();

        System.out.println("\n--- SCENARIO 3: Passengers select destinations inside elevator ---");
        controller.pressInternalButton(2, 12);
        controller.pressInternalButton(3, 4);
        controller.step();
        controller.step();

        System.out.println("\n--- SCENARIO 4: Capacity enforcement (max 8 passengers / 680 kg) ---");
        for (int i = 0; i < 9; i++) {
            controller.boardPassenger(1, 75.0);
        }

        System.out.println("\n--- SCENARIO 5: Manual door operations ---");
        controller.pressOpenDoor(2);
        controller.pressCloseDoor(2);

        controller.displaySystemStatus();

        System.out.println("\n--- FLOOR 5 EXTERNAL PANEL DISPLAY ---");
        controller.displayFloorPanel(5);

        System.out.println("\n============================================");
        System.out.println("   SIMULATION COMPLETE");
        System.out.println("============================================");
    }
}
