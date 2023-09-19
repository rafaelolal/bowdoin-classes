package Project1;

import java.util.Random;

public class CoinStrip {
    private Coin[] strip;
    private int boardLength = 1;
    private int trailingSpaces = 0;

    static final private int MAX_RANDOM_DISTANCE = 6;

    public CoinStrip(int length) {
        this.strip = new Coin[length];
        Random rand = new Random();

        for (int i = this.strip.length - 1; i >= 0; i--) {
            int toNext = rand.nextInt(MAX_RANDOM_DISTANCE) + 1;
            boardLength += toNext;
            this.strip[i] = new Coin(i, toNext);
        }

        // adding one to toNext of last coin to "simulate" a ghost coin after it just
        // after the end of the board
        this.strip[this.strip.length - 1].addToNext(1);
    }

    public boolean moveCoin(int index, int distance) {
        if (!isMoveLegal(index, distance)) {
            return false;
        }

        this.strip[index].subToNext(distance);

        if (index != 0) {
            this.strip[index - 1].addToNext(distance);
        } else {
            trailingSpaces += distance;
        }

        return true;
    }

    public boolean isMoveLegal(int index, int distance) {
        return distance < this.strip[index].getToNext();
    }

    public boolean isGameOver() {
        for (Coin c : this.strip) {
            if (c.getToNext() != 1) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("_ ".repeat(boardLength));

        // multiplying by two because the repeat method above is done on two characters
        int currentPos = trailingSpaces * 2;
        for (Coin currentCoin : this.strip) {
            str.setCharAt(currentPos, (char) (currentCoin.getIndex() + '0'));
            currentPos += currentCoin.getToNext() * 2;
        }

        return str.toString();
    }
}
