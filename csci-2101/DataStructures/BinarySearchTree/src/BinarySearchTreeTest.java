import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class BinarySearchTreeTest {

    @Test
    public void addTest() {
        BinarySearchTree<Integer> test = new BinarySearchTree<>();
        int[] nums = { 30, 20, 20, 30, 10, 10, 15, 25, 35, 45, 55, 55, 100, 0, 0 };
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            numList.add(nums[i]);
            Collections.sort(numList);
            test.add(nums[i]);
            assertEquals(numList.toString(), test.toString());
        }
    }
}