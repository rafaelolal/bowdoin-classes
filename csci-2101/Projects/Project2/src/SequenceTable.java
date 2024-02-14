import java.util.HashMap;
import java.util.Map;

/** Represents many sequences and their respective frequency maps using a Map */
public class SequenceTable {
    private Map<String, FrequencyMap> frequencyMaps;

    public SequenceTable() {
        frequencyMaps = new HashMap<>();
    }

    /**
     * Uses the put method of FrequencyMap to increment the frequency of a character
     * that follows a sequence. Creates a FrequencyMap for the sequence if not
     * already present.
     * 
     * @param sequence  the sequence that precedes a character
     * @param character the character that must have its frequency incremented
     */
    public void put(String sequence, String character) {
        if (!frequencyMaps.containsKey(sequence)) {
            frequencyMaps.put(sequence, new FrequencyMap());
        }

        frequencyMaps.get(sequence).put(character);
    }

    /**
     * Uses a weighted method to randomly select the character that should follow
     * sequence.
     * 
     * Creates a random double in the range [0, 1) and then uses
     * subtraction to iteratively check if a given entry has enough weight to be
     * selected given the random number.
     * 
     * source:
     * https://stackoverflow.com/questions/6737283/weighted-randomness-in-java
     * 
     * @param sequence the sequence that must be followed by the return value
     * @return a randomly selected character given the frequency (weights) of the
     *         sequence's FrequencyMap
     */
    public String getNextCharacter(String sequence) {
        double r = Math.random();
        for (Map.Entry<String, Integer> entry : frequencyMaps.get(sequence).getFrequencies().entrySet()) {
            r -= (double) entry.getValue() / frequencyMaps.get(sequence).getFrequencySum();
            if (r <= 0.0) {
                return entry.getKey();
            }
        }

        return null;
    }

    /**
     * Keeps track of the frequency a character follows a sequence using a Map and
     * the sum of the frequencies. The sequence in question is stored in the outer
     * class.
     */
    private class FrequencyMap {

        /** Represents the frequency a character follows a sequence */
        private Map<String, Integer> frequencies;
        /** Total characters put into the frequencies Map */
        private int frequencySum;

        private FrequencyMap() {
            frequencies = new HashMap<>();
            frequencySum = 0;
        }

        /**
         * Puts as 0 or increments the frequency of a character in this' frequencies.
         * 
         * @param character the character to put or increment frequency
         */
        private void put(String character) {
            frequencies.put(character,
                    frequencies.getOrDefault(character, 0) + 1);
            frequencySum++;
        }

        private Map<String, Integer> getFrequencies() {
            return frequencies;
        }

        private int getFrequencySum() {
            return frequencySum;
        }
    }

}
