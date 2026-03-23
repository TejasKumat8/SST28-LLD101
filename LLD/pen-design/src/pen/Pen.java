package pen;

// Abstract base class for all pen types.
// Holds the common attributes every pen has.
public abstract class Pen {

    protected String brand;
    protected PenType type;
    protected PenState state;
    protected int inkLevel; // how much ink is left (0-100)

    public Pen(String brand, PenType type, int inkLevel) {
        this.brand = brand;
        this.type = type;
        this.inkLevel = inkLevel;
        this.state = PenState.NEW; // every pen starts as NEW (cap on)
    }

    // Opens/uncaps the pen so it is ready to write
    public void start() {
        if (state == PenState.EMPTY) {
            System.out.println("[" + brand + "] Cannot start – pen is empty.");
            return;
        }
        if (state == PenState.OPEN) {
            System.out.println("[" + brand + "] Pen is already open.");
            return;
        }
        state = PenState.OPEN;
        System.out.println("[" + brand + "] Pen opened. Ready to write. Ink: " + inkLevel + "%");
    }

    // Writes the given text (consumes ink)
    public void write(String text) {
        if (state != PenState.OPEN) {
            System.out.println("[" + brand + "] Cannot write – pen is not open (current state: " + state + ").");
            return;
        }
        if (inkLevel <= 0) {
            state = PenState.EMPTY;
            System.out.println("[" + brand + "] Pen is out of ink!");
            return;
        }
        // each character costs 1 unit of ink (simplified assumption)
        int inkUsed = Math.min(text.length(), inkLevel);
        inkLevel -= inkUsed;
        System.out.println("[" + brand + "] Writing: \"" + text + "\" | Ink left: " + inkLevel + "%");

        if (inkLevel == 0) {
            state = PenState.EMPTY;
            System.out.println("[" + brand + "] Ink just ran out!");
        }
    }

    // Caps/closes the pen
    public void close() {
        if (state == PenState.CLOSED || state == PenState.NEW) {
            System.out.println("[" + brand + "] Pen is already closed.");
            return;
        }
        state = PenState.CLOSED;
        System.out.println("[" + brand + "] Pen closed.");
    }

    // Getters
    public String getBrand() {
        return brand;
    }

    public PenType getType() {
        return type;
    }

    public PenState getState() {
        return state;
    }

    public int getInkLevel() {
        return inkLevel;
    }
}
