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
		return buildGameTree(player, board, -1);
	}
	public static Node buildGameTree(piece player, Game.piece[] board, int position){
		Game.status state = Game.getState(board);
		Node node;
		if (state == Game.status.atPlay){
			//TODO consider reflections, rotations + a/b pruning to reduce node count and search
			Set<Integer> available = Game.availablePositions(board);
			List<Node>children = new ArrayList<Node>(available.size());
			for (Integer pos:available) {
				Game.piece[] gameTreeCopy = board.clone();
				gameTreeCopy[pos] = player;
				Node childNode = buildGameTree(Game.rival(player), gameTreeCopy, pos);
				children.add(childNode);
			}
			node = new Node(state, position, children);
		}
		else node = new Node(state, position);//node is leaf
		
		return node;
	}
	
	public static int suggestedMove(Game.piece player, Game.piece[] board){
		if (null==player||Game.piece._==player||null==board) throw new IllegalArgumentException("Arguments cannot be null.");
		
		List<Node> choices = buildGameTree(player, board).getChildren();
		int best = worst(true);
		int position = -1;
		for(Node choice:choices){
			int score = computeMinimax(player, choice, false);
			if (betterThan(score, best, true)){
				best = score;
				position = choice.getPosition();
			}
		}
		
		return position;
	}
	
	public static int computeMinimax(Game.piece player, Node node, boolean maximize) {
		Game.status winState = Game.getWinningState(player);
		
		if (Game.status.atPlay != node.getStatus()){
			if (Game.status.tie == node.getStatus()) return 1;
			else return (node.getStatus() == winState) ? 1 : -1;
		}
		
		int best = worst(maximize);
		
		for(Node childNode:node.getChildren()){
			int childScore = computeMinimax(player, childNode, !maximize);
			if (maximize){
				int wins = countStateAtDepth(winState, 1, childNode);
				childScore += wins;
			}else{
				int wins = countStateAtDepth(Game.getWinningState(Game.rival(player)), 2, childNode);
				childScore += wins;
			}
			
			if (betterThan(childScore, best, maximize)) best = childScore;
		}
		return best;
	}
	
	public static int countStateAtDepth(Game.status state, int depth, Node node) {
		if (Game.status.atPlay != node.getStatus() && depth > 0) return 0; //terminal reached before going to the right depth
		
		if (depth == 0)return (state == node.getStatus()) ? 1 : 0;
		
		int count = 0;
		for (Node childNode:node.getChildren())//assumes that since the node's status is atPlay then it has children
			if (depth>0) count += countStateAtDepth(state, depth-1, childNode);
		return count;
	}
	private static boolean betterThan(int x, int y, boolean maximize) {return ((x>y)&&maximize || ((x<y)&& !maximize));}
	private static int worst(boolean maximize) {return (maximize)? Integer.MIN_VALUE : Integer.MAX_VALUE;}
}
