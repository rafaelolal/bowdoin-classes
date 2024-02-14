import java.util.List;

public class GenericHashSet<T> {

    static private final int DEFAULT_CAPACITY = 20;

    private Object[] items;

    private int numItems;

    public GenericHashSet() {
        items = new Object[DEFAULT_CAPACITY];
        numItems = 0;
    }

    public boolean add(T t) {
        if (numItems >= items.length) {
            rehash();
        }

        int loc = compress(t.hashCode(), items.length);

        while (items[loc] != null) {
            loc = ++loc % items.length;
        }

        items[loc] = t;
        numItems++;

        return true;

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

    public boolean contains(T t) {

        int loc = compress(t.hashCode(), items.length);
        int numTries = 0;

        while (items[loc] != null && !items[loc].equals(t) && numTries < items.length) {
            loc = ++loc % items.length;
            numTries++;
        }

        return t.equals(items[loc]);

    }

    @SuppressWarnings("unchecked")
    private void rehash() {

        Object[] newArray = new Object[2 * items.length];

        for (T t : (T[]) items) {
            if (null != t) {
                int loc = compress(t.hashCode(), newArray.length);

                while (newArray[loc] != null) {
                    loc = ++loc % newArray.length;
                }

                newArray[loc] = t;
            }
        }

        items = newArray;

    }

    static private int compress(int hash, int slots) {
        return Math.abs(hash) % slots;
    }

    public static void main(String[] args) {

        List<String> words = List.of("the", "frogs", "fantastic", "natascha", "ASCII", "wizard", "grape",
                "computer science");

        GenericHashSet<String> hs = new GenericHashSet<String>();

        for (String word : words) {
            hs.add(word);
        }

        for (String word : words) {
            int[] idx = hs.index(word);
            System.out.printf("`%s` found: %b at index %d (expected: %d)\n", word, hs.contains(word), idx[1], idx[0]);
        }

        System.out.println("~~~~~~~~~~~~");

        List<String> notFound = List.of("an", "goldfish", "horse");

        for (String word : notFound) {
            int[] idx = hs.index(word);
            System.out.printf("`%s` found: %b at index %d (expected: %d)\n", word, hs.contains(word), idx[1], idx[0]);
        }

        GenericHashSet<Integer> hsi = new GenericHashSet<>();

        for (int i = 0; i < 10; i++) {
            hsi.add(i);
        }

        for (int i = 5; i < 15; i++) {
            System.out.println(hsi.contains(i));
        }

    }

}