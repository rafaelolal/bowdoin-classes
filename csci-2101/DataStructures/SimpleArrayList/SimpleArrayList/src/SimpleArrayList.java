public class SimpleArrayList<T> extends AbstractSimpleList<T> {
    private T[] array;
    private int size;

    public SimpleArrayList() {
        this.array = (T[]) new Object[16];
        this.size = 0;
    }

    public SimpleArrayList(T[] array) {
        this.array = array;
        this.size = array.length;
    }

    public SimpleArrayList(int capacity) {
        this.array = (T[]) new Object[capacity];
        this.size = 0;
    }

    private void ensureCapacity(int newSize) {
        double resizeFactor;
        if (newSize <= (int) (this.array.length * 0.5)) {
            resizeFactor = 0.75;
        } else if (newSize > this.array.length) {
            resizeFactor = 1.5;
        } else {
            return;
        }

        T[] newArray = (T[]) new Object[(int) Math.ceil(this.size * resizeFactor)];
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.array[i];
        }
        this.array = newArray;
    }

    public T set(int index, T element) {
        T removed = this.array[index];
        this.array[index] = element;
        return removed;
    }

    public T get(int index) {
        return this.array[index];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T remove(int index) {
        ensureCapacity(--size);

        T removed = this.array[index];
        for (int i = this.size - 1; i <= index; i--) {
            this.array[i - 1] = this.array[i];
        }
        this.array[this.size - 1] = null;
        return removed;
    }

    public void add(int index, T element) {
        ensureCapacity(size + 1);

        T removed = set(index, element);
        size++;
        for (int i = index + 1; i < this.size; i++) {
            removed = set(i, removed);
        }
    }

    public boolean add(T element) {
        ensureCapacity(size + 1);

        this.array[size++] = element;
        return true;
    }

    public int size() {
        return this.size;
    }

}
