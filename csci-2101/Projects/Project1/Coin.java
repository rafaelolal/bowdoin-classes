package Project1;

/*
 * Rafael Almeida
 * CSCI 2101 C
 * 09/20/2023
 * Project 1: Silver Dollar Game
 */
public class Coin {
    private int index;
    private int untilNext; // space between current coin and one to the left or left edge

    public Coin(int index, int toNext) {
        this.index = index;
        this.untilNext = toNext;
    }

    public int getIndex() {
        return index;
    }

    public int getUntilNext() {
        return untilNext;
    }

    public void addUntilNext(int add) {
        this.untilNext += add;
    }

    public void subtractUntilNext(int sub) {
        this.untilNext -= sub;
    }
}
