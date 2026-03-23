package parking;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ParkingTicket is generated the moment a vehicle enters.
 *
 * Think of it like the physical slip you get at a toll-booth:
 * - it records WHAT vehicle, WHERE it parked, and WHEN it entered
 * - the exit process takes this ticket and uses it to compute the bill
 *
 * ticketId : globally unique (UUID) so tickets can't be confused
 * vehicle : which vehicle is parked
 * slot : the exact ParkingSlot assigned (carries slotType for billing)
 * entryTime : when the vehicle entered (for duration calc later)
 * entryGateId: for audit/logging — which gate was used
 */
public class ParkingTicket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final LocalDateTime entryTime;
    private final String entryGateId;

    public ParkingTicket(Vehicle vehicle, ParkingSlot slot,
            LocalDateTime entryTime, String entryGateId) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.entryGateId = entryGateId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getEntryGateId() {
        return entryGateId;
    }

    @Override
    public String toString() {
        return "=== PARKING TICKET ==="
                + "\n  Ticket ID  : " + ticketId
                + "\n  Vehicle    : " + vehicle
                + "\n  Slot       : " + slot.getSlotId() + " (" + slot.getSlotType() + ")"
                + "\n  Entry Gate : " + entryGateId
                + "\n  Entry Time : " + entryTime
                + "\n======================";
    }
}
