// linear probing

import java.util.NoSuchElementException;

public class HashSet<T> {
    private Object[] data;
    private int size;

    public HashSet(int capacity) {
        data = new Object[capacity];
        size = 0;
        // create a `minimumCapacity` field? if `capacity` is initially set to 10, and I
        // add the first element it will resize, which is not expected behavior
    }

    public void add(T element) {
        if (contains(element)) {
            return;
        }

        data = addToArr(data, element);
        size++;
    }

    // do not have to check what class `element` actually is because
    // this private method is only used internally, and ik exactly what will be
    // passed to it
    private Object[] addToArr(Object[] arr, Object element) {
        double resizeFactor = getResizeFactor(arr);
        if (resizeFactor != 1) {
            arr = resize(arr, resizeFactor);
        }

        int hashValue = compress(element.hashCode(), arr.length);
        int i = 0;
        // does not have to be `i < arr.length` because I checked the capacity above
        while (true) { // rotating to find an empty spot
            int rotatedI = (hashValue + i) % arr.length;
            i++;

            if (rotatedI < 0) {
                continue;
            }

            if (arr[rotatedI] == null || arr[rotatedI].getClass() == Dummy.class) {
                arr[rotatedI] = element;
                return arr;
            }
        }
    }

    private double getResizeFactor(Object[] arr) {
        int arrCapacity = arr.length;
        if (size + 1 < arrCapacity * 0.3) {
            return 0.6; // new size/capacity ratio is 0.5
        } else if (size + 1 > arrCapacity * 0.8) {
            return 2;
        }

        return 1;
    }

    private Object[] resize(Object[] arr, double resizeFactor) {
        Object[] newData = new Object[(int) (arr.length * resizeFactor)];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                int hash;
                // instanceOf was giving me a "cannot safely cast error"
                if (arr[i].getClass() == Dummy.class) {
                    hash = ((Dummy<T>) arr[i]).getData().hashCode();
                } else {
                    hash = arr[i].hashCode();
                }

                // initially, I was simply doing newData[newHashValue] = data[i]
                // but I noticed I cannot do that because of collisions. I must apply the linear
                // proving algorithm again. That is why I created the addToArr method
                newData = addToArr(newData, arr[i]);
            }
        }

        return newData;
    }

    public boolean contains(T element) {
        int hashValue = compress(element.hashCode(), data.length);
        int i = 0;
        while (i < data.length) {
            int rotatedI = (hashValue + i) % data.length;
            i++;

            if (rotatedI < 0) {
                continue;
            }

            if (data[rotatedI] == null) {
                return false;
            } else if (data[rotatedI].equals(element)) { // not needed but allows to stop quicker
                return true;
            }
        }

        return false;
    }

    public void remove(T element) {
        double resizeFactor = getResizeFactor(data);
        if (resizeFactor != 1) {
            data = resize(data, resizeFactor);
        }

        int hashValue = compress(element.hashCode(), data.length);
        int i = 0;
        while (i < data.length) {
            int rotatedI = (hashValue + i) % data.length;
            i++;

            if (rotatedI < 0) {
                continue;
            }

            if (data[rotatedI] != null && data[rotatedI].equals(element)) {
                data[rotatedI] = new Dummy<T>(element);
                size--;
                return;
            }
        }
        throw new NoSuchElementException();
    }

    private int compress(int hash, int numSlots) {
        return hash % numSlots;
    }

    public int size() {
        return size;
    }

    public int getLength() {
        return data.length;
    }

    public static final class Dummy<T> {
        private T data;

        public Dummy(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }
}
