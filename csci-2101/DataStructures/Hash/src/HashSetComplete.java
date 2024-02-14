import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class HashSetComplete<T> {

    // private static final class Empty {
    // }

    // private static final Empty e = new Empty();
    static private final int DEFAULT_CAPACITY = 20;
    private List<T>[] items;
    private int numItems;
    private double loadFactor;

    public HashSetComplete(Double loadFactor) {
        items = new List[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            items[i] = new LinkedList<>();
        }
        numItems = 0;
        this.loadFactor = loadFactor;
    }

    public HashSetComplete() {
        this(0.5);
    }

    public boolean add(T t) {
        if (numItems >= items.length * loadFactor) {
            rehash();
        }

        int loc = compress(t.hashCode(), items.length);
        items[loc].add(t);
        numItems++;

        return true;

    }

    public boolean remove(T t) {
        int loc = compress(t.hashCode(), items.length);
        for (T item : items[loc]) {
            if (t.equals(item)) {
                items[loc].remove(t);
                numItems--;
                return true;
            }
        }

        throw new NoSuchElementException();
    }

    public int[] index(T t) {
        int expectedLoc = compress(t.hashCode(), items.length), loc = expectedLoc;
        int numTries = 0;
        while (items[loc] != null && !items[loc].equals(t) && numTries < items.length) {
            loc = ++loc % items.length;
            numTries++;
        }

        loc = t.equals(items[loc]) ? loc : -1;

        return new int[] { expectedLoc, loc };
    }

    public boolean contains(Object o) {
        int loc = compress(o.hashCode(), items.length);
        for (T item : items[loc]) {
            if (o.equals(item)) {
                return true;
            }
        }

        return false;
    }

    // TODO: rehash for removing items
    private void rehash() {
        List<T>[] newArray = new List[items.length * 2];
        // TODO: stop doing special loops and loop through the indexes and only create a
        // new LinkedList if you need to put an item there. Add LinkedList on the for
        // loop after the one directly below
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = new LinkedList<>();
        }

        for (List<T> l : items) {
            for (T t : l) {
                int loc = compress(t.hashCode(), newArray.length);
                newArray[loc].add(t);
            }
        }

        items = newArray;
    }

    static private int compress(int hash, int slots) {
        return Math.abs(hash) % slots;
    }

    public static void main(String[] args) {

        List<String> words = List.of("the", "frogs", "fantastic", "natascha", "ASCII", "wizard", "grape",
                "computer science", "1", "2", "3", "4", "5", "6", "7", "8", "9", "11", "22", "13", "14", "15", "16",
                "17", "18", "19", "111");

        HashSetComplete<String> hs = new HashSetComplete<String>();

        for (String word : words) {
            hs.add(word);
        }

        for (String word : words) {
            if (!hs.contains(word)) {
                throw new NoSuchElementException("Word %s expected but not found.".formatted(word));
            }
        }

        System.out.println("All words in the `words` list were successfully found!");

        List<String> notFound = List.of("an", "goldfish", "horse");

        for (String word : notFound) {
            if (hs.contains(word)) {
                throw new IllegalArgumentException("Word %s not expected but found.".formatted(word));
            }
        }

        System.out.println("No missing words accidentally found!");

        List<String> toRemove = List.of("the", "grape", "frogs");
        for (String word : toRemove) {
            hs.remove(word);
        }

        for (String word : toRemove) {
            if (hs.contains(word)) {
                throw new IllegalArgumentException("Word %s was removed but still present in HashSet.".formatted(word));
            }
        }

        System.out.println("No removed words accidentally found!");
    }
}