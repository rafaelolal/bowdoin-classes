import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A dictionary for use in a game of Boggle. Stores all valid words and
 * can also locate whether given strings are prefixes of any valid word.
 * Don't modify this class!
 */
public class BoggleDictionary {

    /**
     * Alphabetically-ordered list of all words in the dictionary.
     */
    private List<String> words;

    /**
     * Construct a Boggle dictionary from a file containing a word list,
     * one word per line.
     * 
     * @param filename The word list filename.
     */
    public BoggleDictionary(String filename) {
        words = new ArrayList<String>();
        Scanner in;
        try {
            in = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("can't read " + filename);
        }
        while (in.hasNextLine()) {
            String line = in.nextLine();
            words.add(line.toLowerCase());
        }
        in.close();
        words.sort(null); // sort words alphabetically
    }

    /**
     * Check whether the given string is a complete word in the dictionary.
     * 
     * @param word The possible word to check.
     * @return Whether the given string is a word in the dictionary.
     */
    public boolean isWord(String word) {
        if (word.length() < 3) {
            return false;
        }

        int search = Collections.binarySearch(words, word);
        return search >= 0;
    }

    /**
     * Check whether the given string is a prefix of any word in the dictionary.
     * Complete words are also considered prefixes, whether or not they are
     * also prefixes of other valid words.
     * 
     * @param prefix The possible prefix to check.
     * @return Whether the given string is a prefix of any word in the dictionary.
     */
    public boolean isPrefix(String prefix) {
        int search = Collections.binarySearch(words, prefix);
        if (search >= 0) {
            return true; // complete word, so also a prefix
        } else {
            int insertionPoint = -(search + 1);
            String testWord = words.get(insertionPoint);
            return testWord.startsWith(prefix);
        }
    }

}