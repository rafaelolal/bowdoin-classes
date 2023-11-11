import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class LexiconTrie implements Lexicon {
    LexiconNode root;
    int wordCount;

    public LexiconTrie() {
        root = new LexiconNode(' ');
        wordCount = 0;
    }

    public boolean containsWord(String word) {
        LexiconNode finalNode = getFinalNodeOf(word);
        return finalNode != null && finalNode.isWord;
    }

    public boolean containsPrefix(String prefix) {
        return getFinalNodeOf(prefix) != null;
    }

    public LexiconNode getFinalNodeOf(String str) {
        LexiconNode curNode = root;
        for (char character : str.toCharArray()) {
            if (curNode.get(character) == null) {
                return null;
            }

            curNode = curNode.get(character);
        }

        return curNode;
    }

    public boolean addWord(String word) {
        LexiconNode curNode = root;

        // iterating through all but the last char
        for (char character : word.substring(0, word.length() - 1).toCharArray()) {
            LexiconNode nextNode = curNode.get(character);
            if (nextNode == null) {
                nextNode = curNode.add(character);
            }

            curNode = nextNode;
        }

        // determining if word was already present or newly added
        char finalChar = word.charAt(word.length() - 1);
        LexiconNode finalNode = curNode.get(finalChar);
        // TODO make this look better
        if (finalNode != null) {
            if (finalNode.isWord) {
                return false;
            }
            finalNode.isWord = true;
            wordCount++;
            return true;
        }

        finalNode = curNode.add(finalChar);
        finalNode.isWord = true;
        wordCount++;
        return true;
    }

    public int addWordsFromFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            int wordsAdded = 0;
            while (scan.hasNext()) {
                addWord(scan.next());
                wordsAdded++;
            }

            return wordsAdded;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    // TODO make this better
    public boolean removeWord(String word) {
        Stack<LexiconNode> nodesVisited = new Stack<>();
        nodesVisited.push(root);
        LexiconNode curNode = root;
        for (char character : word.toCharArray()) {
            LexiconNode nextNode = curNode.get(character);
            if (nextNode == null) {
                return false;
            }
            curNode = nextNode;
            nodesVisited.push(curNode);
        }

        if (!curNode.isWord) {
            return false;
        }

        curNode.isWord = false;
        if (curNode.children.size() != 0) {
            wordCount--;
            return true;
        }

        // TODO can't handle one character words
        char toRemove = nodesVisited.pop().character;
        LexiconNode removeFrom = nodesVisited.pop();

        while (!removeFrom.isWord && !nodesVisited.isEmpty()) {
            toRemove = removeFrom.character;
            removeFrom = nodesVisited.pop();
        }

        removeFrom.remove(toRemove);
        wordCount--;
        return true;
    }

    public Set<String> matchRegex(String pattern) {
        return new HashSet<String>();
    }

    public int numWords() {
        return wordCount;
    }

    public Set<String> suggestCorrections(String target, int maxDistance) {
        return new HashSet<String>();
    }

    @Override
    public Iterator<String> iterator() {
        return new LexiconTrieIterator();
    }

    private class LexiconTrieIterator implements Iterator<String> {

        public LexiconTrieIterator() {

        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            return "Hi!";
        }
    }

    private class LexiconNode implements Iterable<LexiconNode> {
        private char character;
        private Map<Character, LexiconNode> children;
        private boolean isWord;

        private LexiconNode(char character) {
            this.character = character;
            children = new HashMap<>();
        }

        private LexiconNode add(char character) {
            LexiconNode newNode = new LexiconNode(character);
            children.put(character, newNode);
            return newNode;
        }

        private LexiconNode get(char character) {
            return children.get(character);
        }

        private LexiconNode remove(char character) {
            return children.remove(character);
        }

        @Override
        public Iterator<LexiconNode> iterator() {
            return new LexiconNodeIterator();
        }

        private class LexiconNodeIterator implements Iterator<LexiconNode> {
            private char curCharacter;
            private final char[] ALPHABET = {
                    'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z' };

            private LexiconNodeIterator() {
                for (char character : ALPHABET) {
                    if (LexiconNode.this.get(character) != null) {
                        curCharacter = character;
                        return;
                    }
                }

                curCharacter = ' ';
            }

            @Override
            public boolean hasNext() {
                return LexiconNode.this.get(curCharacter) != null;
            }

            @Override
            public LexiconNode next() {
                char prevCharacter = curCharacter;
                for (char character : ALPHABET) {
                    if (character > prevCharacter && LexiconNode.this.get(prevCharacter) != null) {
                        curCharacter = character;
                    }
                }

                return LexiconNode.this.get(prevCharacter);
            }
        }
    }
}
