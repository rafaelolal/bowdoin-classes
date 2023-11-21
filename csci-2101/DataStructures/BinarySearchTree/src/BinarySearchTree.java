import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {

    private BinaryTree<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public BinaryTree<T> getRoot() {
        return root;
    }

    public BinaryTree<T> locate(BinaryTree<T> curNode, T target) {
        // this node is the target
        if (curNode.getData().equals(target)) {
            return curNode;
        }

        // deciding which child tree may contain the target
        BinaryTree<T> child;
        if (target.compareTo(curNode.getData()) < 0) {
            child = curNode.getLeft();
        } else {
            child = curNode.getRight();
        }

        if (child == null) {
            return curNode;
        }

        // moving on to that target
        return locate(child, target);
    }

    public void add(T target) {
        BinaryTree<T> newNode = new BinaryTree<T>(target);
        if (root == null) {
            root = newNode;
            return;
        }

        BinaryTree<T> insertLocation = locate(root, target);
        if (target.compareTo(insertLocation.getData()) < 0) {
            insertLocation.setLeft(newNode);
        } else if (target.compareTo(insertLocation.getData()) > 0) {
            insertLocation.setRight(newNode);
        } else {
            // looking for successor: smallest element on the right subtree
            // if no right subtree present, put newNode on the right
            if (insertLocation.getRight() != null) {
                insertLocation = successor(insertLocation);
                insertLocation.setLeft(newNode);
            } else {
                insertLocation.setRight(newNode);
            }

        }
    }

    public boolean contains(T target) {
        if (root == null) {
            return false;
        }

        BinaryTree<T> node = locate(root, target);
        return node.getData().equals(target);
    }

    private BinaryTree<T> successor(BinaryTree<T> root) {
        BinaryTree<T> curNode = root.getRight();
        while (curNode.getLeft() != null) {
            curNode = curNode.getLeft();
        }

        return curNode;
    }

    public String toString() {
        String str = "[";
        for (T data : this) {
            str += data + ", ";
        }

        if (str.length() == 1) {
            str += "]";
            return str;
        }

        // removing trailing ", " and adding "]"
        return str.substring(0, str.length() - 2) + "]";
    }

    public Iterator<T> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<T> {
        Stack<BinaryTree<T>> stack;

        public InOrderIterator() {
            stack = new Stack<>();
            BinaryTree<T> curTree = root;
            while (curTree != null) {
                stack.push(curTree);
                curTree = curTree.getLeft();
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            BinaryTree<T> toReturn = stack.pop();

            if (toReturn.getRight() != null) {
                BinaryTree<T> curTree = toReturn.getRight();
                while (curTree != null) {
                    stack.push(curTree);
                    curTree = curTree.getLeft();
                }
            }

            return toReturn.getData();
        }
    }
}
