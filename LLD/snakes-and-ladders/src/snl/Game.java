package snl;

import java.util.*;

/*
 * Game orchestrates the entire flow:
 *  1. Creates the Board (with snakes & ladders)
 *  2. Loops turn-by-turn through active players
 *  3. Applies dice roll → checks snake → checks ladder → checks win
 *  4. Stops when only 1 player is left (everyone else has won)
 */
public class Game {

    private final Board board;
    private final Dice dice;
    private final List<Player> players; // all players
    private final List<Player> winners; // ranked list of winners

    public Game(int n, List<String> playerNames, DifficultyLevel difficulty) {
        this.board = new Board(n, difficulty);
        this.dice = new Dice();
        this.players = new ArrayList<>();
        this.winners = new ArrayList<>();

        for (String name : playerNames) {
            this.players.add(new Player(name));
        }
    }

    public void start() {
        int n = (int) Math.sqrt(board.getTotalCells());
        System.out.println("\n========================================");
        System.out.println("  SNAKES & LADDERS  —  " + n + " x " + n + " board");
        System.out.println("========================================");
        printBoardSummary();
        System.out.println();

        // Active players: those still trying to win
        List<Player> activePlayers = new ArrayList<>(players);

        // Keep playing as long as at least 2 players haven't won
        while (activePlayers.size() > 1) {

            Iterator<Player> it = activePlayers.iterator();

            while (it.hasNext() && activePlayers.size() > 1) {
                Player player = it.next();

                int roll = dice.roll();
                int oldPos = player.getPosition();
                int newPos = oldPos + roll;

                System.out.print(player.getName()
                        + " | dice=" + roll
                        + " | " + oldPos + " → " + newPos);

                // Rule: if newPos exceeds last cell, player stays
                if (newPos > board.getTotalCells()) {
                    System.out.println(
                            " | [OVERSHOOT] cannot go beyond " + board.getTotalCells() + ", stays at " + oldPos);
                    continue;
                }

                player.setPosition(newPos);

                // Check snake at newPos
                Snake snake = board.getSnakeAt(newPos);
                if (snake != null) {
                    player.setPosition(snake.getTail());
                    System.out.print(" | [SNAKE] slid down to " + snake.getTail());
                }

                // Check ladder at newPos
                Ladder ladder = board.getLadderAt(newPos);
                if (ladder != null) {
                    player.setPosition(ladder.getEnd());
                    System.out.print(" | [LADDER] climbed up to " + ladder.getEnd());
                }

                System.out.println(" | final pos: " + player.getPosition());

                // Check win condition
                if (player.getPosition() == board.getTotalCells()) {
                    winners.add(player);
                    System.out.println("*** " + player.getName()
                            + " WINS! (Rank #" + winners.size() + ") ***");
                    it.remove(); // remove from active list
                }
            }
        }

        // The last remaining player is the loser / last place
        System.out.println("\n========================================");
        System.out.println("           FINAL RANKINGS");
        System.out.println("========================================");
        for (int i = 0; i < winners.size(); i++) {
            System.out.println("  #" + (i + 1) + "  " + winners.get(i).getName());
        }
        if (!activePlayers.isEmpty()) {
            System.out.println("  (Last) " + activePlayers.get(0).getName());
        }
        System.out.println("========================================\n");
    }

    // ---------------------------------------------------------------
    // Print where all snakes and ladders are
    // ---------------------------------------------------------------
    private void printBoardSummary() {
        System.out.println("\nSNAKES (" + board.getSnakeMap().size() + "):");
        board.getSnakeMap().values().stream()
                .sorted(Comparator.comparingInt(Snake::getHead))
                .forEach(s -> System.out.println("  Head " + s.getHead() + "  →  Tail " + s.getTail()));

        System.out.println("\nLADDERS (" + board.getLadderMap().size() + "):");
        board.getLadderMap().values().stream()
                .sorted(Comparator.comparingInt(Ladder::getStart))
                .forEach(l -> System.out.println("  Start " + l.getStart() + "  →  End " + l.getEnd()));
    }
}
