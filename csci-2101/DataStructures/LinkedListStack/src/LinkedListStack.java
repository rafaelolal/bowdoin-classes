public class LinkedListStack<T> {
    private LinkedList<T> list;

    public LinkedListStack() {
        list = new LinkedList<>();
    }

    public void push(T e) {
        list.add(0, e);
    }

    public T peek() {
        return list.get(0);
    }

    public T pop() {
        return list.remove(0);
    }
}
