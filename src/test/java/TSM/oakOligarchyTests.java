import org.junit.*;
import static org.junit.Assert.*;

public class oakOligarchyTests{
	@Test
	public void testRollDice() {
		System.out.println("testRollDice()");
		int result = oakOligarchy.rollDice();
		assertTrue(result >= 1 && result <= 6);
	}
}
