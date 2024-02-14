import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class BinaryTreeTests {

    @Test
    public void findRoot() {
        BinaryTree<Integer> l2 = new BinaryTree<Integer>(3);
        BinaryTree<Integer> r2 = new BinaryTree<Integer>(4);
        BinaryTree<Integer> l = new BinaryTree<Integer>(1, l2, r2);

        BinaryTree<Integer> l3 = new BinaryTree<Integer>(5);
        BinaryTree<Integer> r3 = new BinaryTree<Integer>(6);
        BinaryTree<Integer> r = new BinaryTree<Integer>(2, l3, r3);

        BinaryTree<Integer> root = new BinaryTree<Integer>(0, l, r);

        assertEquals(root, root.findRoot());
        assertEquals(root, l.findRoot());
        assertEquals(root, r.findRoot());
        assertEquals(root, l2.findRoot());
        assertEquals(root, r2.findRoot());
        assertEquals(root, l3.findRoot());
        assertEquals(root, r3.findRoot());
    }

    @Test
    public void findDepth() {
        BinaryTree<Integer> l3 = new BinaryTree<Integer>(5);
        BinaryTree<Integer> r3 = new BinaryTree<Integer>(6);
        BinaryTree<Integer> l2 = new BinaryTree<Integer>(3, null, r3);
        BinaryTree<Integer> r2 = new BinaryTree<Integer>(4, l3, null);
        BinaryTree<Integer> l = new BinaryTree<Integer>(1, l2, null);
        BinaryTree<Integer> r = new BinaryTree<Integer>(2, null, r2);
        BinaryTree<Integer> root = new BinaryTree<Integer>(0, l, r);

        assertEquals(0, root.findDepth());
        assertEquals(1, l.findDepth());
        assertEquals(1, r.findDepth());
        assertEquals(2, l2.findDepth());
        assertEquals(2, r2.findDepth());
        assertEquals(3, l3.findDepth());
        assertEquals(3, r3.findDepth());
    }

    @Test
    public void findHeight() {
        BinaryTree<Integer> l3 = new BinaryTree<Integer>(5);
        BinaryTree<Integer> r3 = new BinaryTree<Integer>(6);
        BinaryTree<Integer> l2 = new BinaryTree<Integer>(3, null, r3);
        BinaryTree<Integer> r2 = new BinaryTree<Integer>(4, l3, null);
        BinaryTree<Integer> l = new BinaryTree<Integer>(1, l2, null);
        BinaryTree<Integer> r = new BinaryTree<Integer>(2, null, r2);
        BinaryTree<Integer> root = new BinaryTree<Integer>(0, l, r);

        assertEquals(3, root.findHeight());
        assertEquals(2, l.findHeight());
        assertEquals(2, r.findHeight());
        assertEquals(1, l2.findHeight());
        assertEquals(1, r2.findHeight());
        assertEquals(0, l3.findHeight());
        assertEquals(0, r3.findHeight());
    }

    @Test
    public void isFull() {
        BinaryTree<Integer> l4 = new BinaryTree<Integer>(5);
        BinaryTree<Integer> r4 = new BinaryTree<Integer>(6);
        BinaryTree<Integer> l3 = new BinaryTree<Integer>(5);
        BinaryTree<Integer> r3 = new BinaryTree<Integer>(6);
        BinaryTree<Integer> l2 = new BinaryTree<Integer>(3, l4, r3);
        BinaryTree<Integer> r2 = new BinaryTree<Integer>(4, l3, r4);
        BinaryTree<Integer> l = new BinaryTree<Integer>(1, l2, null);
        BinaryTree<Integer> r = new BinaryTree<Integer>(2, null, r2);
        BinaryTree<Integer> root = new BinaryTree<Integer>(0, l, r);

        assertFalse(root.isFull());
        assertFalse(l.isFull());
        assertFalse(r.isFull());
        assertTrue(l2.isFull());
        assertTrue(r2.isFull());
        assertTrue(l3.isFull());
        assertTrue(r3.isFull());
        assertTrue(l4.isFull());
        assertTrue(r4.isFull());
    }

    @Test
    public void testContains() {
        // Create a binary tree with a height of 3
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);
        root.setLeft(left);
        root.setRight(right);

        // Second level
        BinaryTree<Integer> leftLeft = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight = new BinaryTree<>(5);
        left.setLeft(leftLeft);
        left.setRight(leftRight);

        // Third level
        BinaryTree<Integer> leftLeftLeft = new BinaryTree<>(6);
        leftLeft.setLeft(leftLeftLeft);
        BinaryTree<Integer> rightRight = new BinaryTree<>(7);
        right.setRight(rightRight);

        // Test cases
        assertTrue(root.contains(1)); // Root contains 1
        assertTrue(root.contains(2)); // 2 is in the tree
        assertTrue(root.contains(3)); // 3 is in the tree
        assertTrue(root.contains(4)); // 4 is in the tree
        assertTrue(root.contains(5)); // 5 is in the tree
        assertTrue(root.contains(6)); // 6 is in the tree
        assertTrue(root.contains(7)); // 7 is in the tree
        assertFalse(root.contains(8)); // 8 is not in the tree
    }

    @Test
    public void testWeakEquals() {
        // Create two binary trees for testing
        // Tree 1
        BinaryTree<Integer> root1 = new BinaryTree<>(1);
        BinaryTree<Integer> left1 = new BinaryTree<>(1);
        BinaryTree<Integer> right1 = new BinaryTree<>(3);
        root1.setLeft(left1);
        root1.setRight(right1);

        // Second level of Tree 1
        BinaryTree<Integer> leftLeft1 = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight1 = new BinaryTree<>(5);
        left1.setLeft(leftLeft1);
        left1.setRight(leftRight1);

        // Third level of Tree 1
        BinaryTree<Integer> leftLeftLeft1 = new BinaryTree<>(6);
        leftLeft1.setLeft(leftLeftLeft1);

        // Tree 2 (same structure as Tree 1 but with different values)
        BinaryTree<Integer> root2 = new BinaryTree<>(1);
        BinaryTree<Integer> left2 = new BinaryTree<>(2);
        BinaryTree<Integer> right2 = new BinaryTree<>(2);
        root2.setLeft(left2);
        root2.setRight(right2);

        // Second level of Tree 2
        BinaryTree<Integer> leftLeft2 = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight2 = new BinaryTree<>(5);
        left2.setLeft(leftLeft2);
        left2.setRight(leftRight2);

        // Third level of Tree 2
        BinaryTree<Integer> leftLeftLeft2 = new BinaryTree<>(6);
        leftLeft2.setLeft(leftLeftLeft2);

        // Test cases
        assertFalse(root1.weakEquals(root2)); // Trees are structurally equal but different values
        assertFalse(root1.weakEquals(left1)); // Different structures
        assertFalse(root1.weakEquals(left2)); // Different values
        assertFalse(root1.weakEquals(null)); // Null tree

        // Modify Tree 2 to have the same values as Tree 1
        root2.setData(1);
        left2.setData(6);
        right2.setData(3);
        leftLeft2.setData(4);
        leftRight2.setData(5);
        leftLeftLeft2.setData(1);
        assertTrue(root1.weakEquals(root2)); // Trees are now equal in values
    }

    @Test
    public void testToString() {
        // Create a binary tree with a height of at least 3
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);
        root.setLeft(left);
        root.setRight(right);

        // Second level
        BinaryTree<Integer> leftLeft = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight = new BinaryTree<>(5);
        left.setLeft(leftLeft);
        left.setRight(leftRight);

        // Third level
        BinaryTree<Integer> leftLeftLeft = new BinaryTree<>(6);
        right.setLeft(leftLeftLeft);
        BinaryTree<Integer> rightRight = new BinaryTree<>(7);
        right.setRight(rightRight);

        // Expected string representation of the tree
        String expected = "[1, 2, 3, 4, 5, 6, 7, ]";

        // Test the toString method
        assertEquals(expected, root.toString());
    }

    @Test
    public void testInOrderIterator() {
        List<Integer> result = new ArrayList<>();

        // Tree 1
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left1 = new BinaryTree<>(2);
        BinaryTree<Integer> right1 = new BinaryTree<>(3);
        root.setLeft(left1);
        root.setRight(right1);

        // Second level of Tree 1
        BinaryTree<Integer> leftLeft1 = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight1 = new BinaryTree<>(5);
        left1.setLeft(leftLeft1);
        left1.setRight(leftRight1);

        // Third level of Tree 1
        BinaryTree<Integer> leftLeftLeft1 = new BinaryTree<>(6);
        BinaryTree<Integer> x = new BinaryTree<>(7);
        right1.setLeft(leftLeftLeft1);
        right1.setRight(x);
        leftLeft1.setRight(new BinaryTree<Integer>(8));
        leftRight1.setLeft(new BinaryTree<Integer>(9));
        leftRight1.setRight(new BinaryTree<Integer>(10));
        leftLeftLeft1.setLeft(new BinaryTree<Integer>(11));

        Iterator<Integer> it = root.inOrderIterator();
        while (it.hasNext()) {
            result.add(it.next());
        }

        assertEquals("[4, 8, 2, 9, 5, 10, 1, 11, 6, 3, 7]", result.toString());
    }

    @Test
    public void testPreOrderIterator() {
        List<Integer> result = new ArrayList<>();

        // Tree 1
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left1 = new BinaryTree<>(2);
        BinaryTree<Integer> right1 = new BinaryTree<>(3);
        root.setLeft(left1);
        root.setRight(right1);

        // Second level of Tree 1
        BinaryTree<Integer> leftLeft1 = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight1 = new BinaryTree<>(5);
        left1.setLeft(leftLeft1);
        left1.setRight(leftRight1);

        // Third level of Tree 1
        BinaryTree<Integer> leftLeftLeft1 = new BinaryTree<>(6);
        BinaryTree<Integer> x = new BinaryTree<>(7);
        right1.setLeft(leftLeftLeft1);
        right1.setRight(x);
        leftLeft1.setRight(new BinaryTree<Integer>(8));
        leftRight1.setLeft(new BinaryTree<Integer>(9));
        leftRight1.setRight(new BinaryTree<Integer>(10));
        leftLeftLeft1.setLeft(new BinaryTree<Integer>(11));

        Iterator<Integer> it = root.PreOrderIterator();
        while (it.hasNext()) {
            result.add(it.next());
        }

        assertEquals("[1, 2, 4, 8, 5, 9, 10, 3, 6, 11, 7]", result.toString());

    }

    @Test
    public void testPostOrderIterator() {
        List<Integer> result = new ArrayList<>();

        // Tree 1
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left1 = new BinaryTree<>(2);
        BinaryTree<Integer> right1 = new BinaryTree<>(3);
        root.setLeft(left1);
        root.setRight(right1);

        // Second level of Tree 1
        BinaryTree<Integer> leftLeft1 = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight1 = new BinaryTree<>(5);
        left1.setLeft(leftLeft1);
        left1.setRight(leftRight1);

        // Third level of Tree 1
        BinaryTree<Integer> leftLeftLeft1 = new BinaryTree<>(6);
        BinaryTree<Integer> x = new BinaryTree<>(7);
        right1.setLeft(leftLeftLeft1);
        right1.setRight(x);
        leftLeft1.setRight(new BinaryTree<Integer>(8));
        leftRight1.setLeft(new BinaryTree<Integer>(9));
        leftRight1.setRight(new BinaryTree<Integer>(10));
        leftLeftLeft1.setLeft(new BinaryTree<Integer>(11));

        Iterator<Integer> it = root.PostOrderIterator();
        while (it.hasNext()) {
            result.add(it.next());
        }

        assertEquals("[8, 4, 9, 10, 5, 2, 11, 6, 7, 3, 1]", result.toString());

    }
}