package snl;

// A snake bites you at its head (higher cell) and drops you to its tail (lower cell).
public class Snake {
    private final int head; // higher position — player lands here and goes DOWN
    private final int tail; // lower position — player lands here after the bite

    public Snake(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return "Snake [head=" + head + " -> tail=" + tail + "]";
    }
}
