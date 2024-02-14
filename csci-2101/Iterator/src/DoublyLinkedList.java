import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> extends AbstractSimpleList<E> {

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
		if (head == null || index == 0) {
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

	@Override
	public Iterator<E> iterator() {
		return new DoublyLinkedListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new DoublyLinkedListListIterator();
	}

	private class DoublyLinkedListIterator implements Iterator<E> {

		/** Tracks a bookmark -- or position -- into the List */
		private ListNode currentNode;

		/**
		 * Constructs a SALI, initially at the beginning of the list
		 */
		public DoublyLinkedListIterator() {
			currentNode = head;
		}

		/**
		 * Returns `true` iff the list contains more elements
		 */
		@Override
		public boolean hasNext() {
			return currentNode != null;
		}

		/**
		 * Returns the next element pointed to by this iterator.
		 */
		@Override
		public E next() {
			E element = currentNode.data;
			currentNode = currentNode.next;
			return element;
		}
	}

	private class DoublyLinkedListListIterator implements ListIterator<E> {

		private ListNode right;
		private int rightIndex;
		private ListNode left;
		private int leftIndex;
		private ListNode lastRetrieved;

		public DoublyLinkedListListIterator() {
			right = head;
			rightIndex = 0;
			left = null;
			leftIndex = -1;
			lastRetrieved = null;
		}

		/**
		 * Returns `true` iff the list contains more elements
		 */
		@Override
		public boolean hasNext() {
			return rightIndex < size;
		}

		/**
		 * Returns the next element pointed to by this iterator.
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			lastRetrieved = right;
			right = right.next;

			if (left != null) {
				left = left.next;
			} else {
				left = right.previous;
			}

			rightIndex++;
			leftIndex++;

			return lastRetrieved.data;
		}

		@Override
		public int nextIndex() {
			if (hasNext()) {
				return rightIndex;
			}

			return size;
		}

		@Override
		public boolean hasPrevious() {
			return rightIndex >= 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			E element = left.data;

			if (right != null) {
				right = right.previous;
			} else {
				right = left.next;
			}

			left = left.previous;

			rightIndex--;
			leftIndex--;

			return element;
		}

		@Override
		public int previousIndex() {
			if (hasPrevious()) {
				return leftIndex;
			}

			return -1;
		}

		@Override
		public void set(E element) {
			lastRetrieved.data = element;
		}

		@Override
		public void add(E element) {
			ListNode newNode = new ListNode(element, right, left);
			right.previous = newNode;
			left.next = newNode;
			left = newNode;
			rightIndex++;
			leftIndex++;
		}

		@Override
		public void remove() {
			lastRetrieved.previous.next = lastRetrieved.next;
		}
	}
}
