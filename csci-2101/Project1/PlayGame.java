package Project1;

import java.util.Scanner;

public class PlayGame {
    public static void main(String[] args) {
        // game state vars
        int turn = 0;
        Scanner scan = new Scanner(System.in);

        // initiating game
        System.out.print("Welcome to the Silver Dollar Game!\nEnter the number of coins: ");
        int stripLength = scan.nextInt();

        CoinStrip strip = new CoinStrip(stripLength);

        System.out.println("Game begins now!\nEnter coin to be moved and distance separated by a space.");

        while (true) {
            System.out.println(strip.toString());

            int player = turn % 2 + 1;

            if (strip.isGameOver()) {
                // subtracting one because if the game is over, it was the last player who won
                System.out.printf("Player %d wins!%n", player - 1);
                break;
            }

            while (true) {
                System.out.printf("Player %d: Enter your move: ", player);
                int index = scan.nextInt();
                int distance = scan.nextInt();

                boolean successfullyMoved = strip.moveCoin(index, distance);
                if (!successfullyMoved) {
                    System.out.println("Illegal move! Try again.");
                    continue;
                }

                break;
            }

            turn++;
        }

        scan.close();
    }
}
