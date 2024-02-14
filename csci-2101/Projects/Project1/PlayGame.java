package Project1;

import java.util.Scanner;

/*
 * Rafael Almeida
 * CSCI 2101 C
 * 09/20/2023
 * Project 1: Silver Dollar Game
 */
public class PlayGame {
    public static void main(String[] args) {
        // game state vars
        int turn = 0;
        int player = 1;
        Scanner scan = new Scanner(System.in);

        // initiating game
        System.out.print("Welcome to the Silver Dollar Game!");
        System.out.println("Enter index of coin to be moved and distance separated by a space.");
        System.out.println("Last player to get all coins on the left wins.");

        System.out.print("Enter the number of coins:");
        int stripLength = scan.nextInt();
        CoinStrip strip = new CoinStrip(stripLength);

        System.out.println("Game begins now!");

        // infinite loop until game is done
        while (true) {
            System.out.println(strip.toString());

            if (strip.isGameOver()) {
                System.out.printf("Player %d wins!%n", player);
                break;
            }

            player = turn % 2 + 1;

            // infinite loop until valid move
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
