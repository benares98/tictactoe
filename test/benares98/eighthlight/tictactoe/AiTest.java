package benares98.eighthlight.tictactoe;

import static org.junit.Assert.*;

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
		
		board = new piece[]{
				piece.o, piece.x, piece._,
				piece.o, piece.o, piece.x,
				piece._, piece._, piece.x
		};
		int actual = Ai.suggestedMove(piece.o, board);
		assertTrue("Actual is "+actual, 6==actual);
	}

	@Test
	public void testSuggestedMove2(){

		//Weighted pos strategy would choose 0 instead of the side pos.
		//If 0 or 8 chosen, player x could choose the other which will give x an advantage.
		piece[] board = new piece[]{
				piece._, piece._, piece.x,
				piece._, piece.o, piece._,
				piece.x, piece._, piece._
		};
		int actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual, 1==actual||3==actual||5==actual||7==actual);
		
		board = new piece[]{
				piece.x, piece._, piece._,
				piece._, piece.o, piece._,
				piece._, piece._, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,1==actual||3==actual||5==actual||7==actual);
		
		board = new piece[]{
				piece.x, piece._, piece._,
				piece.o, piece.o, piece.x,
				piece._, piece._, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,2==actual);
		
		board = new piece[]{
				piece.x, piece._, piece.o,
				piece.o, piece.o, piece.x,
				piece.x, piece._, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,7==actual);
	}
	
	@Test
	public void testSuggestedMove3(){
		//rotation
		piece[] board = new piece[]{
				piece.x, piece.o, piece.x,
				piece._, piece.o, piece._,
				piece.x, piece.x, piece.o
		};
		int actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,3==actual);
		
		board = new piece[]{
				piece.x, piece._, piece.x,
				piece.x, piece.o, piece.o,
				piece.o, piece._, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,1==actual);
		
		board = new piece[]{
				piece.o, piece.x, piece.x,
				piece._, piece.o, piece._,
				piece.x, piece.o, piece.x
		};
		actual = Ai.suggestedMove(piece.o, board);
		assertTrue("actual is "+actual,5==actual);
	}
	
	@Test
	public void testBuildGameTree(){
		
		piece[] board = new piece[]{
				piece.x, piece._, piece.o,
				piece.o, piece.o, piece.x,
				piece.x, piece.o, piece.x	
		};
		
		Node root = Ai.buildGameTree(piece.x, board);
		List<Node> children = root.getChildren();
		assertEquals(1, children.size());
		assertEquals(1, children.get(0).getPosition());
		assertEquals(status.tie, children.get(0).getStatus());
		assertEquals(null, children.get(0).getChildren());
		
		board = new piece[]{
				piece.x, piece._, piece.o,
				piece.o, piece.o, piece.x,
				piece.x, piece._, piece.x	
		};
		
		root = Ai.buildGameTree(piece.x, board);
		children = root.getChildren();
		assertEquals(2, children.size());
		assertEquals(1, children.get(0).getPosition());
		assertEquals(status.atPlay, children.get(0).getStatus());
				
		List<Node>children2 = children.get(0).getChildren();
		assertEquals(1, children2.size());
		assertEquals(7, children2.get(0).getPosition());
		assertEquals(status.tie, children2.get(0).getStatus());
		assertEquals(null, children2.get(0).getChildren());
		
		assertEquals(7, children.get(1).getPosition());
		assertEquals(status.x, children.get(1).getStatus());
		assertEquals(null, children.get(1).getChildren());
	}
}
