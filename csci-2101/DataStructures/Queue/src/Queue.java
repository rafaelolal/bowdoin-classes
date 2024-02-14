public class Queue<T> {
    DoublyLinkedList<T> list;

    public Queue() {
        list = new DoublyLinkedList<>();
    }

    public void enqueue(T element) {
        list.add(element);
    }

    public T dequeue() {
        return list.remove(0);
    }

    public T peakHead() {
        return list.get(0);
    }

    public T peakTail() {
        return list.get(list.size() - 1);
    }
}