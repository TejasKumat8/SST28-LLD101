package parking;

/**
 * EntryGate represents a physical entry point to the parking lot.
 *
 * gateId : unique identifier, e.g. "G1"
 * floorNumber : the floor this gate opens onto — used to compute
 * how close any given slot is to this gate.
 *
 * Nearest-slot distance formula (used in ParkingLot):
 * distance(gate, slot) = |gate.floorNumber - slot.floorNumber| * FLOOR_WEIGHT
 * + slot.slotIndex
 *
 * This ensures:
 * 1. Slots on the same floor are always preferred over other floors.
 * 2. Among same-floor slots, the one with the smallest index (closest entry)
 * wins.
 */
public class EntryGate {

    private final String gateId;
    private final int floorNumber; // which floor this gate leads to

    public EntryGate(String gateId, int floorNumber) {
        this.gateId = gateId;
        this.floorNumber = floorNumber;
    }

    public String getGateId() {
        return gateId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        return "Gate[" + gateId + ", floor=" + floorNumber + "]";
    }
}
