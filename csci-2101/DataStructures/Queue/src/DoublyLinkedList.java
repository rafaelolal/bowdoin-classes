import java.util.List;

public class DoublyLinkedList<E> extends AbstractSimpleList<E> implements List<E> {

	/* Add the required instance fields here */
	private ListNode head;
	private ListNode tail;
	private int size;

	/* And a constructor */
	public DoublyLinkedList() {
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
	public boolean add(E e) {
		if (isEmpty()) {
			head = new ListNode(e);
			tail = head;
			size++;
			return true;
		}

		ListNode newNode = new ListNode(e, null, tail);
		tail.next = newNode;
		tail = newNode;
		size++;
		return true;
	}

	@Override
	public E set(int index, E element) {
		ListNode current = getNode(index);

		E old = current.data;
		current.data = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		if (index == 0) {
			ListNode newNode = new ListNode(element, head, null);
			head.previous = newNode;
			head = newNode;
			size++;
			return;
		}

		ListNode current = getNode(index);
		ListNode newNode = new ListNode(element, current, current.previous);
		current.previous.next = newNode;
		current.previous = newNode;
		size++;
	}

	@Override
	public E remove(int index) {
		if (index == 0) {
			E removed = head.data;
			if (size == 1) {
				head = null;
				tail = null;
			} else {
				head = head.next;
				head.previous = null;
			}
			size--;
			return removed;
		}

		ListNode wanted = getNode(index);
		E removed = wanted.data;
		wanted.previous.next = wanted.next;
		if (wanted.next != null) {
			wanted.next.previous = wanted.previous;
		}

		size--;
		return removed;
	}

	@Override
	public E get(int index) {
		return getNode(index).data;
	}

	private ListNode getNode(int index) {
		if (index <= size / 2) {
			return headTo(index);
		} else {
			return tailTo(index);
		}
	}

	private ListNode headTo(int index) {
		ListNode current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current;
	}

	private ListNode tailTo(int index) {
		ListNode current = tail;
		for (int i = size - 1; i > index; i--) {
			current = current.previous;
		}

		return current;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		ListNode current = head;
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

	private class ListNode {
		private E data;
		private ListNode next;
		private ListNode previous;

		private ListNode(E data) {
			this(data, null, null);
		}

		private ListNode(E data, ListNode next, ListNode previous) {
			this.data = data;
			this.next = next;
			this.previous = previous;
		}

	}

}
