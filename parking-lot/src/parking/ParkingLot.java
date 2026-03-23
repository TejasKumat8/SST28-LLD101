package parking;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ParkingLot is the central controller of the entire system.
 *
 * ────────────────────────────────────────────────────────────────
 * DESIGN PATTERN: Singleton
 * ────────────────────────────────────────────────────────────────
 * There is exactly one physical parking lot. Using a Singleton
 * ensures:
 * - one shared state for all floors, gates, and active tickets
 * - no two threads can accidentally create two "lots" with
 * conflicting slot availability counts
 *
 * ────────────────────────────────────────────────────────────────
 * NEAREST SLOT ALGORITHM
 * ────────────────────────────────────────────────────────────────
 * Every ParkingSlot has a (floorNumber, slotIndex).
 * Every EntryGate has a floorNumber (the floor it opens onto).
 *
 * distance(gate, slot) = |gate.floor - slot.floor| * FLOOR_WEIGHT
 * + slot.slotIndex
 *
 * FLOOR_WEIGHT = 1000 (any value > max slots per floor).
 * This guarantees: same-floor slots always beat other-floor slots,
 * and within the same floor, lower indices (closer to gate) win.
 *
 * ────────────────────────────────────────────────────────────────
 * SLOT UPGRADE LOGIC
 * ────────────────────────────────────────────────────────────────
 * If no slot of the requested type is available for the vehicle:
 * TWO_WHEELER → try SMALL → MEDIUM → LARGE
 * CAR → try MEDIUM → LARGE
 * BUS → LARGE only (no fallback)
 *
 * SlotType.canFit(vehicleType) encodes the compatibility.
 * We just iterate the upgrade order until we find an available slot.
 */
public class ParkingLot {

    // ── Singleton ──────────────────────────────────────────────
    private static ParkingLot instance;

    public static ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    // Reset for testing (so we can create a fresh lot in Main each run)
    public static void reset() {
        instance = null;
    }

    // ── State ──────────────────────────────────────────────────
    private final String name;
    private final List<ParkingFloor> floors;
    private final Map<String, EntryGate> gates; // gateId → gate

    // All active tickets (vehicles currently inside)
    private final Map<String, ParkingTicket> activeTickets; // ticketId → ticket

    // Weight applied per floor difference in nearest-slot calc
    private static final int FLOOR_WEIGHT = 1000;

    // ── Constructor (private) ──────────────────────────────────
    private ParkingLot() {
        this.name = "Multilevel Parking Lot";
        this.floors = new ArrayList<>();
        this.gates = new LinkedHashMap<>();
        this.activeTickets = new HashMap<>();
    }

    // ── Configuration API (called during setup) ────────────────

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void addGate(EntryGate gate) {
        gates.put(gate.getGateId(), gate);
    }

    // ── Core API ───────────────────────────────────────────────

    /**
     * park() — the entry-point API.
     *
     * @param vehicle           vehicle details (number + type)
     * @param entryTime         when the vehicle arrived
     * @param requestedSlotType the preferred slot size (SMALL/MEDIUM/LARGE)
     * @param entryGateId       which gate the vehicle entered through
     * @return ParkingTicket if a compatible slot was found; null if full
     */
    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime,
            SlotType requestedSlotType, String entryGateId) {

        EntryGate gate = gates.get(entryGateId);
        if (gate == null) {
            System.out.println("[ERROR] Unknown gate: " + entryGateId);
            return null;
        }

        // Build the list of slot types to try, in upgrade order
        List<SlotType> typesToTry = upgradeOrder(vehicle.getVehicleType(), requestedSlotType);

        for (SlotType tryType : typesToTry) {
            ParkingSlot nearestSlot = findNearestSlot(vehicle.getVehicleType(), tryType, gate);
            if (nearestSlot != null) {
                nearestSlot.occupy();
                ParkingTicket ticket = new ParkingTicket(vehicle, nearestSlot, entryTime, entryGateId);
                activeTickets.put(ticket.getTicketId(), ticket);
                return ticket;
            }
        }

        System.out.println("[FULL] No compatible slot available for " + vehicle);
        return null;
    }

    /**
     * status() — snapshot of free slots broken down by type, across all floors.
     */
    public void status() {
        System.out.println("\n=== PARKING LOT STATUS ===");
        for (SlotType type : SlotType.values()) {
            long free = floors.stream().mapToLong(f -> f.countAvailable(type)).sum();
            long total = floors.stream()
                    .flatMap(f -> f.getSlots().stream())
                    .filter(s -> s.getSlotType() == type)
                    .count();
            System.out.printf("  %-8s : %2d / %2d free%n", type, free, total);
        }
        System.out.println("==========================\n");
    }

    /**
     * exit() — the exit-point API.
     *
     * @param ticketId the ID from the ParkingTicket
     * @param exitTime when the vehicle is leaving
     * @return Bill containing duration and total amount
     */
    public Bill exit(String ticketId, LocalDateTime exitTime) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            System.out.println("[ERROR] No active ticket found for ID: " + ticketId);
            return null;
        }

        // Free the slot so the next vehicle can use it
        ticket.getSlot().vacate();

        return new Bill(ticket, exitTime);
    }

    // ── Private helpers ────────────────────────────────────────

    /**
     * Builds an ordered list of SlotTypes to try.
     * We start with the requested type; if unavailable, we upgrade.
     *
     * Example: TWO_WHEELER requests SMALL
     * → try SMALL first, then MEDIUM, then LARGE
     * (because larger slots are compatible with smaller vehicles)
     *
     * Example: CAR requests MEDIUM
     * → try MEDIUM first, then LARGE (CAR can't fit in SMALL)
     */
    private List<SlotType> upgradeOrder(VehicleType vehicleType, SlotType requested) {
        // Full upgrade chain in ascending size order
        List<SlotType> allTypes = List.of(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
        List<SlotType> result = new ArrayList<>();

        boolean startAdding = false;
        for (SlotType t : allTypes) {
            if (t == requested)
                startAdding = true;
            // Only include types that can actually fit this vehicle
            if (startAdding && t.canFit(vehicleType)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Finds the nearest FREE slot of exactly (vehicleType-compatible, slotType)
     * from a gate.
     *
     * Nearest = smallest distance value where:
     * distance = |gate.floor - slot.floor| * FLOOR_WEIGHT + slot.slotIndex
     */
    private ParkingSlot findNearestSlot(VehicleType vehicleType, SlotType slotType, EntryGate gate) {
        ParkingSlot best = null;
        int bestDist = Integer.MAX_VALUE;

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (slot.getSlotType() != slotType)
                    continue;
                if (!slot.isAvailableFor(vehicleType))
                    continue;

                int dist = Math.abs(gate.getFloorNumber() - slot.getFloorNumber()) * FLOOR_WEIGHT
                        + slot.getSlotIndex();

                if (dist < bestDist) {
                    bestDist = dist;
                    best = slot;
                }
            }
        }
        return best;
    }
}
