package pen;

// FountainPen – uses liquid ink from a reservoir, IS refillable.
public class FountainPen extends Pen implements Refillable {

    public FountainPen(String brand, int inkLevel) {
        super(brand, PenType.FOUNTAIN_PEN, inkLevel);
    }

    @Override
    public void refill(int amount) {
        if (amount <= 0) {
            System.out.println("[" + brand + "] Refill amount must be positive.");
            return;
        }
        inkLevel = Math.min(100, inkLevel + amount);
        if (state == PenState.EMPTY) {
            state = PenState.CLOSED;
        }
        System.out.println("[" + brand + "] Fountain pen refilled. Ink: " + inkLevel + "%");
    }
}
