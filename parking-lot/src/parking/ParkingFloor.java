package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ParkingFloor groups all slots on one level of the parking structure.
 *
 * Responsibilities:
 * - holds the list of slots on this floor
 * - answers how many slots of each type are free (for status())
 *
 * It does NOT decide which slot to assign — that decision belongs to ParkingLot
 * (which considers all floors together and picks the nearest slot globally).
 */
public class ParkingFloor {

    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    /** Count free slots of a specific type on this floor. */
    public long countAvailable(SlotType type) {
        return slots.stream()
                .filter(s -> s.getSlotType() == type && !s.isOccupied())
                .count();
    }
}
