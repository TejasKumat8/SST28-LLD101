package snl;

// A ladder boosts you from its start (lower cell) to its end (higher cell).
public class Ladder {
    private final int start; // lower position — player lands here and goes UP
    private final int end; // higher position — player lands here via the ladder

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Ladder [start=" + start + " -> end=" + end + "]";
    }
}
