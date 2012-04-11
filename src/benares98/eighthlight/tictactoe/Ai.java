/**
 * 
 */
package benares98.eighthlight.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import benares98.eighthlight.tictactoe.Game.piece;

/**
 * @author user
 *
 */
public class Ai {
	public static Node buildGameTree(piece player, Game.piece[] board){
		Node node = new Node(Game.getState(board));
		node.board = board;
		if (node.status == Game.status.atPlay){
			//TODO consider reflections, rotations + a/b pruning to reduce node count and search
			Set<Integer> available = Game.availablePositions(board);
			List<Node>children = new ArrayList<Node>(available.size());
			for (Integer pos:available) {
				Game.piece[] gameTreeCopy = board.clone();
				gameTreeCopy[pos] = player;
				Node childNode = buildGameTree(rival(player), gameTreeCopy);
				childNode.position = pos;
				children.add(childNode);
			}
			node.setChildren(children);
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
		
		List<Node> choices = buildGameTree(player, board).getChildren();
		int best = worst(true);
		int position = -1;
		for(Node choice:choices){
			int localPos = choice.position;
			piece[] localBoard = choice.board;
			int score = computeMinimax(player, choice, true);
			System.out.println("Score "+score+" for pos "+localPos);
			if (betterThan(score, best, true)){
				best = score;
				position = choice.position;
			}
		}
		return position;
	}
	
	public static int computeMinimax(Game.piece player, Node node, boolean maximize) {
		Game.status winState = Game.getWinningState(player);
		
		if (Game.status.atPlay != node.status){
			if (Game.status.tie == node.status) return 0;
			else return (node.status == winState) ? 1 : -1;
		}
		
		int best = worst(maximize);
		
		for(Node childNode:node.getChildren()){
			int childScore = computeMinimax(player, childNode, maximize);
			int wins = countStateAtDepth(winState, 1, childNode);
			childScore += wins;
			
			if (betterThan(childScore, best, maximize)) best = childScore;
		}
		return best;
	}
	
	public static int countStateAtDepth(Game.status state, int depth, Node node) {
		if (Game.status.atPlay != node.status && depth > 0) return 0; //terminal reached before going to the right depth
		
		if (depth == 0)return (state == node.status) ? 1 : 0;
		
		int count = 0;
		for (Node childNode:node.getChildren())//assumes that since the node's status is atPlay then it has children
			if (depth>0) count += countStateAtDepth(state, depth-1, childNode);
		return count;
	}
	private static boolean betterThan(int x, int y, boolean maximize) {return ((x>y)&&maximize || ((x<y)&& !maximize));}
	private static int worst(boolean maximize) {return (maximize)? Integer.MIN_VALUE : Integer.MAX_VALUE;}
}
