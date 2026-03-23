package pen;

// GelPen – uses water-based gel ink, IS refillable.
public class GelPen extends Pen implements Refillable {

    public GelPen(String brand, int inkLevel) {
        super(brand, PenType.GEL_PEN, inkLevel);
    }

    @Override
    public void refill(int amount) {
        if (amount <= 0) {
            System.out.println("[" + brand + "] Refill amount must be positive.");
            return;
        }
        inkLevel = Math.min(100, inkLevel + amount);
        // After a refill the pen is usable again (not EMPTY)
        if (state == PenState.EMPTY) {
            state = PenState.CLOSED;
        }
        System.out.println("[" + brand + "] Gel pen refilled. Ink: " + inkLevel + "%");
    }
}
