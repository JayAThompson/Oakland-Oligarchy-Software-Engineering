import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.*;

public class TileTests {
    @Test
    public void testTile() {
        Tile test = new Tile(new JPanel(), "Test Tile", 100, 1, 2500);
        assertEquals(test.propertyName, "Test Tile");
        assertEquals(test.propertyValue, 100);
        assertEquals(test.tileIndex, 1);
        assertEquals(test.rent, 2500);
    }

    @Test
    public void testGetPanel() {
        JPanel testPanel = new JPanel();
        Tile test = new Tile(testPanel, "Test Tile", 100, 1, 2500);
        assertEquals(test.getPanel(), testPanel);
    }

    @Test
    public void testAddPlayer() {
        Tile test = new Tile(new JPanel(), "Test Tile", 100, 1, 2500);
        Player player = new Player("Test Player", 10000, "blue");
        test.addPlayer(player);
        assertEquals(test.players.size(), 1);
    }

    @Test
    public void testSetOwner() {
        Tile test = new Tile(new JPanel(), "Test Tile", 100, 1, 2500);
        Player testOwner = new Player("Test Owner", 10000, "blue");
        test.setOwner(testOwner);
        assertEquals(test.owner, testOwner);
    }

    @Test
    public void testRemovePlayer() {
        Tile test = new Tile(new JPanel(), "Test Tile", 100, 1, 2500);
        Player player = new Player("Test Player", 10000, "blue");
        test.addPlayer(player);
        assertEquals(test.players.size(), 1);
        test.removePlayer(player);
        assertEquals(test.players.size(), 0);
    }
}
