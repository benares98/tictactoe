package benares98.eighthlight.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

	public static enum status{x, o, tie, atPlay};
	public static enum player{x, o};
	public static status getState(char[] board) {
		// TODO Auto-generated method stub
		return status.atPlay;
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
					
				}
			} catch (IOException e) {
				System.out.println("IO error trying to read your input.");
				System.exit(1);
			} catch (NumberFormatException e) {
				System.out.println("Couldn't understand your input.");
			}
		}while(!userSelected);
	}
}
