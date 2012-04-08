package benares98.eighthlight.tictactoe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSuggestedMove() {
		Player user = null;
		Player enemy = null;
		Board board = null;
		assertEquals(0, Ai.suggestedMove(user, enemy, board));
	}

}
