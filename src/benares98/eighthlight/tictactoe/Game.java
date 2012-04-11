package benares98.eighthlight.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import benares98.eighthlight.tictactoe.Game.piece;

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
			for(Integer pos:winPos){
				if (player != board[pos]) win = false;
			}
			if (win) return status.valueOf(player.toString());
		}
		
		player = piece.o;
		
		for(Set<Integer> winPos:scoringPositions){
			boolean win = true;
			for(Integer pos:winPos){
				if (player != board[pos]) win = false;
			}
			if (win) return status.valueOf(player.toString());
		}
		
		for(piece pos:board) if (piece._ == pos) return status.atPlay;
		return status.tie;
	}

	public static void main(String[] args){
		boolean userSelected = false;
		do{
			System.out.println("Press  1  to be the first player.  Press 2 to be the second player.");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = br.readLine();
				int chosen = Integer.valueOf(input);
				if (chosen <= 2 && chosen > 0){
					//TODO
				}
			} catch (IOException e) {
				System.out.println("IO error trying to read your input.");
				System.exit(1);
			} catch (NumberFormatException e) {
				System.out.println("Couldn't understand your input.");
			}
		}while(!userSelected);
	}
	
	public static Set<Integer> availablePositions(piece[] board){
		Set<Integer>available = new HashSet<Integer>(4);
		for(int i=0; i<board.length; i++) if (piece._ == board[i]) available.add(i);
		return available;
	}

	public static Game.status getWinningState(piece player) {
		return Game.status.valueOf(player.toString());
	}
	
	public static String pretty(piece[] board){
		StringBuilder sb = new StringBuilder();
		sb.append(board[0]).append(board[1]).append(board[2]).append('\n');
		sb.append(board[3]).append(board[4]).append(board[5]).append('\n');
		sb.append(board[6]).append(board[7]).append(board[8]).append('\n');
		
		return sb.toString();
	}
}
