import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class BinaryTree<E> {
    private E data;
    private BinaryTree<E> parent;
    private BinaryTree<E> left;
    private BinaryTree<E> right;
    private int size;

    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        this.data = data;
        size = 0;
        parent = null;
        setLeft(left);
        setRight(right);
    }

    public BinaryTree(E data) {
        this(data, null, null);
    }

    private class InOrderIterator implements Iterator<E> {
        Stack<BinaryTree<E>> stack;

        public InOrderIterator() {
            stack = new Stack<>();
            BinaryTree<E> curTree = BinaryTree.this;
            while (curTree != null) {
                stack.push(curTree);
                curTree = curTree.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            BinaryTree<E> toReturn = stack.pop();

            if (toReturn.right != null) {
                BinaryTree<E> curTree = toReturn.right;
                while (curTree != null) {
                    stack.push(curTree);
                    curTree = curTree.left;
                }
            }

            return toReturn.data;
        }
    }

    public Iterator<E> inOrderIterator() {
        return new InOrderIterator();
    }

    private class PreOrderIterator implements Iterator<E> {
        Stack<BinaryTree<E>> stack;

        public PreOrderIterator() {
            stack = new Stack<>();
            stack.push(left.parent);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            BinaryTree<E> toReturn = stack.pop();

            if (toReturn.right != null) {
                stack.push(toReturn.right);
            }

            if (toReturn.left != null) {
                stack.push(toReturn.left);
            }

            return toReturn.data;
        }
    }

    public Iterator<E> PreOrderIterator() {
        return new PreOrderIterator();
    }

    private class PostOrderIterator implements Iterator<E> {
        Stack<BinaryTree<E>> stack;
        BinaryTree<E> lastReturned;

        public PostOrderIterator() {
            stack = new Stack<>();
            BinaryTree<E> curTree = BinaryTree.this;
            while (curTree != null) {
                stack.push(curTree);
                curTree = curTree.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            BinaryTree<E> toReturn = stack.peek();

            if (toReturn.left == lastReturned) {
                BinaryTree<E> curTree = toReturn.right;
                while (curTree != null) {
                    stack.push(curTree);
                    curTree = curTree.left;
                }
            }

            toReturn = stack.pop();
            lastReturned = toReturn;
            return toReturn.data;
        }
    }

    public Iterator<E> PostOrderIterator() {
        return new PostOrderIterator();
    }

    public E setData(E element) {
        E oldValue = data;
        data = element;
        return oldValue;
    }

    public void setLeft(BinaryTree<E> left) {
        if (this.left != null) {
            this.left.parent = null;
        }
        this.left = left;
        if (this.left != null) {
            this.left.parent = this;
        }
    }

    public void setRight(BinaryTree<E> right) {
        if (this.right != null) {
            this.right.parent = null;
        }
        this.right = right;
        if (this.right != null) {
            this.right.parent = this;
        }
    }

    public E getData() {
        return data;
    }

    public BinaryTree<E> getLeft() {
        return left;
    }

    public BinaryTree<E> getRight() {
        return right;
    }

    public BinaryTree<E> getParent() {
        return parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public BinaryTree<E> findRoot() {
        if (isRoot()) {
            return this;
        }

        return this.parent.findRoot();
    }

    public int findDepth() {
        if (this.parent == null) {
            return 0;
        }

        return 1 + this.parent.findDepth();
    }

    public int findHeight() {
        if (this.left == null && this.right == null) {
            return 0;
        }

        int leftDepth = 0;
        if (this.left != null) {
            leftDepth = 1 + this.left.findHeight();
        }

        int rightDepth = 0;
        if (this.right != null) {
            rightDepth = 1 + this.right.findHeight();
        }

        return leftDepth > rightDepth ? leftDepth : rightDepth;
    }

    public boolean isFull() {
        if (left == null && right == null) {
            return true;
        } else if ((left == null && right != null) || (left != null && right == null)) {
            return false;
        }

        boolean isLeftFull = left.isFull();
        boolean isRightFull = right.isFull();

        return isLeftFull && isRightFull;
    }

    public boolean contains(E element) {
        if (data == element) {
            return true;
        } else if (this.left == null && this.right == null) {
            return false;
        }

        return (this.left != null && this.left.contains(element))
                || (this.right != null && this.right.contains(element));
    }

    // map instead of set
    public boolean weakEquals(BinaryTree<E> other) {
        // Create sets to store the values of both trees
        Map<E, Integer> thisMap = new HashMap<>();
        Map<E, Integer> otherMap = new HashMap<>();

        // Populate the Maps with the values of this tree and the other tree
        populateMap(this, thisMap);
        populateMap(other, otherMap);

        // Check if the sets are equal, meaning both trees have the same values
        return thisMap.equals(otherMap);
    }

    private void populateMap(BinaryTree<E> node, Map<E, Integer> map) {
        if (node == null) {
            return;
        }

        map.put(node.data, map.containsKey(node.data) ? map.get(node.data) + 1 : 1);

        populateMap(node.left, map);
        populateMap(node.right, map);
    }

    public String toString() {
        Deque<BinaryTree<E>> deque = new LinkedList<>();
        deque.push(this);

        String str = "[";

        while (!deque.isEmpty()) {
            BinaryTree<E> removed = deque.pollFirst();
            if (removed == null) {
                continue;
            }
            deque.offerLast(removed.left);
            deque.offerLast(removed.right);
            str += removed.data + ", ";
        }

        return str + "]";
    }
}