import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
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
        root.recursiveMatchRegex("", pattern, matches);
        return matches;
    }

    @Override
    public int numWords() {
        return wordCount;
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
        // removal is done if final node is part of a word
        if (curNode.children.size() != 0) {
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
        root.recursiveSuggestCorrections(target, maxDistance, "", set);
        return set;
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
        root.recursiveIterator("", words);
        return words.iterator();
    }

    /**
     * A recursively defined node class used to build the trie
     */
    private class LexiconNode implements Iterable<LexiconNode>, Comparable<LexiconNode> {
        /** The character this node represent */
        private char character;
        /** All the children of this node */
        private Map<Character, LexiconNode> children;
        /** Whether all the nodes leading up to this forms a word */
        private boolean isWord;

        private LexiconNode(char character) {
            this.character = character;
            children = new HashMap<>();
        }

        /**
         * Adds character in trie after the current node
         * 
         * @param character the character the new child should represent
         * 
         * @return the newly added node
         */
        private LexiconNode add(char character) {
            LexiconNode newNode = new LexiconNode(character);
            children.put(character, newNode);
            return newNode;
        }

        private LexiconNode get(char character) {
            return children.get(character);
        }

        /**
         * Recursively explores the trie to add to `words` all of the words in the trie
         * in alphabetical order
         * 
         * @param prefix the current potential word being formed
         * @param words  all words found
         */
        private void recursiveIterator(String prefix, List<String> words) {
            if (isWord) {
                words.add(prefix);
            }

            // Reached a leaf, cannot form another word
            if (children.isEmpty()) {
                return;
            }

            // Branching out to find more words
            for (LexiconNode child : this) {
                child.recursiveIterator(prefix + child.getCharacter(), words);
            }
        }

        /**
         * Recursively traverses te trie to explore possible corrections for a given
         * target string based on
         * `maxDistance`
         *
         * @param target      The target string for which corrections are suggested
         * @param maxDistance The maximum allowable different characters between a
         *                    potential correction and the target
         * @param prefix      The current prefix being formed during the recursive
         *                    traversal
         * @param corrections A set to store valid corrections for the target string
         */
        private void recursiveSuggestCorrections(
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
                if (isWord) {
                    corrections.add(prefix);
                }
                return;
            }

            // Determine the position of the next character in the prefix
            int nextCharacter = prefix.length();

            // Iterate through child nodes in the lexicon to explore possible corrections.
            for (LexiconNode child : this) {
                // Determine the adjustment to `maxDistance` based on the current character
                // match
                int toSubtract = child.getCharacter() != target.charAt(nextCharacter) ? 1 : 0;

                // Recursively call the method with updated parameters for the current child
                // node
                child.recursiveSuggestCorrections(
                        target,
                        maxDistance - toSubtract,
                        prefix + child.getCharacter(),
                        corrections);
            }
        }

        private LexiconNode remove(char character) {
            return children.remove(character);
        }

        /**
         * Recursively traverses the trie, exploring matches against a given pattern
         * using simple wildcard
         * characters (*, ?, -), within the trie represented by the current node.
         *
         * @param prefix  The possible match being formed
         * @param pattern The pattern to match against
         * @param matches A set to store valid matches for the given pattern.
         */
        private void recursiveMatchRegex(String prefix, String pattern, Set<String> matches) {
            // If the pattern is empty, check and add to matches if the current prefix is a
            // valid word
            if (pattern.length() == 0) {
                if (isWord) {
                    matches.add(prefix);
                }
                return;
            }

            char toMatch = pattern.charAt(0);

            // Any one character
            if (toMatch == '-') {
                for (LexiconNode child : this) {
                    child.recursiveMatchRegex(
                            prefix + child.character,
                            pattern.substring(1),
                            matches);
                }
            }
            // Any sequence of zero or more characters
            else if (toMatch == '*') {
                // Add to matches if the current prefix is valid and the pattern only contains
                // '*'
                if (isWord && pattern.length() == 1) {
                    matches.add(prefix);
                }

                // Recursively call the method with the remaining pattern after consuming '*' at
                // the current position
                this.recursiveMatchRegex(
                        prefix,
                        pattern.substring(1),
                        matches);

                // Iterate through child nodes to explore possible matches with the '*' wildcard
                // For every child, either consume '*' or not
                for (LexiconNode child : this) {
                    child.recursiveMatchRegex(
                            prefix + child.character,
                            pattern,
                            matches);
                }
            }
            // None or any character
            else if (toMatch == '?') {
                // Recursively call the method with the remaining pattern after consuming '?' at
                // the current position
                this.recursiveMatchRegex(
                        prefix,
                        pattern.substring(1),
                        matches);

                // Iterate through child nodes to explore possible matches with the '?'
                // wildcard
                for (LexiconNode child : this) {
                    child.recursiveMatchRegex(
                            prefix + child.character,
                            pattern.substring(1),
                            matches);
                }
            }
            // Match against the character in the current position of the pattern
            else {
                // Get the next node corresponding to the current character in the lexicon.
                LexiconNode nextNode = get(toMatch);

                // If a matching node is found, recursively call the method with the updated
                // prefix and pattern.
                if (nextNode != null) {
                    nextNode.recursiveMatchRegex(
                            prefix + toMatch,
                            pattern.substring(1),
                            matches);
                }
            }
        }

        public char getCharacter() {
            return character;
        }

        private boolean isWord() {
            return isWord;
        }

        private void setIsWord(boolean isWord) {
            this.isWord = isWord;
        }

        /**
         * Compares two LexiconNodes by the ASCII order of their characters
         * 
         * @param other a LexiconNode to compare to
         * 
         * @return > 0 if this is greater, 0 if this is equal, < 0 if this is less than
         *         other
         */
        @Override
        public int compareTo(LexiconNode other) {
            return character - other.getCharacter();
        }

        /**
         * Alphabetical iterator for the children nodes of the current node
         * 
         * @return alphabetical iterator of LexiconNodes
         */
        @Override
        public Iterator<LexiconNode> iterator() {
            // create list of LexiconNodes of all children
            List<LexiconNode> nodes = new ArrayList<>(children.values());
            // can sort the list because of the compareTo method defined above
            Collections.sort(nodes);
            // returning a regular ArrayList iterator
            return nodes.iterator();
        }
    }
}
