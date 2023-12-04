package WorldOfLoot;

import java.util.HashMap;

public class MagicContainer<T> implements Putable<T>, Openable {
    private String description;

    private boolean isOpen = true;

    private HashMap<T, Integer> content = new HashMap<>();

    public MagicContainer(String description) {
        this.description = description;
    }

    public boolean put(T item, int value) {
        if (isOpen) {
            content.put(item, Integer.valueOf(value));
        }

        if (!isOpen) {
            System.out.println("Cannot put into closed container!");
        }

        return isOpen;
    }

    public boolean open() {
        isOpen = true;
        return true;
    }

    public boolean close() {
        isOpen = false;
        return true;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("Desc: " + this.description + "\n");
        s.append("Status: " + (isOpen ? "open" : "closed") + "\n");

        if (isOpen) {
            int totalValue = 0;
            for (HashMap.Entry<T, Integer> entry : content.entrySet()) {
                s.append("\t" + entry.getKey().toString() + " | value: " + entry.getValue() + "\n");
                totalValue = entry.getValue();
            }
            s.append("Total value: " + totalValue);
        }

        return s.toString();
    }
}
