package parking;

import java.time.LocalDateTime;

/**
 * Main demonstrates the full lifecycle of the parking lot system.
 *
 * Setup:
 * - 3-floor parking lot
 * - Each floor has 3 SMALL, 3 MEDIUM, 2 LARGE slots
 * - 3 entry gates (one per floor)
 *
 * Scenarios demonstrated:
 * 1. Normal park + exit for all vehicle types
 * 2. Slot upgrade: TWO_WHEELER forced into MEDIUM (all SMALL slots full)
 * 3. Billing on slot type (bike in MEDIUM pays MEDIUM rate)
 * 4. Nearest-slot selection across floors
 * 5. Overshoot: parking when fully full
 * 6. status() before and after operations
 */
public class Main {

    public static void main(String[] args) {

        // ── 1. Build the parking lot ─────────────────────────────────────
        ParkingLot.reset(); // clear any previous singleton state
        ParkingLot lot = ParkingLot.getInstance();

        // Create 3 floors, each with 3 SMALL + 3 MEDIUM + 2 LARGE slots
        for (int floorNum = 0; floorNum < 3; floorNum++) {
            ParkingFloor floor = new ParkingFloor(floorNum);
            int slotIdx = 0;

            for (int i = 0; i < 3; i++, slotIdx++) {
                floor.addSlot(new ParkingSlot(
                        "F" + floorNum + "-S" + slotIdx, SlotType.SMALL, floorNum, slotIdx));
            }
            for (int i = 0; i < 3; i++, slotIdx++) {
                floor.addSlot(new ParkingSlot(
                        "F" + floorNum + "-M" + slotIdx, SlotType.MEDIUM, floorNum, slotIdx));
            }
            for (int i = 0; i < 2; i++, slotIdx++) {
                floor.addSlot(new ParkingSlot(
                        "F" + floorNum + "-L" + slotIdx, SlotType.LARGE, floorNum, slotIdx));
            }

            lot.addFloor(floor);
            // One entry gate per floor
            lot.addGate(new EntryGate("G" + floorNum, floorNum));
        }

        System.out.println("========================================");
        System.out.println("    MULTILEVEL PARKING LOT SYSTEM");
        System.out.println("========================================");

        // ── 2. Initial status ────────────────────────────────────────────
        System.out.println(">> Initial status:");
        lot.status();

        // ── 3. Park vehicles ─────────────────────────────────────────────
        LocalDateTime base = LocalDateTime.of(2026, 3, 23, 9, 0);

        // Scenario A: Normal park — TWO_WHEELER at Gate G0 (floor 0)
        System.out.println("── Scenario A: Park a bike via G0 (floor 0) ──");
        Vehicle bike1 = new Vehicle("MH12-AB-1111", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = lot.park(bike1, base, SlotType.SMALL, "G0");
        System.out.println(t1);

        // Scenario B: Park a car at Gate G1 (floor 1) — wants MEDIUM
        System.out.println("\n── Scenario B: Park a car via G1 (floor 1) ──");
        Vehicle car1 = new Vehicle("MH12-CD-2222", VehicleType.CAR);
        ParkingTicket t2 = lot.park(car1, base.plusMinutes(5), SlotType.MEDIUM, "G1");
        System.out.println(t2);

        // Scenario C: Park a bus at Gate G2 (floor 2) — LARGE only
        System.out.println("\n── Scenario C: Park a bus via G2 (floor 2) ──");
        Vehicle bus1 = new Vehicle("MH12-EF-3333", VehicleType.BUS);
        ParkingTicket t3 = lot.park(bus1, base.plusMinutes(10), SlotType.LARGE, "G2");
        System.out.println(t3);

        // Scenario D: Fill all SMALL slots on floor 0 by parking 2 more bikes
        System.out.println("\n── Filling remaining SMALL slots on floor 0 ──");
        Vehicle bike2 = new Vehicle("MH12-GH-4444", VehicleType.TWO_WHEELER);
        ParkingTicket t4 = lot.park(bike2, base.plusMinutes(15), SlotType.SMALL, "G0");
        System.out.println(t4);
        Vehicle bike3 = new Vehicle("MH12-IJ-5555", VehicleType.TWO_WHEELER);
        ParkingTicket t5 = lot.park(bike3, base.plusMinutes(20), SlotType.SMALL, "G0");
        System.out.println(t5);

        // Floor 0 SMALL is now full. Next bike should auto-upgrade to MEDIUM.
        System.out.println("\n── Scenario E: Slot Upgrade — bike when SMALL is full on floor 0 ──");
        System.out.println("   (All SMALL slots on floor 0 are now occupied)");
        Vehicle bike4 = new Vehicle("MH12-KL-6666", VehicleType.TWO_WHEELER);
        ParkingTicket t6 = lot.park(bike4, base.plusMinutes(25), SlotType.SMALL, "G0");
        System.out.println(t6);
        if (t6 != null) {
            System.out.println("   => Bike actually got: " + t6.getSlot().getSlotId()
                    + " (" + t6.getSlot().getSlotType() + ")");
            System.out.println("   Billing rate for this slot = Rs."
                    + t6.getSlot().getSlotType().getHourlyRate() + "/hr");
        }

        // ── 4. Status mid-game ────────────────────────────────────────────
        System.out.println("\n>> Status after parking 6 vehicles:");
        lot.status();

        // ── 5. Exit scenarios ─────────────────────────────────────────────

        // Bike1 exits after 90 minutes → 2 hours charged (ceiling)
        System.out.println("── Exit: bike1 after 90 min (2 hrs charged) ──");
        LocalDateTime exitTime1 = base.plusMinutes(90);
        Bill bill1 = lot.exit(t1.getTicketId(), exitTime1);
        System.out.println(bill1);

        // Car exits after 3 hours exactly → 3 hours charged
        System.out.println("\n── Exit: car after 180 min (3 hrs charged) ──");
        LocalDateTime exitTime2 = base.plusMinutes(180);
        Bill bill2 = lot.exit(t2.getTicketId(), exitTime2);
        System.out.println(bill2);

        // Bus exits after 2.5 hours → 3 hours charged
        System.out.println("\n── Exit: bus after 150 min (3 hrs charged) ──");
        LocalDateTime exitTime3 = base.plusMinutes(150);
        Bill bill3 = lot.exit(t3.getTicketId(), exitTime3);
        System.out.println(bill3);

        // Upgraded bike exits → bill uses MEDIUM rate, not SMALL
        System.out.println("\n── Exit: upgraded bike (should pay MEDIUM rate) ──");
        LocalDateTime exitTime6 = base.plusHours(1);
        Bill bill6 = lot.exit(t6.getTicketId(), exitTime6);
        System.out.println(bill6);

        // ── 6. Final status ────────────────────────────────────────────────
        System.out.println(">> Final status after exits:");
        lot.status();

        // ── 7. Error scenario: invalid ticket ─────────────────────────────
        System.out.println("── Error: exit with fake ticket ID ──");
        lot.exit("FAKEID", LocalDateTime.now());
    }
}
