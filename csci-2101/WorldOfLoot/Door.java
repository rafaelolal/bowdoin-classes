package WorldOfLoot;

public class Door implements Openable {
    boolean isLocked;
    boolean isOpen = false;

    public Door(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean open() {
        if (!isLocked) {
            isOpen = true;
        }

        return isLocked;
    }

    public boolean close() {
        isOpen = false;
        return true;
    }
}
