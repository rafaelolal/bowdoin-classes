package WorldOfLoot.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import WorldOfLoot.MagicContainer;
import WorldOfLoot.Player;

public class PlayerTests {
    @Test
    public void put() {
        Player player = new Player("Test");
        MagicContainer<String> container = new MagicContainer<>("Test Container");
        player.put(container, "Test Item", 0);
        assertEquals(container.toString().split("\n").length, 4);
    }

    @Test
    public void open() {
        Player player = new Player("Test");
        MagicContainer<String> container = new MagicContainer<>("Test Container");
        assertEquals(container.getIsOpen(), false);
        player.open(container);
        assertEquals(container.getIsOpen(), true);
    }

    @Test
    public void close() {
        Player player = new Player("Test");
        MagicContainer<String> container = new MagicContainer<>("Test Container");
        container.open();
        assertEquals(container.getIsOpen(), true);
        player.close(container);
        assertEquals(container.getIsOpen(), false);
    }
}
