package benares98.eighthlight.tictactoe;

import java.util.HashSet;
import java.util.Set;

public class Game {
	public static final Set<Set<Integer>> scoringPositions = new HashSet<Set<Integer>>();
	public static enum status{atPlay, x, o, tie};
	public static enum piece{x, o,_};
	
	static{
		Set<Integer> h1 = new HashSet<Integer>();
		h1.add(0);h1.add(1);h1.add(2);
		scoringPositions.add(h1);
		
		h1 = new HashSet<Integer>();
		h1.add(3);h1.add(4);h1.add(5);
		scoringPositions.add(h1);

		h1 = new HashSet<Integer>();
		h1.add(6);h1.add(7);h1.add(8);
		scoringPositions.add(h1);

		h1 = new HashSet<Integer>();
		h1.add(0);h1.add(3);h1.add(6);
		scoringPositions.add(h1);
		
		h1 = new HashSet<Integer>();
		h1.add(1);h1.add(4);h1.add(7);
		scoringPositions.add(h1);
	
		h1 = new HashSet<Integer>();
		h1.add(2);h1.add(5);h1.add(8);
		scoringPositions.add(h1);
		
		h1 = new HashSet<Integer>();
		h1.add(0);h1.add(4);h1.add(8);
		scoringPositions.add(h1);
		
		h1 = new HashSet<Integer>();
		h1.add(2);h1.add(4);h1.add(6);
		scoringPositions.add(h1);
	}
	
	public static status getState(piece[] board) {
		piece player = piece.x;
		
		for(Set<Integer> winPos:scoringPositions){
			boolean win = true;
			for(Integer pos:winPos)	if (player != board[pos]) win = false;
			if (win) return status.valueOf(player.toString());
		}
		
		player = piece.o;
		
		for(Set<Integer> winPos:scoringPositions){
			boolean win = true;
			for(Integer pos:winPos) if (player != board[pos]) win = false;
			if (win) return status.valueOf(player.toString());
		}
		
		for(piece pos:board) if (piece._ == pos) return status.atPlay;
		return status.tie;
	}

	public static Set<Integer> availablePositions(piece[] board){
		Set<Integer>available = new HashSet<Integer>(4);
		for(int i=0; i<board.length; i++) if (piece._ == board[i]) available.add(i);
		return available;
	}

	public static Game.status getWinningState(piece player) {
		if (null==player)throw new IllegalArgumentException("Argument must not be null");
		if (piece._==player)throw new IllegalArgumentException("Argument must be either x or o");
		return Game.status.valueOf(player.toString());
	}
	
	public static Game.piece rival(Game.piece player) {
		if (Game.piece.x == player) return Game.piece.o; 
		else if(Game.piece.o == player) return Game.piece.x;
		else throw new IllegalArgumentException("Cannot use "+player+" to determine rival.");
	}
}
