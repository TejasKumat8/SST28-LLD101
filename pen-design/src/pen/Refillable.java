package pen;

// Only pens that can be refilled implement this interface.
// For example: FountainPen and some GelPens can be refilled,
// but a BallPen typically cannot.
public interface Refillable {
    void refill(int amount);
}
