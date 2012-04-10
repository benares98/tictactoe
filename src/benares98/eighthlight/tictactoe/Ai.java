/**
 * 
 */
package benares98.eighthlight.tictactoe;

import benares98.eighthlight.tictactoe.Game.piece;

/**
 * @author user
 *
 */
public class Ai {
	private static Node root = null;
	public static Node buildGameTree(piece player, Game.piece[] board){
		Node node = new Node();
		node.player = player;
		node.board = board;
		if ((node.status = Game.getState(board)) == Game.status.atPlay){
			for (int i = 0; i < board.length; i++) {
				if (Game.piece._== board[i]) {
					Game.piece[] gameTreeCopy = board.clone();
					gameTreeCopy[i] = player;
					Node childNode = buildGameTree(rival(player),gameTreeCopy);
					childNode.position = i;
					node.addChild(childNode);
				}
			}
		}
		else{
			//node is the leaf.
		}
		
		return node;
	}
	private static Game.piece rival(Game.piece player) {
		if (Game.piece.x == player) return Game.piece.o; 
		else if(Game.piece.o == player) return Game.piece.x;
		else throw new IllegalArgumentException("Cannot use "+player+" to determine rival.");
	}
	
	public static int suggestedMove(Game.piece player, Game.piece[] board){
		if (null==player||Game.piece._==player||null==board) throw new IllegalArgumentException("Arguments cannot be null.");
		if (null==root){
			root = buildGameTree(player, board);
		}
		
		return computeMinimax(root, true);
	}
	private static int computeMinimax(Node node, boolean maximize) {
		if (Game.status.end != node.status){
			return 1;
		}
		
		int best = worst(maximize);
		
		for(Node childNode:node.getChildren()){
			int childScore = computeMinimax(childNode, maximize);
			if (betterThan(childScore, best, maximize)) best = childScore;	
		}
		return best;
	}
	private static boolean betterThan(int x, int y, boolean maximize) {return ((x>y)&&maximize || ((x<y)&& !maximize));}
	private static int worst(boolean maximize) {return (maximize)? Integer.MAX_VALUE : Integer.MIN_VALUE;}
}
