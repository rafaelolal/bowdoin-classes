import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterates over all combinations of all lengths without repeats in a given list
 */
public class SubsetIterator<T> implements Iterator<List<T>> {

    /** the original list */
    private List<T> list;
    /**
     * a number whose binary representation indicates which elements to be in the
     * subset. 0s means that the index in list should not be included in the subset,
     * while 1s means it should be included
     */
    private long currentCombination;

    public SubsetIterator(List<T> list) {
        this.list = list;
        currentCombination = 0;
    }

    /**
     * Allows the iteration to continue for 2^list.size() - 1, the total number of
     * combinations that can be made
     * 
     * @return if there is a next combination to be made
     */
    public boolean hasNext() {
        return currentCombination < powerOf2(list.size());
    }

    /**
     * Grabs the next combination that can be made with the original list
     * 
     * @return the corresponding combination given the binary representation of
     *         currentCombination
     */
    public List<T> next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        List<T> combination = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            // since the combination is determined by the binary representation of
            // currentCombination, checks the ith bit of currentCombination, if that bit is
            // not 0, it means that the corresponding ith index in the original list should
            // be present in the combination
            if ((currentCombination & powerOf2(i)) != 0) {
                combination.add(list.get(i));
            }
        }

        currentCombination++;
        return combination;
    }

    /**
     * Uses bitwise left shift operator to calculate powers of 2
     * 
     * @param i the power to be calculate
     * 
     * @return the ith power of 2
     */
    private long powerOf2(int i) {
        // shifts a bit to the left i times
        return 1L << i;
    }

}
