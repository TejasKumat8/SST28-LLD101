package snl;

/**
 * DifficultyLevel controls how punishing snakes are and how helpful ladders
 * are.
 *
 * EASY → short snakes (small drops), tall ladders (big boosts)
 * HARD → long snakes (large drops), short ladders (tiny boosts)
 *
 * This is the "Strategy configuration" — Board reads this enum and adjusts
 * placement parameters accordingly. No if-else chains scattered around;
 * the enum acts as a named constant that carries intent.
 */
public enum DifficultyLevel {
    EASY,
    HARD
}
