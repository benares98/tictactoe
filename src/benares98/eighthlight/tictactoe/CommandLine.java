package benares98.eighthlight.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import benares98.eighthlight.tictactoe.Game.piece;
import benares98.eighthlight.tictactoe.Game.status;

public class CommandLine {
	public static piece asFirstPlayer = piece.x;
	public static piece asSecondPlayer = piece.o;
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
		if (chosen == 1)play(asFirstPlayer, board);
		else play(asSecondPlayer, board);
	}
	
	private static void play(piece player, piece[] board){
		status winState = Game.getWinningState(player);
		status currentState = status.atPlay;
		int chosen = -1;
		System.out.println(pretty(board));
		if (isUser(player)) chosen = userSelected(board);
		else {
			chosen = Ai.suggestedMove(player, board);
			System.out.println("Computer has chosen its move.");
		}
		
		board[chosen] = player;
		currentState = Game.getState(board);
		if (currentState == status.atPlay) play(Game.rival(player), board);
		else{
			System.out.println(pretty(board));
			if (status.tie == currentState) System.out.println("The game ends with a tie.");
			else if (currentState == winState && isUser(player))System.out.println("You win!");
			else System.out.println("You lose!");
		}
	}

	public static boolean isUser(piece player) {
		return player == piece.x;
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
	
	
	public static String pretty(piece[] board){
		StringBuilder sb = new StringBuilder();
		sb.append(board[0]).append(board[1]).append(board[2]).append('\n');
		sb.append(board[3]).append(board[4]).append(board[5]).append('\n');
		sb.append(board[6]).append(board[7]).append(board[8]).append('\n');
		
		return sb.toString();
	}
}
