/**
 * 
 */
package benares98.eighthlight.tictactoe;

/**
 * @author user
 *
 */
public class Ai {
	
	public static Node buildGameTree(char player, char[] board){
		Node node = new Node();
		node.player = player;
		node.board = board;
		if ((node.status = Game.getState(board)) == Game.status.atPlay)
			for (int i = 0; i < board.length; i++) {
				if ('x'!= board[i] && 'o'!= board[i]) {
					char[] gameTreeCopy = board.clone();
					gameTreeCopy[i] = player;
					node.addChild(buildGameTree(otherPlayer(player),gameTreeCopy));
				}
			}
		else{
			//node is the leaf.
			
		}
		
		return node;
	}
	private static char otherPlayer(char player) {if ('X' == player) return 'O'; else return 'X';}
	
	public static int suggestedMove(Player user, Player[] board){
		if (null==user||null==board) throw new IllegalArgumentException("Arguments cannot be null.");
		return 0;
	}
}
