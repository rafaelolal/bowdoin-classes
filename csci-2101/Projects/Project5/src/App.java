import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Rafael Almeida
 * CSCI 2101 C
 * 10/26/2023
 * Project 5: Two Towers
 * 
 * Handles receiving input of number of blocks and outputting the best tower,
 * the time taken to do so, and all other relevant information
 */
public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        // taking in input
        System.out.print("Enter number of blocks: ");
        int blockCount = scan.nextInt();
        scan.close();

        double optimalHeight = getOptimalHeight(blockCount);

        // beginning the timer for the relevant part of the code, finding the best short
        // tower
        long startTime = System.currentTimeMillis();
        List<Integer> bestSubset = getBestSubset(blockCount, optimalHeight);
        long duration = System.currentTimeMillis() - startTime;

        double bestHeight = getHeight(bestSubset);

        // printing out all required information
        System.out.println("Target (optimal) height: " + optimalHeight);
        System.out.println("Best subset: " + beautifyArrayToString(bestSubset));
        System.out.println("Best height: " + bestHeight);
        System.out.println("Distance from optimal: " + (optimalHeight - bestHeight));
        System.out.println(("Solve duration: " + duration + " ms"));

    }

    /**
     * Calculates then divides the total height by 2, which is the optimal height
     * 
     * @param blockCount the number of blocks
     * @return optimal height of a tower
     */
    private static double getOptimalHeight(int blockCount) {
        double sum = 0;
        for (int i = 1; i <= blockCount; i++) {
            sum += Math.sqrt(i);
        }
        return sum / 2;
    }

    /**
     * Iterates over all possible subsets and returns the one with the smallest
     * difference from optimalHeight, ensuring the difference is > 0 to return the
     * shorter tower
     * 
     * @param blockCount    the number of blocks
     * @param optimalHeight the height that should be compared to
     * 
     * @return a list with the best blocks you can make the short tower with
     */
    private static List<Integer> getBestSubset(int blockCount, double optimalHeight) {
        // creating list of blocks
        List<Integer> blocks = new ArrayList<>();
        for (int i = 1; i <= blockCount; i++) {
            blocks.add(Integer.valueOf(i));
        }

        SubsetIterator<Integer> it = new SubsetIterator<>(blocks);

        List<Integer> bestSubset = new ArrayList<>();
        double minDifference = optimalHeight;
        while (it.hasNext()) {
            List<Integer> subset = it.next();
            double height = getHeight(subset);
            double difference = optimalHeight - height;
            // to get the shorter tower, the difference must be > 0.
            if (difference > 0 && difference < minDifference) {
                minDifference = difference;
                bestSubset = subset;
            }
        }

        return bestSubset;
    }

    /**
     * Given the area of the blocks, adds up their side length to find the height
     * 
     * @param blocks the blocks that make the tower
     * 
     * @return the height of the tower
     */
    private static double getHeight(List<Integer> blocks) {
        double sum = 0;
        for (Integer i : blocks) {
            sum += Math.sqrt(i);
        }
        return sum;
    }

    /**
     * Simply removes the '[', ']', and ',' from the string representation of an
     * array
     * 
     * @param arr the array to convert to string
     * 
     * @return the string representation of an array without '[', ']', and ','
     */
    private static String beautifyArrayToString(List<?> arr) {
        String str = arr.toString();
        return str.substring(1, str.length() - 1).replaceAll(",", "");
    }

}
