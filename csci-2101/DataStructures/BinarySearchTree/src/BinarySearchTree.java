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

    // displacing means making the pointers that point to o point to n
    private void displace(BinaryTree<T> o, BinaryTree<T> n) {
        if (o.getParent() == null) { // if o is root, make n the root
            root = n;

        }
        // o has a parent
        else if (o == o.getParent().getLeft()) { // if o is a left child, make n o's parent's left child
            o.getParent().setLeft(n);

        } else { // if o is a right child, make n o's parent's right child
            o.getParent().setRight(n);
        }

        if (n != null) { // if n is not empty, since it is now a child of o's parent make n's parent o's
                         // parent
            n.setParent(o.getParent());
        }
    }

    public void remove(T data) {
        BinaryTree<T> node = get(root, data);

        if (node.getLeft() == null) { // node only has right child
            // make whatever used to point to node point to its right child
            displace(node, node.getRight());
        } else if (node.getRight() == null) { // node only has left child
            // ditto
            displace(node, node.getLeft());
        } else { // node has neither or both children
            BinaryTree<T> successor = successor(node);
            if (successor.getParent() != node) { // if node is not the parent of its successor / if the successor of
                                                 // node is not its right child
                // make whatever used to point to successor point to its right child
                displace(successor, successor.getRight());
                // make the successor right child, whatever the deleted node has as the right
                // this is done because in the next step node will be displaced by successor
                successor.setRight(node.getRight());
            }

            // make everything that pointed to node point to successor, which points to
            // node's right children
            displace(node, successor);
            // make successor point to node's left children
            successor.setLeft(node.getLeft());
            // make node's left children know that it is now its parents because of the
            // above step
            successor.getLeft().setParent(successor);
        }
    }

    public BinaryTree<T> get(BinaryTree<T> curNode, T target) {
        if (curNode == null || curNode.getData().equals(target)) {
            return curNode;
        } else if (target.compareTo(curNode.getData()) < 0) {
            return curNode.getLeft() != null ? get(curNode.getLeft(), target) : null;
        } else {
            return curNode.getRight() != null ? get(curNode.getRight(), target) : null;
        }
    }

    public void add(BinaryTree<T> curNode, T target) {
        if (curNode == null) {
            BinaryTree<T> newNode = new BinaryTree<>(target); // repeated code but avoids creating when unnecessary
            root = newNode;
            return;
        }

        if (curNode.getLeft() != null && target.compareTo(curNode.getData()) < 0) {
            add(curNode.getLeft(), target);
            return;
        } else if (curNode.getRight() != null && target.compareTo(curNode.getData()) >= 0) {
            add(curNode.getRight(), target);
            return;
        }

        BinaryTree<T> newNode = new BinaryTree<>(target);

        if (target.compareTo(curNode.getData()) < 0) {
            curNode.setLeft(newNode);
        } else {
            curNode.setRight(newNode);
        }

        newNode.setParent(curNode);
    }

    public boolean contains(T target) {
        if (root == null) {
            return false;
        }

        BinaryTree<T> node = get(root, target);
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
