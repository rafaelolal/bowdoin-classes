package WorldOfLoot;

import java.util.HashMap;

public class OpenMagicContainer<T> implements Putable<T> {

    String description;
    private HashMap<T, Integer> content = new HashMap<>();

    public OpenMagicContainer(String description) {
        this.description = description;
    }

    public boolean put(T item, int value) {
        content.put(item, Integer.valueOf(value));
        return true;
    }
}
