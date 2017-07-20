import org.junit.*;
import static org.junit.Assert.*;

public class PlayerTest{
	@Test
	public void testGetMoney(){
		Player player = new Player("Atta", 1000000, "blue");
		assertEquals(player.getMoney(), 1000000);
	}


	@Test
	public void testGetName(){
		Player player = new Player("Atta", 1000000, "blue");
		assertEquals(player.getName(), "Atta");
	}

	@Test
	public void testSetMoney(){
		Player player = new Player("Atta", 1000000, "blue");
		player.setMoney(0);
		assertEquals(player.getMoney(), 0);
	}

	@Test
	public void testSetName(){
		Player player = new Player("Atta", 1000000, "blue");
		player.setName("Bill");
		assertEquals(player.getName(), "Bill");
	}

	@Test
	public void testInJail() {
		Player player = new Player("Test", 10000, "red");
		assertFalse(player.isInJail());
		player.setInJail(true);
		assertTrue(player.isInJail());
	}

	@Test
	public void testJailedTurns() {
		Player player = new Player("Test", 10000, "red");
		player.setInJail(true);
		assertTrue(player.isInJail());
		for (int i = 3; i >= 0; i--) {
			assertEquals(player.getRemainingJailedTurns(), i);
			player.incrementJailedTurns();
		}
		assertFalse(player.isInJail());
	}
}
