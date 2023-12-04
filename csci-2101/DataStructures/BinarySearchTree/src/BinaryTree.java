/**
 * A simple implementation of a binary tree. Note that there is effectively no
 * distinction between a tree object and a node object - a tree object can
 * equivalently be thought of as the node object that is the root of that tree.
 *
 * @author Sean Barker
 * @author Kai Presler-Marshall
 */
public class BinaryTree<T extends Comparable<T>> {
    private T data;
    private BinaryTree<T> left, right, parent;

    public BinaryTree(T data) {
        this(data, null, null);
    }

    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
        this.data = data;
        setLeft(left);
        setRight(right);
    }

    public void setLeft(BinaryTree<T> left) {
        if (null != this.left) {
            this.left.parent = null;
        }
        this.left = left;
        if (null != this.left) {
            this.left.parent = this;
        }
    }

    public void setRight(BinaryTree<T> right) {
        if (null != this.right) {
            this.right.parent = null;
        }
        this.right = right;
        if (null != this.right) {
            this.right.parent = this;
        }
    }

    public T getData() {
        return data;
    }

    public BinaryTree<T> getLeft() {
        return left;
    }

    public BinaryTree<T> getRight() {
        return right;
    }

    public BinaryTree<T> getParent() {
        return parent;
    }

    // check if this is a root node (no parent)
    public boolean isRoot() {
        return parent == null;
    }

    // check if this is a leaf node (no children)
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // get root of the tree that this node is part of
    public BinaryTree<T> root() {
        if (this.isRoot()) {
            return this;
        }
        return this.parent.root();
    }

    // get the number of nodes in this tree
    public int size() {
        return 1 + (null == left ? 0 : left.size()) + (null == right ? 0 : right.size());
    }

    // get depth of this node (path length from root to this node)
    public int depth() {
        if (this.isRoot()) {
            return 0;
        }
        return parent.depth() + 1;
    }

    // get height of tree (maximum path length to some descendant)
    public int height() {
        if (this.isLeaf()) {
            return 0;
        }
        return 1 + Math.max(
                null == left ? 0 : left.height(),
                null == right ? 0 : right.height());
    }
}
