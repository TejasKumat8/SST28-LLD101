package snl;

// Represents one player in the game.
// Each player starts outside the board at position 0.
public class Player {
    private final String name;
    private int position; // 0 = outside board

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        this.position = pos;
    }

    @Override
    public String toString() {
        return name + " (pos=" + position + ")";
    }
}
