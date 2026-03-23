package snl;

import java.util.Random;

/**
 * Dice models a standard 6-sided die.
 *
 * Why a separate class?
 * - Single Responsibility: only the dice knows how to roll.
 * - Testability: you can swap in a fake/seeded Dice in unit tests.
 * - Extensibility: if you need a 12-sided die later, just change this class.
 */
public class Dice {

    private static final int FACES = 6;
    private final Random random = new Random();

    /**
     * Simulates rolling the die.
     *
     * @return a random integer in [1, 6]
     */
    public int roll() {
        return random.nextInt(FACES) + 1; // nextInt(6) gives 0-5, +1 makes it 1-6
    }
}
