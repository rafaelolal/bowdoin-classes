import java.util.NoSuchElementException;
import java.util.Stack;

public class RecursiveLinkedListStarter<E> extends AbstractSimpleList<E> {

    // same basic setup as our non-recursive LL
    private ListNode front;

    private int size;

    public void reverse() {
        Stack<ListNode> s = new Stack<>();

        ListNode cur = front;
        while (cur != null) {
            s.push(cur);
            cur = cur.next;
        }

        front = s.pop();
        cur = front;
        while (s.size() != 0) {
            cur.next = s.pop();
            cur = cur.next;
        }

        cur.next = null;
    }

    public void replace(E search, E replace) {
        front.replace(search, replace);
    }

    @Override
    public int size() {
        // we _can_ use recursion to handle this...but it creates an actively worse
        // experience by turning an O(1) operation into O(n) (for the list traversal) so
        // I'm not inclined to do that :)
        return size;
    }

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public boolean add(E e) {

        // if the front of the list is null, we've got to update the front reference
        // that requires us to handle it here, because a null front means we have no
        // `ListNode` references to call a method on
        if (null == front) {
            front = new ListNode(e);
        }

        // otherwise, recursive call. basically, we're telling the first ListNode, "go
        // figure out how to add another element at the end of the list"
        else {
            front.add(e);
        }

        // either way, need to increment size
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        return front.get(index);
    }

    @Override
    public E set(int index, E element) {
        return front.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        front.add(index, element);
    }

    @Override
    public E remove(int index) {
        return front.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        ListNode current = front;
        while (current != null) {
            str.append(current.data);
            str.append(", ");
            current = current.next;
        }

        if (str.length() > 1) {
            str.replace(str.length() - 2, str.length(), "");
        }

        str.append("]");
        return str.toString();
    }

    private class ListNode {

        private E data;

        private ListNode next;

        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        public ListNode(E data) {
            this(data, null);
        }

        public void replace(E search, E replace) {
            if (data == search) {
                data = replace;
            }

            if (next == null) {
                return;
            }

            next.replace(search, replace);
        }

        public void add(E e) {

            // add an element to the end of the LinkedList, by recursively following the
            // chain of ListNodes

            if (null == next) {
                // base case -- we've hit the end of the list, so we can link in a new node here
                this.next = new ListNode(e);
            } else {
                // recursive case -- still not hit the end of the chain, so we will continue to
                // walk towards it
                this.next.add(e);
            }

        }

        public void add(int index, E e) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException(index);
            } else if (index == 1 || index == 0) {
                ListNode newNode = new ListNode(e);
                // index == 0
                if (this == front) {
                    newNode.next = this;
                    front = newNode;
                }
                // index == 1
                else {
                    newNode.next = this.next;
                    this.next = newNode;
                }
                size++;
                return;
            }
            this.next.add(index - 1, e);
        }

        public E get(int index) {
            if (0 == index) {
                return this.data;
            } else if (this.next == null) {
                throw new NoSuchElementException();
            }

            return this.next.get(index - 1);
        }

        public E set(int index, E element) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException(index);
            } else if (0 == index) {
                E removed = this.data;
                this.data = element;
                return removed;
            }

            return this.next.set(index - 1, element);
        }

        public E remove(int index) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException(index);
            } else if (1 == index || 0 == index) {
                E removed;
                // index == 0 I HATE THIS!
                if (this == front && index == 0) {
                    removed = this.data;
                    front = this.next;
                }
                // index == 1
                else {
                    removed = this.next.data;
                    this.next = this.next.next;
                }
                size--;
                return removed;
            }
            return this.next.remove(index - 1);
        }
    }
}