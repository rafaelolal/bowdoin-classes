import java.util.List;

public class LinkedList<E> extends AbstractSimpleList<E> implements List<E> {

	/* Add the required instance fields here */
	private ListNode head;
	private int size;

	/* And a constructor */
	public LinkedList() {
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
			size++;
			return true;
		}

		ListNode last = head;
		while (last.next != null) {
			last = last.next;
		}

		last.next = new ListNode(e);
		size++;

		return true;
	}

	@Override
	public E get(int index) {
		ListNode wanted = head;
		for (int i = 0; i < index; i++) {
			wanted = wanted.next;
		}

		return wanted.data;
	}

	@Override
	public E set(int index, E element) {
		ListNode wanted = head;
		for (int i = 0; i < index; i++) {
			wanted = wanted.next;
		}

		E old = wanted.data;
		wanted.data = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		if (index == 0) {
			ListNode newNode = new ListNode(element);
			newNode.next = head;
			head = newNode;
			size++;
			return;
		}

		ListNode wanted = head;
		for (int i = 1; i < index; i++) {
			wanted = wanted.next;
		}

		ListNode next = wanted.next;
		ListNode newNode = new ListNode(element);
		wanted.next = newNode;
		newNode.next = next;
		size++;
	}

	@Override
	public E remove(int index) {
		if (index == 0) {
			E removed = head.data;
			head = head.next;
			size--;
			return removed;
		}

		ListNode wanted = head;
		for (int i = 1; i < index - 1; i++) {
			wanted = wanted.next;
		}

		E removed = wanted.next.data;
		wanted.next = wanted.next.next;
		size--;
		return removed;
	}

	private class ListNode {
		public E data;
		public ListNode next;

		public ListNode(E data) {
			this(data, null);
		}

		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

	}

}
