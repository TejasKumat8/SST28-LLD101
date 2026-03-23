package parking;

/**
 * ParkingSlot is the smallest physical unit of the parking lot.
 *
 * slotId : unique string ID, e.g. "F1-S3" (Floor 1, Slot 3)
 * slotType : SMALL / MEDIUM / LARGE
 * floorNumber : 0-indexed floor this slot belongs to
 * slotIndex : position within the floor (0 = closest to entry gate)
 * occupied : true when a vehicle is currently parked here
 *
 * Viva: why floorNumber + slotIndex instead of a single absolute number?
 * Because the "nearest slot" calculation uses them separately:
 * distance = |gateFloor - floorNumber| * FLOOR_WEIGHT + slotIndex
 * This naturally prefers the same floor first, then close slots on that floor.
 */
public class ParkingSlot {

    private final String slotId;
    private final SlotType slotType;
    private final int floorNumber;
    private final int slotIndex; // position within the floor

    private boolean occupied;

    public ParkingSlot(String slotId, SlotType slotType, int floorNumber, int slotIndex) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
        this.slotIndex = slotIndex;
        this.occupied = false;
    }

    /** Can this slot accept the given vehicle (type check + availability check)? */
    public boolean isAvailableFor(VehicleType vehicleType) {
        return !occupied && slotType.canFit(vehicleType);
    }

    public void occupy() {
        this.occupied = true;
    }

    public void vacate() {
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public String toString() {
        return slotId + " [" + slotType + ", floor=" + floorNumber
                + ", idx=" + slotIndex + ", " + (occupied ? "OCCUPIED" : "FREE") + "]";
    }
}
