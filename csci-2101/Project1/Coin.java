package Project1;

public class Coin {
    private int index;
    private int toNext;

    public Coin(int index, int toNext) {
        this.index = index;
        this.toNext = toNext;
    }

    public int getIndex() {
        return index;
    }

    public int getToNext() {
        return toNext;
    }

    public void addToNext(int add) {
        this.toNext += add;
    }

    public void subToNext(int sub) {
        this.toNext -= sub;
    }
}
