package lexicon;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LexiconTest {
	
	
	private Lexicon lx;
	
	@BeforeEach
	public void setup() {
		lx = new LexiconTrie();
	}
	
	
	@Test
	public void testEmptyLexicon() {
		Assertions.assertEquals(0,  lx.numWords());
		Assertions.assertFalse(lx.containsWord("cat"));
		Assertions.assertFalse(lx.containsWord("dog"));
	}
	
	
	@Test
	public void testAddWords() {
		Assertions.assertEquals(0,  lx.numWords());
		
		Assertions.assertTrue(lx.addWord("cat"));
		
		Assertions.assertEquals(1,  lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("cat"));
		
		Assertions.assertFalse(lx.containsWord("ca"));
		Assertions.assertFalse(lx.containsWord("c"));
		Assertions.assertFalse(lx.containsWord("catherine"));
		
		
		Assertions.assertFalse(lx.addWord("cat"));
		
		Assertions.assertEquals(1,  lx.numWords());
		
		Assertions.assertTrue(lx.addWord("dog"));
		
		Assertions.assertEquals(2,  lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("dog"));

		
		Assertions.assertTrue(lx.addWord("goat"));
		
		Assertions.assertEquals(3,  lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("dog"));
		Assertions.assertTrue(lx.containsWord("goat"));
		
	}
	
	@Test
	public void testPrefix() {

		
		Assertions.assertFalse(lx.containsPrefix("cat"));
		Assertions.assertFalse(lx.containsPrefix("ca"));
		Assertions.assertFalse(lx.containsPrefix("c"));

		
		Assertions.assertEquals(0,  lx.numWords());
		
		Assertions.assertTrue(lx.addWord("cat"));
		
		Assertions.assertEquals(1,  lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("cat"));
		
		
		Assertions.assertTrue(lx.containsPrefix("cat"));
		Assertions.assertTrue(lx.containsPrefix("ca"));
		Assertions.assertTrue(lx.containsPrefix("c"));
		
		
		Assertions.assertTrue(lx.removeWord("cat"));
		Assertions.assertTrue(lx.addWord("cabbage"));
		
		Assertions.assertTrue(lx.containsPrefix("cab"));
		Assertions.assertFalse(lx.containsPrefix("cabbages"));
		
		
		
	}
	
	@Test
	public void testAddOverlappingWords() {
		Assertions.assertEquals(0,  lx.numWords());
		
		Assertions.assertFalse(lx.containsWord("cat"));
		Assertions.assertFalse(lx.containsWord("catherine"));
		
		Assertions.assertTrue(lx.addWord("catherine"));
		
		Assertions.assertFalse(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("catherine"));
		
		Assertions.assertTrue(lx.addWord("cat"));
		
		Assertions.assertTrue(lx.containsWord("catherine"));
		Assertions.assertTrue(lx.containsWord("cat"));
		
		
		Assertions.assertFalse(lx.addWord("catherine"));
		
		Assertions.assertTrue(lx.addWord("catastrophe"));
		
		Assertions.assertEquals(3, lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("catherine"));
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("catastrophe"));
		
	}
	
	
	@Test
	public void testRemoveWords() {
		lx.addWord("goat");
		lx.addWord("horse");
		lx.addWord("cat");
		lx.addWord("duck");
		
		Assertions.assertEquals(4,  lx.numWords());
		
		
		// test that garbage removals don't change anything
		Assertions.assertFalse(lx.removeWord("cow")); 
		
		Assertions.assertFalse(lx.removeWord("catherine"));
		
		Assertions.assertEquals(4,  lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("goat"));
		Assertions.assertTrue(lx.containsWord("horse"));
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("duck"));
		
		
		// but that non-garbage removals do change, and in the expected manner

		Assertions.assertTrue(lx.removeWord("goat")); 
		
		Assertions.assertEquals(3,  lx.numWords());
		
		Assertions.assertFalse(lx.containsWord("goat"));
		Assertions.assertTrue(lx.containsWord("horse"));
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("duck"));
		
		Assertions.assertTrue(lx.removeWord("horse")); 
		
		Assertions.assertEquals(2,  lx.numWords());
		
		Assertions.assertFalse(lx.containsWord("goat"));
		Assertions.assertFalse(lx.containsWord("horse"));
		Assertions.assertTrue(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("duck"));
		
		Assertions.assertTrue(lx.removeWord("cat")); 
		
		Assertions.assertEquals(1,  lx.numWords());
		
		Assertions.assertFalse(lx.containsWord("goat"));
		Assertions.assertFalse(lx.containsWord("horse"));
		Assertions.assertFalse(lx.containsWord("cat"));
		Assertions.assertTrue(lx.containsWord("duck"));
		
		
		Assertions.assertTrue(lx.removeWord("duck")); 
		
		Assertions.assertEquals(0,  lx.numWords());
		
		Assertions.assertFalse(lx.containsWord("goat"));
		Assertions.assertFalse(lx.containsWord("horse"));
		Assertions.assertFalse(lx.containsWord("cat"));
		Assertions.assertFalse(lx.containsWord("duck"));

		
		// duplicate remove shouldn't impact anything
		Assertions.assertFalse(lx.removeWord("duck"));
		Assertions.assertEquals(0,  lx.numWords());
		Assertions.assertFalse(lx.containsWord("duck"));
		
		
	}
	
	@Test
	public void testRemoveComplicated() {
		
		lx.addWord("cat");
		lx.addWord("catherine");
		lx.addWord("catastrophe");
		lx.addWord("dog");
		

		// removing a word that is the prefix of another shouldn't break those longer words
		// or anything else
		Assertions.assertTrue(lx.removeWord("cat"));
		Assertions.assertEquals(3, lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("catherine"));
		Assertions.assertTrue(lx.containsWord("catastrophe"));
		Assertions.assertTrue(lx.containsWord("dog"));
		
		// re-adding after remove should be fine
		
		Assertions.assertTrue(lx.addWord("cat"));
		Assertions.assertEquals(4, lx.numWords());
		
		Assertions.assertTrue(lx.containsWord("cat"));		
		Assertions.assertTrue(lx.containsWord("catherine"));
		Assertions.assertTrue(lx.containsWord("catastrophe"));
		Assertions.assertTrue(lx.containsWord("dog"));
		
		Assertions.assertFalse(lx.addWord("cat"));
		Assertions.assertEquals(4, lx.numWords());
	}
	
	
	@Test
	public void testIterator() {
		
		lx.addWord("cat");
		lx.addWord("goat");
		lx.addWord("zebra");
		lx.addWord("kangaroo");
		lx.addWord("shark");
		lx.addWord("panda");
		
		
		List<String> allWordFound = new ArrayList<>();
		
		for (String word: lx) {
			allWordFound.add(word);
		}
		
		Assertions.assertEquals(6, allWordFound.size());
		
		// existential checks
		Assertions.assertTrue(allWordFound.contains("cat"));
		Assertions.assertTrue(allWordFound.contains("goat"));
		Assertions.assertTrue(allWordFound.contains("zebra"));
		Assertions.assertTrue(allWordFound.contains("kangaroo"));
		Assertions.assertTrue(allWordFound.contains("shark"));
		Assertions.assertTrue(allWordFound.contains("panda"));
		
		
		// ordering checks
		Assertions.assertEquals("cat", allWordFound.get(0));
		Assertions.assertEquals("goat", allWordFound.get(1));
		Assertions.assertEquals("kangaroo", allWordFound.get(2));
		Assertions.assertEquals("panda", allWordFound.get(3));
		Assertions.assertEquals("shark", allWordFound.get(4));
		Assertions.assertEquals("zebra", allWordFound.get(5));
		
		
	}
	
	@Test
	public void testIteratorOrder() {
		lx.addWord("a");
		lx.addWord("as");
		lx.addWord("an");
		lx.addWord("ass");
		lx.addWord("apple");
		lx.addWord("application");
		lx.addWord("applied");
		lx.addWord("banana");
		
		List<String> allWordFound = new ArrayList<>();
		
		for (String word: lx) {
			allWordFound.add(word);
		}
		
		Assertions.assertEquals("a", allWordFound.get(0));
		Assertions.assertEquals("an", allWordFound.get(1));
		Assertions.assertEquals("apple", allWordFound.get(2));
		Assertions.assertEquals("application", allWordFound.get(3));
		Assertions.assertEquals("applied", allWordFound.get(4));
		Assertions.assertEquals("as", allWordFound.get(5));
		Assertions.assertEquals("ass", allWordFound.get(6));
		Assertions.assertEquals("banana", allWordFound.get(7));
		
		
	}
	
	
}

