import java.util.LinkedList;

/**
 * A simple implementation of a binary tree. Note that there is effectively no
 * distinction between a tree object and a node object - a tree object can
 * equivalently be thought of as the node object that is the root of that tree.
 *
 * @author Sean Barker
 * @author Kai Presler-Marshall
 */
public class BinaryTree<T extends Comparable<T>> {
    private Integer data;
    private BinaryTree<T> left, right, parent;

    public BinaryTree(T data) {
        this(data, null, null);
    }

    public LinkedList<String> findLeafWithACost(BinaryTree<T> root, int cost) {
        if (cost == 0) {
            LinkedList<String> path = new List<>();

            BinaryTree<T> curNode = root;
            while (curNode.parent != null) {
                if (curNode.parent.left.equals(root)) {
                    path.add(0, "Left");
                } else {
                    path.add(0, 'Right');
                }


            }
        }

        if (root.left != null && root.left.getData() <= cost) {
            findLeafWithACost(root.left, cost - root.left.getData());
        }

        if (root.right != null && root.right.getData() <= cost) {
            findLeafWithACost(root.right, cost - root.right.getData());
        }
    }

    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
        this.data = (int) data;

        setLeft(left);
        if (left != null) {
            left.parent = this;
        }

        setRight(right);
        if (right != null) {
            right.parent = this;
        }
    }

    public void setLeft(BinaryTree<T> left) {
        this.left = left;
    }

    public void setRight(BinaryTree<T> right) {
        this.right = right;
    }

    public void setParent(BinaryTree<T> parent) {
        this.parent = parent;
    }

    public Integer getData() {
        return data;
    }

    public void setData(T data) {
        this.data = (int) data;
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

    public String toString() {
        String s = "";

        s += "D: " + data.toString();
        s += " L: " + (left != null ? left.getData().toString() : "null");
        s += " R: " + (right != null ? right.getData().toString() : "null");
        s += " P: " + (parent != null ? parent.getData().toString() : "null");
        return s;
    }
}
