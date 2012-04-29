package benares98.eighthlight.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static void main(String[] args){
		do playerSelect();
		while(playAgain());
	}

	private static boolean playAgain() {
		do{
			System.out.println("Play again?  Y/N");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = br.readLine();
				if ("Y".equalsIgnoreCase(input)) return true;
				if ("N".equalsIgnoreCase(input)) return false;
				System.out.println("Could not understand.");
			} catch (IOException e) {
				System.out.println("IO error trying to read your input.");
				System.exit(1);
			}
		}while(true);
	}

	private static void playerSelect() {
		boolean userSelected = false;
		int chosen = -1;
		do{
			System.out.println("Press  1  to be the first player.  Press 2 to be the second player.");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = br.readLine();
				chosen = Integer.valueOf(input);
				if (chosen == 1 || chosen == 2) userSelected = true;
			} catch (IOException e) {
				System.out.println("IO error trying to read your input.");
				System.exit(1);
			} catch (NumberFormatException e) {System.out.println("Couldn't understand your input.");}
		}while(!userSelected);
		
		piece[] board = new piece[]{
				piece._, piece._, piece._,
				piece._, piece._, piece._,
				piece._, piece._, piece._	
		};
		if (chosen == 1){
			play(true, piece.x, board);
		}
		if (chosen == 2){
			play(false, piece.o, board);
		}
	}
	
	private static void play(boolean user, piece player, piece[] board){
		status winState = getWinningState(player);
		status currentState = status.atPlay;
		int chosen = -1;
		System.out.println(pretty(board));
		if (user) chosen = userSelected(board);
		else {
			chosen = Ai.suggestedMove(player, board);
			System.out.println("Computer has chosen its move.");
		}
		
		board[chosen] = player;
		currentState = getState(board);
		if (currentState == status.atPlay) play(!user, rival(player), board);
		else{
			System.out.println(pretty(board));
			if (status.tie == currentState) System.out.println("The game ends with a tie.");
			else if (currentState == winState && user)System.out.println("You win!");
			else System.out.println("You lose!");
		}
	}

	private static int userSelected(piece[] board) {
		do {
			System.out.println("Please select a position.");			
			Set<Integer> available = Game.availablePositions(board);
			StringBuilder sb = new StringBuilder();
			for (int position : available) sb.append(' ').append(position);
			
			System.out.println("The available positions are:" + sb);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = br.readLine();
				int chosen = Integer.valueOf(input);
				if (chosen > -1 && chosen < 9)
					if (available.contains(chosen)) return chosen;
					else System.out.println("Position "+chosen+ " is not available.");
				System.out.println(pretty(board));
			} catch (IOException e) {
				System.out.println("IO error trying to read your input.");
				System.exit(1);
			} catch (NumberFormatException e) {System.out.println("Couldn't understand your input.");}
		} while (true);
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
	
	public static String pretty(piece[] board){
		StringBuilder sb = new StringBuilder();
		sb.append(board[0]).append(board[1]).append(board[2]).append('\n');
		sb.append(board[3]).append(board[4]).append(board[5]).append('\n');
		sb.append(board[6]).append(board[7]).append(board[8]).append('\n');
		
		return sb.toString();
	}
}
