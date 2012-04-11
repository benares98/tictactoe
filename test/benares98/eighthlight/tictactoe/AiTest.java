package benares98.eighthlight.tictactoe;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import benares98.eighthlight.tictactoe.Game.piece;
import benares98.eighthlight.tictactoe.Game.status;

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
		
		//Weighted pos strategy would choose 4.  Should use minimax to determine next pos.
		board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece._, piece._,
				piece._, piece._, piece._
		};
		assertFalse(4 == Ai.suggestedMove(piece.o, board));
	}

	@Test
	public void testSuggestedMove2(){

		//Weighted pos strategy would choose 0 instead of the side pos.
		//If 0 chosen, player x could choose 8 which will give x an advantage.
		piece[] board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece.o, piece._,
				piece.x, piece._, piece._
		};
		int actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual, 1==actual||3==actual||5==actual||7==actual);
		
		System.out.println("----");
		board = new piece[]{
				piece.x, piece._, piece._,
				piece._, piece.o, piece._,
				piece._, piece._, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,1==actual||3==actual||5==actual||7==actual);
	}
	
	@Test
	public void testCountStateAtDepth(){
		
		piece[] board = new piece[]{
				piece.x, piece._, piece._,
				piece._, piece.o, piece._,
				piece._, piece._, piece.x	
		};
		
		Node root = Ai.buildGameTree(piece.o, board);
	}
}
