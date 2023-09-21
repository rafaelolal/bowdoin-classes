package Project1;

import java.util.Random;

/*
 * Rafael Almeida
 * CSCI 2101 C
 * 09/20/2023
 * Project 1: Silver Dollar Game
 */
public class CoinStrip {
    private Coin[] coins;
    private int boardLength = 0;
    private int trailingSpaces = 0;

    static final private int MAX_RANDOM_DISTANCE = 6;

    public CoinStrip(int length) {
        this.coins = new Coin[length];
        Random rand = new Random();

        // creating Coin objects and determining final board length
        // since coins array does not directly represent the board
        for (int i = 0; i < this.coins.length; i++) {
            int untilNext = rand.nextInt(MAX_RANDOM_DISTANCE) + 1;
            boardLength += untilNext;
            this.coins[i] = new Coin(i, untilNext);
        }
    }

    /*
     * Movement is done by reducing untilNext and, if present, increasing untilNext
     * of coin to the left
     */
    public boolean moveCoin(int index, int distance) {
        if (!isMoveLegal(index, distance)) {
            return false;
        }

        this.coins[index].subtractUntilNext(distance);

        if (index != this.coins.length - 1) {
            this.coins[index + 1].addUntilNext(distance);
        } else {
            trailingSpaces += distance;
        }

        return true;
    }

    /*
     * Movement is legal if it is rightward and is within limit of the next coin
     */
    public boolean isMoveLegal(int index, int distance) {
        return distance > 0 && distance < this.coins[index].getUntilNext();
    }

    /*
     * Game is over when all coins touch
     */
    public boolean isGameOver() {
        for (Coin c : this.coins) {
            if (c.getUntilNext() != 1) {
                return false;
            }
        }
        return true;
    }

    /*
     * Builds board right to left
     */
    public String toString() {
        String[] board = "_".repeat(this.boardLength).split("");

        // starting at the right, ensuring I account for trailing spaces
        // that arise from moving the last coin
        int currentPos = this.boardLength - 1 - this.trailingSpaces;
        for (int i = this.coins.length - 1; i >= 0; i--) {
            board[currentPos] = String.valueOf(this.coins[i].getIndex());
            currentPos -= this.coins[i].getUntilNext();
        }

        return String.join(" ", board);
    }
}
