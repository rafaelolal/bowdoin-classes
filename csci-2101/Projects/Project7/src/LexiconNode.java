import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A recursively defined node class used to build the trie
 */
public class LexiconNode implements Iterable<LexiconNode>, Comparable<LexiconNode> {
    /** The character this node represent */
    private char character;
    /** All the children of this node */
    private Map<Character, LexiconNode> children;
    /** Whether all the nodes leading up to this forms a word */
    private boolean isWord;

    public LexiconNode(char character) {
        this.character = character;
        children = new HashMap<>();
    }

    /**
     * Adds character in trie after the current node
     * 
     * @param character the character the new child should represent
     * 
     * @return the newly added node. This behavior helps when adding a
     *         new word
     */
    public LexiconNode add(char character) {
        LexiconNode newNode = new LexiconNode(character);
        children.put(character, newNode);
        return newNode;
    }

    public LexiconNode get(char character) {
        return children.get(character);
    }

    public LexiconNode remove(char character) {
        return children.remove(character);
    }

    public char getCharacter() {
        return character;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
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
