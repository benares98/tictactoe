package benares98.eighthlight.tictactoe;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import benares98.eighthlight.tictactoe.Game.piece;
import benares98.eighthlight.tictactoe.Game.status;

public class GameTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetState() {
		
		piece[] board = new piece[]{
				piece._, piece._, piece._,
				piece._, piece._, piece._,
				piece._, piece._, piece._,
		};
		assertEquals(status.atPlay, Game.getState(board));
		
		board = new piece[]{
				piece.x, piece.x, piece.x,
				piece._, piece._, piece._,
				piece._, piece._, piece._
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece._, piece._, piece._,
				piece.x, piece.x, piece.x,				
				piece._, piece._, piece._
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece._, piece._, piece._,
				piece._, piece._, piece._,
				piece.x, piece.x, piece.x,
		};
		assertEquals(status.x, Game.getState(board));
		

		board = new piece[]{
				piece.x, piece._, piece._,
				piece.x, piece._, piece._,
				piece.x, piece._, piece._,
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece._, piece.x, piece._,
				piece._, piece.x, piece._,
				piece._, piece.x, piece._,
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece._, piece.x,
				piece._, piece._, piece.x,
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece.x, piece._, piece._,
				piece._, piece.x, piece._,
				piece._, piece._, piece.x,
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece.x, piece._,
				piece.x, piece._, piece._,
		};
		assertEquals(status.x, Game.getState(board));
		
		board = new piece[]{
				piece.o, piece.o, piece.o,
				piece._, piece._, piece._,
				piece._, piece._, piece._,
		};
		assertEquals(status.o, Game.getState(board));
		
		board = new piece[]{
				piece.o, piece.x, piece.o,
				piece.x, piece.o, piece.x,
				piece.x, piece.o, piece.x,
		};
		assertEquals(status.tie, Game.getState(board));
	}

	@Test
	public void testAvailablePositions() {
		piece[] board = new piece[]{
				piece.o, piece.x, piece.o,
				piece.x, piece.o, piece.x,
				piece.x, piece.o, piece.x,
		};
		assertEquals(0, Game.availablePositions(board).size());
		
		board = new piece[]{
				piece._, piece.x, piece.o,
				piece.x, piece.o, piece.x,
				piece.x, piece.o, piece.x,
		};
		
		Set actual = Game.availablePositions(board);
		assertTrue(actual.contains(0));
		assertEquals(1, actual.size());
		
		board = new piece[]{
				piece._, piece._, piece.o,
				piece.x, piece.o, piece.x,
				piece.x, piece.o, piece.x,
		};
		
		actual = Game.availablePositions(board);
		assertTrue(actual.contains(0));
		assertTrue(actual.contains(1));
		assertEquals(2, actual.size());
		
		board = new piece[]{
				piece._, piece._, piece.o,
				piece.x, piece.o, piece.x,
				piece.x, piece.o, piece._,
		};
		
		actual = Game.availablePositions(board);
		assertTrue(actual.contains(0));
		assertTrue(actual.contains(1));
		assertTrue(actual.contains(8));
		assertEquals(3, actual.size());
		assertFalse(actual.contains(2));
		assertFalse(actual.contains(3));
		assertFalse(actual.contains(4));
		assertFalse(actual.contains(5));
		assertFalse(actual.contains(6));
		assertFalse(actual.contains(7));
		
	}

	@Test
	public void testGetWinningState() {
		assertEquals(status.o, Game.getWinningState(piece.o));
		assertEquals(status.x, Game.getWinningState(piece.x));
		
		try{
			Game.getWinningState(piece._);
			fail("Should have thrown an IllegalArgumentException.  piece._ represents an empty space.");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
		
		try{
			Game.getWinningState(null);
			fail("Should have thrown an IllegalArgumentException for null argument");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
}
