package Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop<T extends Item> {
    private String name;
    private List<T> inventory;
    private Map<Person, MagicBag<T>> wishlists;

    public Shop(String name) {
        this.name = name;
        inventory = new ArrayList<>();
        wishlists = new HashMap<>();
    }

    public void addItem(T item) {
        inventory.add(item);
    }

    public MagicBag<T> getWishlist(Person person) {
        return wishlists.get(person);
    }

    public void createWishlist(Person person, String name) {
        wishlists.put(person, new MagicBag<T>(name));
    }

    public void addToWishlist(Person person, T item) {
        wishlists.get(person).addLoot(item);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("Name; %s%n", this.name));
        str.append("+-- Inventory --+\n");
        for (int i = 0; i < this.inventory.size(); i++) {
            str.append(String.format("Item %d: %s%n", i, this.inventory.get(i)));
        }
        return str.toString();
    }
}
