import java.util.*;

/**
 * OCP (Refactored):
 * HostelFeeCalculator no longer contains a switch-case for room types or
 * if-chains for add-ons. It simply sums contributions from a list of
 * PricingComponent objects.
 *
 * Adding a new room type or add-on requires only a new PricingComponent class
 * and wiring it here – the calculate loop does NOT change.
 */
public class HostelFeeCalculator {

    private final FakeBookingRepo repo;

    public HostelFeeCalculator(FakeBookingRepo repo) { this.repo = repo; }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        // Build pricing components; loop sums them — no switches here
        List<PricingComponent> components = new ArrayList<>();
        components.add(new RoomPricing(req.roomType));
        for (AddOn a : req.addOns) {
            components.add(new AddOnPricing(a));
        }

        Money total = new Money(0.0);
        for (PricingComponent c : components) {
            total = total.plus(c.monthlyContribution());
        }
        return total;
    }
}
