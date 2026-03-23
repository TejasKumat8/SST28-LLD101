package snl;

import java.util.*;

/*
 * Board sets up the n x n grid and randomly places n snakes and n ladders.
 *
 * Two HashMaps make lookups O(1):
 *   snakeMap  : position → Snake  (keyed by snake head)
 *   ladderMap : position → Ladder (keyed by ladder start)
 *
 * Difficulty affects snake length and ladder height:
 *   EASY → short snakes (drop ~25% of board), tall ladders (jump ~50% of board)
 *   HARD → long  snakes (drop ~50% of board), short ladders (jump ~25% of board)
 *
 * No cycle guarantee: we keep a single "occupied" set. A cell added as
 * a snake head/tail or ladder start/end cannot be reused, so a snake tail
 * can never be the start of a ladder or vice versa.
 */
public class Board {

    private final int n; // board dimension (n x n)
    private final int totalCells; // n^2

    private final Map<Integer, Snake> snakeMap = new HashMap<>();
    private final Map<Integer, Ladder> ladderMap = new HashMap<>();

    private final Random random = new Random();

    public Board(int n, DifficultyLevel difficulty) {
        this.n = n;
        this.totalCells = n * n;

        Set<Integer> occupied = new HashSet<>();
        // Position 1 and totalCells are always free (game start/end)
        occupied.add(1);
        occupied.add(totalCells);

        placeSnakes(n, difficulty, occupied);
        placeLadders(n, difficulty, occupied);
    }

    // ---------------------------------------------------------------
    // Snake placement
    // ---------------------------------------------------------------
    private void placeSnakes(int count, DifficultyLevel difficulty, Set<Integer> occupied) {
        int placed = 0;
        int attempts = 0;

        while (placed < count && attempts < 10_000) {
            attempts++;

            // Snake head: anywhere from 2 to (totalCells - 1)
            int head = randomInt(2, totalCells - 1);
            if (occupied.contains(head))
                continue;

            // Decide snake length based on difficulty
            int drop;
            if (difficulty == DifficultyLevel.HARD) {
                drop = randomInt(n / 2, n); // long snake drops ≥ half a row
            } else {
                drop = randomInt(1, n / 2); // short snake drops less than half a row
            }

            int tail = head - drop;
            if (tail < 1)
                continue; // tail must stay on the board
            if (occupied.contains(tail))
                continue;

            snakeMap.put(head, new Snake(head, tail));
            occupied.add(head);
            occupied.add(tail);
            placed++;
        }
    }

    // ---------------------------------------------------------------
    // Ladder placement
    // ---------------------------------------------------------------
    private void placeLadders(int count, DifficultyLevel difficulty, Set<Integer> occupied) {
        int placed = 0;
        int attempts = 0;

        while (placed < count && attempts < 10_000) {
            attempts++;

            // Ladder start: anywhere from 1 to (totalCells - 1)
            int start = randomInt(1, totalCells - 1);
            if (occupied.contains(start))
                continue;

            // Decide ladder height based on difficulty
            int boost;
            if (difficulty == DifficultyLevel.EASY) {
                boost = randomInt(n / 2, n); // tall ladder jumps ≥ half a row
            } else {
                boost = randomInt(1, n / 2); // short ladder doesn't help much
            }

            int end = start + boost;
            if (end > totalCells)
                continue; // end must stay on the board
            if (occupied.contains(end))
                continue;

            ladderMap.put(start, new Ladder(start, end));
            occupied.add(start);
            occupied.add(end);
            placed++;
        }
    }

    // ---------------------------------------------------------------
    // Helpers used by Game
    // ---------------------------------------------------------------
    public int getTotalCells() {
        return totalCells;
    }

    // Returns the snake at this position (null if none)
    public Snake getSnakeAt(int position) {
        return snakeMap.get(position);
    }

    // Returns the ladder at this position (null if none)
    public Ladder getLadderAt(int position) {
        return ladderMap.get(position);
    }

    public Map<Integer, Snake> getSnakeMap() {
        return Collections.unmodifiableMap(snakeMap);
    }

    public Map<Integer, Ladder> getLadderMap() {
        return Collections.unmodifiableMap(ladderMap);
    }

    // ---------------------------------------------------------------
    // Utility
    // ---------------------------------------------------------------
    private int randomInt(int min, int max) {
        if (min > max)
            return min;
        return random.nextInt(max - min + 1) + min;
    }
}
