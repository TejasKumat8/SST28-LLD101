package parking;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Bill is generated when a vehicle exits.
 *
 * Key design note:
 * Billing is based on the SLOT TYPE, not the vehicle type.
 * If a bike parked in a MEDIUM slot, it pays the MEDIUM hourly rate.
 * The rate is read from slot.getSlotType().getHourlyRate().
 *
 * Duration is calculated as:
 * ceil(minutes / 60) → we charge for every started hour
 * (partial hours count as a full hour, which is the industry norm)
 *
 * Viva: why store both ticket and exitTime, not just amount?
 * Full audit trail — you can recompute the amount, trace the vehicle,
 * find the slot, etc., from a single Bill object.
 */
public class Bill {

    private final ParkingTicket ticket;
    private final LocalDateTime exitTime;
    private final long durationMinutes;
    private final double totalAmount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime) {
        this.ticket = ticket;
        this.exitTime = exitTime;

        // How long was the vehicle parked?
        this.durationMinutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();

        // Charge for every started hour (ceil division)
        long hoursCharged = (durationMinutes + 59) / 60; // integer ceiling
        this.totalAmount = hoursCharged * ticket.getSlot().getSlotType().getHourlyRate();
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        long hrs = durationMinutes / 60;
        long mins = durationMinutes % 60;
        return "=== PARKING BILL ==="
                + "\n  Ticket ID  : " + ticket.getTicketId()
                + "\n  Vehicle    : " + ticket.getVehicle()
                + "\n  Slot       : " + ticket.getSlot().getSlotId()
                + " (" + ticket.getSlot().getSlotType() + ")"
                + "\n  Entry      : " + ticket.getEntryTime()
                + "\n  Exit       : " + exitTime
                + "\n  Duration   : " + hrs + "h " + mins + "m"
                + "\n  Rate       : ₹" + ticket.getSlot().getSlotType().getHourlyRate() + "/hr"
                + "\n  TOTAL      : ₹" + String.format("%.2f", totalAmount)
                + "\n====================";
    }
}
