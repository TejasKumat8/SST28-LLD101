package pen;

// BallPen – uses oil-based ink, NOT refillable.
// Once ink runs out, pen is discarded.
public class BallPen extends Pen {

    public BallPen(String brand, int inkLevel) {
        super(brand, PenType.BALL_PEN, inkLevel);
    }
}
