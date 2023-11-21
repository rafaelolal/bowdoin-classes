import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Rafael Almeida
 * CSCI 2101 C
 * 11/20/2023
 * Project 7: Lexicon, Helicon, Lexical
 * 
 * Implementation of a trie data structure that tracks words and allows
 * suggesting corrections given a target and returning results to a regex search
 */
public class LexiconTrie implements Lexicon {
    /** The first node in a trie, always represents the character ' ' */
    LexiconNode root;
    /** Count of words in the trie */
    int wordCount;

    public LexiconTrie() {
        root = new LexiconNode(' ');
        wordCount = 0;
    }

    /**
     * Adds all `word` characters into the trie if not already present and
     * sets final node as the ending of a word if needed
     * 
     * @param word word to add
     * 
     * @return whether the word was added
     */
    @Override
    public boolean addWord(String word) {
        LexiconNode curNode = root;
        for (char character : word.toCharArray()) {
            LexiconNode nextNode = curNode.get(character);
            if (nextNode == null) {
                nextNode = curNode.add(character);
            }

            curNode = nextNode;
        }

        if (!curNode.isWord()) {
            curNode.setIsWord(true);
            wordCount++;
            return true;
        }

        return false;
    }

    /**
     * Scans all lines of file `filename` and adds it as a word
     * 
     * @param filename the file to read
     * 
     * @return the number of words added or -1 if `filename` not found
     */
    @Override
    public int addWordsFromFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            int wordsAdded = 0;
            while (scan.hasNext()) {
                if (addWord(scan.next())) {
                    wordsAdded++;
                }
            }

            return wordsAdded;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    /**
     * Checks if the sequence of characters of `word` are in the trie and that the
     * final node is the ending of a word
     * 
     * @param word the word to search for
     * 
     * @return if the trie contains `word`
     */
    @Override
    public boolean containsWord(String word) {
        LexiconNode finalNode = getFinalNodeOf(word);
        return finalNode != null && finalNode.isWord();
    }

    /**
     * Checks if the sequence of characters of `prefix` are in the trie
     * 
     * @param prefix the prefix to search for
     * 
     * @return if the trie contains `prefix`
     */
    @Override
    public boolean containsPrefix(String prefix) {
        return getFinalNodeOf(prefix) != null;
    }

    /**
     * Helper method for `.containsWord` and `.containsPrefix`. Returns node
     * representing the final character in `str` if any
     * 
     * @param str the string to search for
     * 
     * @return the node of the final character in `str` or null
     */
    private LexiconNode getFinalNodeOf(String str) {
        LexiconNode curNode = root;
        for (char character : str.toCharArray()) {
            if (curNode.get(character) == null) {
                return null;
            }

            curNode = curNode.get(character);
        }

        return curNode;
    }

    @Override
    public Set<String> matchRegex(String pattern) {
        Set<String> matches = new HashSet<>();
        recursiveMatchRegex(root, "", pattern, matches);
        return matches;
    }

    /**
     * Recursively traverses the trie, exploring matches against a given pattern
     * using simple wildcard
     * characters (*, ?, -), within the trie represented by the current node.
     *
     * @param curNode the current node being traversed to see if the word it may
     *                forms matches the pattern
     * @param prefix  The possible match being formed
     * @param pattern The pattern to match against
     * @param matches A set to store valid matches for the given pattern.
     */
    private void recursiveMatchRegex(LexiconNode curNode, String prefix, String pattern, Set<String> matches) {
        // If the pattern is empty, check and add to matches if the current prefix is a
        // valid word
        if (pattern.length() == 0) {
            if (curNode.isWord()) {
                matches.add(prefix);
            }
            return;
        }

        char toMatch = pattern.charAt(0);

        // Any one character
        if (toMatch == '_') {
            for (LexiconNode child : curNode) {
                recursiveMatchRegex(
                        child,
                        prefix + child.getCharacter(),
                        pattern.substring(1),
                        matches);
            }
        }
        // Any sequence of zero or more characters
        else if (toMatch == '*') {
            // Recursively call the method with the remaining pattern after consuming '*' at
            // the current position
            recursiveMatchRegex(
                    curNode,
                    prefix,
                    pattern.substring(1),
                    matches);

            // Iterate through child nodes to explore possible matches with the '*' wildcard
            // For every child, either consume '*' or not
            for (LexiconNode child : curNode) {
                recursiveMatchRegex(
                        child,
                        prefix + child.getCharacter(),
                        pattern,
                        matches);
            }
        }
        // None or any character
        else if (toMatch == '?') {
            // Recursively call the method with the remaining pattern after consuming '?' at
            // the current position
            recursiveMatchRegex(
                    curNode,
                    prefix,
                    pattern.substring(1),
                    matches);

            // Iterate through child nodes to explore possible matches with the '?'
            // wildcard
            for (LexiconNode child : curNode) {
                recursiveMatchRegex(
                        child,
                        prefix + child.getCharacter(),
                        pattern.substring(1),
                        matches);
            }
        }
        // Match against the character in the current position of the pattern
        else {
            // Get the next node corresponding to the current character in the lexicon.
            LexiconNode nextNode = curNode.get(toMatch);

            // If a matching node is found, recursively call the method with the updated
            // prefix and pattern.
            if (nextNode != null) {
                recursiveMatchRegex(
                        nextNode,
                        prefix + toMatch,
                        pattern.substring(1),
                        matches);
            }
        }
    }

    /**
     * Attempts to remove `word` from trie
     * 
     * @param word the word to remove
     * 
     * @return if `word` was removed
     */
    @Override
    public boolean removeWord(String word) {
        // stores the nodes leading up to the node of the final character in `word`
        Stack<LexiconNode> nodesVisited = new Stack<>();
        nodesVisited.push(root);
        LexiconNode curNode = root;
        // iterate over nodes representing `word`
        for (char character : word.toCharArray()) {
            LexiconNode nextNode = curNode.get(character);
            // if no `nextNode`, trie does not contain `word`
            if (nextNode == null) {
                return false;
            }

            curNode = nextNode;
            nodesVisited.push(curNode);
        }

        // trie does not contain `word` even if it contains the prefix `word`
        if (!curNode.isWord()) {
            return false;
        }

        // making the final node not correspond to a word
        curNode.setIsWord(false);
        wordCount--;
        // removal is done if final node is part of another word
        if (curNode.iterator().hasNext()) {
            return true;
        }

        // moving back up the trie by popping `nodesVisited` until finding a node that
        // represents a word or the root
        char child = nodesVisited.pop().getCharacter();
        LexiconNode parent = nodesVisited.pop();

        while (!parent.isWord() && !nodesVisited.isEmpty()) {
            child = parent.getCharacter();
            parent = nodesVisited.pop();
        }

        // removing the node closest to the root that was part of `word` and is not part
        // of another word
        parent.remove(child);
        return true;
    }

    /**
     * Returns all words in the trie that are `maxDistance` from target
     * 
     * @param target      the word to suggest corrections to
     * @param maxDistance the maximum number of different characters allowed for two
     *                    words of the same length
     * 
     * @return all appropriate corrections
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
        Set<String> set = new HashSet<>();
        recursiveSuggestCorrections(root, target, maxDistance, "", set);
        return set;
    }

    /**
     * Recursively traverses te trie to explore possible corrections for a given
     * target string based on
     * `maxDistance`
     *
     * @param curNode     the current node that is being traversed checked if it
     *                    forms a suggestion
     * @param target      The target string for which corrections are suggested
     * @param maxDistance The maximum allowable different characters between a
     *                    potential correction and the target
     * @param prefix      The current prefix being formed during the recursive
     *                    traversal
     * @param corrections A set to store valid corrections for the target string
     */
    private void recursiveSuggestCorrections(
            LexiconNode curNode,
            String target,
            int maxDistance,
            String prefix,
            Set<String> corrections) {
        // If the maximum distance is less than 0, no further corrections allowed
        if (maxDistance < 0) {
            return;
        }

        // If the prefix has reached the length of the target, check and add to
        // corrections if it is a valid word
        if (prefix.length() == target.length()) {
            if (curNode.isWord()) {
                corrections.add(prefix);
            }
            return;
        }

        // Determine the position of the next character in the prefix
        int nextCharacter = prefix.length();

        // Iterate through child nodes in the lexicon to explore possible corrections.
        for (LexiconNode child : curNode) {
            // Determine the adjustment to `maxDistance` based on the current character
            // match
            int toSubtract = child.getCharacter() != target.charAt(nextCharacter) ? 1 : 0;

            // Recursively call the method with updated parameters for the current child
            // node
            recursiveSuggestCorrections(
                    child,
                    target,
                    maxDistance - toSubtract,
                    prefix + child.getCharacter(),
                    corrections);
        }
    }

    @Override
    public int numWords() {
        return wordCount;
    }

    /**
     * Recursively explores the trie to add to `words` all of the words in the
     * trie in alphabetical order
     * 
     * @return alphabetical order iterator of all words in the trie
     */
    @Override
    public Iterator<String> iterator() {
        List<String> words = new ArrayList<>();
        recursiveIterator(root, "", words);
        return words.iterator();
    }

    /**
     * Recursively explores the trie to add to `words` all of the words in the trie
     * in alphabetical order
     * 
     * @param curNode the current node being traversed to see if it forms a word
     * @param prefix  the current potential word being formed
     * @param words   all words found
     */
    private void recursiveIterator(LexiconNode curNode, String prefix, List<String> words) {
        if (curNode.isWord()) {
            words.add(prefix);
        }

        // Branching out to find more words
        for (LexiconNode child : curNode) {
            recursiveIterator(child, prefix + child.getCharacter(), words);
        }
    }
}
