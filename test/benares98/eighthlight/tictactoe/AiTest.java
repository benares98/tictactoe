package benares98.eighthlight.tictactoe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import benares98.eighthlight.tictactoe.Game.piece;

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
		piece[] board = new piece[]{
				piece.x, piece.o, piece.x,
				piece.o, piece.x, piece.o,
				piece.o, piece.o, piece._
		};
		assertEquals(8, Ai.suggestedMove(piece.x, board));
		
		//Weighted pos strategy would choose 2 instead of 3 which is the better move
		board = new piece[]{
				piece.o, piece.x, piece._,
				piece._, piece.o, piece._,
				piece._, piece._, piece.x
		};
		assertEquals(3, Ai.suggestedMove(piece.o, board));
		
		//Weighted pos strategy would choose 0 instead of the side pos.
		//If 0 chosen, player x could choose 8 which will give x an advantage.
		board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece.o, piece._,
				piece.x, piece._,	piece._
		};
		assertFalse(0 == Ai.suggestedMove(piece.o, board));
		
		//Weighted pos strategy would choose 4.  Should use minimax to determine next pos.
		board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece._, piece._,
				piece._, piece._, piece._
		};
		assertFalse(4 == Ai.suggestedMove(piece.o, board));
	}

}
