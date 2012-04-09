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
		
		try{
			Ai.suggestedMove(null, null);
			fail("Should have thrown an IllegalArgumentException for null arguments");
		}catch(Exception e){
			assert(e instanceof IllegalArgumentException);
		}
		Player x = new Player('X');
		Player o = new Player('O');
		Player[] board = new Player[]{
				x, o, x,
				o, x, o,
				o, o, null
		};
		assertEquals(8, Ai.suggestedMove(x, board));
		
		//Weighted pos strategy would choose 2 instead of 3 which is the better move
		board = new Player[]{
				o, 	  x,    null,
				null, o,    null,
				null, null, x
		};
		assertEquals(3, Ai.suggestedMove(o, board));
		
		//Weighted pos strategy would choose 0 instead of the side pos.
		//If 0 chosen, player x could choose 8 which will give x an advantage.
		board = new Player[]{
				null,	null,	x,
				null,	o,		null,
				x,		null,	null
		};
		assertFalse(0 == Ai.suggestedMove(o, board));
		
		//Weighted pos strategy would choose 4.  Should use minimax to determine next pos.
		board = new Player[]{
				null,	null,	x,
				null,	null,	null,
				null,	null,	null
		};
		assertFalse(4 == Ai.suggestedMove(o, board));
	}

}
