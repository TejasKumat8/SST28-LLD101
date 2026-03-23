package snl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board size n (board will be n x n): ");
        int n = sc.nextInt();

        System.out.print("Enter number of players: ");
        int x = sc.nextInt();

        System.out.print("Enter difficulty level (EASY / HARD): ");
        String diffInput = sc.next().toUpperCase();
        DifficultyLevel difficulty;
        try {
            difficulty = DifficultyLevel.valueOf(diffInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid difficulty. Defaulting to EASY.");
            difficulty = DifficultyLevel.EASY;
        }

        List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            System.out.print("Enter name of player " + i + ": ");
            playerNames.add(sc.next());
        }

        sc.close();

        Game game = new Game(n, playerNames, difficulty);
        game.start();
    }
}
