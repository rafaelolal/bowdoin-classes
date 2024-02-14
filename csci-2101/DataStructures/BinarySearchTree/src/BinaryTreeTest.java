import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BinaryTreeTest {

    @Test
    public void testSetLeftAndSetRight() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);

        tree.setLeft(left);
        tree.setRight(right);

        assertEquals(left, tree.getLeft());
        assertEquals(right, tree.getRight());
    }

    @Test
    public void testSetParent() {
        BinaryTree<Integer> parent = new BinaryTree<>(1);
        BinaryTree<Integer> child = new BinaryTree<>(2);

        child.setParent(parent);

        assertEquals(parent, child.getParent());
    }

    @Test
    public void testGetDataAndSetData() {
        BinaryTree<String> tree = new BinaryTree<>("apple");

        assertEquals("apple", tree.getData());

        tree.setData("orange");

        assertEquals("orange", tree.getData());
    }

    @Test
    public void testIsRoot() {
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> child = new BinaryTree<>(2);

        root.setLeft(child);
        assertTrue(root.isRoot());
        child.setParent(root);
        assertFalse(child.isRoot());
        root.setLeft(null);
        child.setParent(null);
        assertTrue(child.isRoot());
    }

    @Test
    public void testIsLeaf() {
        BinaryTree<Integer> leaf = new BinaryTree<>(1);
        BinaryTree<Integer> parent = new BinaryTree<>(2, null, leaf);

        assertTrue(leaf.isLeaf());
        assertFalse(parent.isLeaf());
    }

    @Test
    public void testRoot() {
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> child = new BinaryTree<>(2);
        BinaryTree<Integer> grandChild = new BinaryTree<>(3);

        root.setLeft(child);
        child.setParent(root);
        child.setLeft(grandChild);
        grandChild.setParent(child);

        assertEquals(root, grandChild.root());
    }

    @Test
    public void testSize() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);

        tree.setLeft(left);
        tree.setRight(right);

        assertEquals(3, tree.size());
    }

    @Test
    public void testDepth() {
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> child = new BinaryTree<>(2);
        BinaryTree<Integer> grandChild = new BinaryTree<>(3);

        root.setLeft(child);
        child.setParent(root);
        child.setLeft(grandChild);
        grandChild.setParent(child);

        assertEquals(2, grandChild.depth());
    }

    @Test
    public void testHeight() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);

        tree.setLeft(left);
        tree.setRight(right);

        assertEquals(1, tree.height());
    }

    @Test
    public void testToString() {
        BinaryTree<String> tree = new BinaryTree<>("root");
        BinaryTree<String> left = new BinaryTree<>("left");
        BinaryTree<String> right = new BinaryTree<>("right");

        tree.setLeft(left);
        tree.setRight(right);

        assertEquals("D: root L: left R: right P: null", tree.toString());
    }
}
