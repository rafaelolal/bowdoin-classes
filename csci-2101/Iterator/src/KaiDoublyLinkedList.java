import java.util.Iterator;

/**
 * A simple doubly-linked list implementation. Performs no error checking (i.e.,
 * assumes that all requested operations are possible).
 *
 * @author Sean Barker
 * @author Kai Presler-Marshall
 */
public class KaiDoublyLinkedList<T> extends AbstractSimpleList<T> {

    // first node in the list
    private ListNode head;
    private ListNode tail;

    // number of nodes
    private int size;

    /**
     * Construct an empty linked list.
     */
    public KaiDoublyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T element) {
        if (head == null) {
            head = new ListNode(element);
            tail = head;
        } else {
            tail.next = new ListNode(element, null, tail);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        int midpoint = size / 2;
        boolean first = index < midpoint;
        ListNode current = first ? head : tail;
        if (first) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }

        return current.data;
    }

    @Override
    public T set(int index, T element) {
        int midpoint = size / 2;
        boolean first = index < midpoint;
        ListNode current = first ? head : tail;
        if (first) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        T oldValue = current.data;
        current.data = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        if (head == null || index == 0) {
            head.previous = new ListNode(element, head);
            head = head.previous;
            if (tail == null) {
                tail = head;
            }
        } else {
            int midpoint = size / 2;
            boolean first = index < midpoint;
            ListNode current = first ? head : tail;
            if (first) {
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                ListNode next = current.next;

                current.next = new ListNode(element, next, current);
                next.previous = current.next;
            } else {
                for (int i = size - 1; i > index; i--) {
                    current = current.previous;
                }
                ListNode previous = current.previous;
                current.previous = new ListNode(element, current, previous);
                previous.next = current.previous;

                if (index == size) {
                    tail = tail.next;
                }
            }
        }
        size++;
    }

    @Override
    public T remove(int index) {
        T oldValue;
        if (index == 0) {
            oldValue = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            }

        } else {
            int midpoint = size / 2;
            boolean first = index < midpoint;
            ListNode current = first ? head : tail;
            if (first) {
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                oldValue = current.next.data;
                current.next = current.next.next;
                current.next.previous = current;

            } else {
                for (int i = size - 1; i > index; i--) {
                    current = current.previous;
                }

                oldValue = current.data;
                current.previous.next = current.next;
                current.next.previous = current.previous;

                if (index == size) {
                    tail = current;
                }
            }
        }
        size--;
        return oldValue;
    }

    @Override
    public String toString() {
        String out = "[";

        ListNode current = head;
        while (current != null) {
            out += current.data;
            if (current.next != null) {
                out += ", ";
            }
            current = current.next;
        }
        out += "]";
        return out;

    }

    public String reverseToString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        ListNode current = tail;
        while (current != null) {
            str.append(current.data);
            str.append(", ");
            current = current.previous;
        }

        if (str.length() > 1) {
            str.replace(str.length() - 2, str.length(), "");
        }

        str.append("]");
        return str.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleLinkedListIterator();
    }

    // @Override
    // public ListIterator<T> listIterator() {
    // return new DoublyLinkedListIterator();
    // }

    /** Your method to return a LI goes here */

    /**
     * SimpleLinkedListIterator is a (simple) iterator for our SimpleLinkedList It
     * allows us to iterate through the elements in our SLL, but does not support
     * the optional remove operation. Nor is it a fail-fast iterator that will
     * object if next() is called multiple times in a row.
     * 
     * This iterator is _functional_ -- it will let us iterate through our
     * LinkedList, and do all of the things that an Iterator is required to do.
     * However, it works rather poorly. Take a look at the slides for a reminder on
     * why if you've forgotten.
     * 
     * @author Kai Presler-Marshall
     */
    private class SimpleLinkedListIterator implements Iterator<T> {

        /** Tracks a bookmark -- or position -- into the List */
        private ListNode current;

        /**
         * Constructs a SALI, initially at the beginning of the list
         */
        public SimpleLinkedListIterator() {
            current = head;
        }

        /**
         * Returns `true` iff the list contains more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element pointed to by this iterator.
         */
        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }

    }

    /** Your ListIterator to go here :) */

    /**
     * A node within the linked list. Each node contains one data element and a
     * reference to the successor node in the list.
     */
    private class ListNode {
        private T data;
        private ListNode next; // null if the last list element
        private ListNode previous; // null if the first list element

        public ListNode(T data, ListNode next) {
            this(data, next, null);
        }

        public ListNode(T data, ListNode next, ListNode previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        public ListNode(T data) {
            this(data, null);
        }
    }

}