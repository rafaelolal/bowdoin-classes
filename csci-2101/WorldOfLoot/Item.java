package WorldOfLoot;

public class Item {
    String description;

    public Item(String description) {
        this.description = description;
    }

    public String toString() {
        return this.getClass() + ": " + description;
    }
}
