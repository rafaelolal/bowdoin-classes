package Shop;

import java.util.ArrayList;
import java.util.List;

public class MagicBag<T extends Item> {

    private String name;
    private List<T> loot;

    public MagicBag(String name) {
        this.name = name;
        loot = new ArrayList<T>();
    }

    public boolean addLoot(T what) {
        return this.loot.add(what);
    }

    @Override
    public String toString() {
        return String.format("A MagicBag named %s with contents: %s\n", this.name, this.loot);
    }
}