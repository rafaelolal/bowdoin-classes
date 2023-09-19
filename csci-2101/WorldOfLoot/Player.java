package WorldOfLoot;

import java.util.List;
import java.util.ArrayList;

public class Player {
    String name;
    List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public <T> void get(Putable<T> container, int idx) {
        T item = container.get(idx);

    }

    public <T> void put(Putable<T> container, T item, int value) {
        container.put(item, value);
    }

    public void open(Openable container) {
        container.open();
    }

    public void close(Openable container) {
        container.close();
    }

}
