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
		float best = worst(true);
		int position = -1;
		for(Node choice:choices){
			float score = computeMinimax(player, choice, false, 0);
			if (betterThan(score, best, true)){
				best = score;
				position = choice.getPosition();
			}
		}
		
		return position;
	}
	
	public static float computeMinimax(Game.piece player, Node node, boolean maximize, int depth) {
		Game.status winState = Game.getWinningState(player);
		Game.status currentStatus = node.getStatus();
		if (Game.status.atPlay != currentStatus) return getObjectiveValue(currentStatus, depth, winState);
		
		float best = worst(maximize);
		
		for(Node childNode:node.getChildren()){
			float childScore = computeMinimax(player, childNode, !maximize, depth+1);
			if (betterThan(childScore, best, maximize)) best = childScore;
		}
		return best;
	}
	
	private static float getObjectiveValue(Game.status currentStatus, int depth, Game.status winState) {
		if (Game.status.atPlay == currentStatus) throw new IllegalArgumentException("Game.status.atPlay is an invalid status to get the objective value.");
		if (Game.status.tie == currentStatus)return 0;
		else{
			float base = (currentStatus == winState)? 1f: -1f;
			return base/(float)depth;
		}
	}
	
	private static boolean betterThan(float x, float y, boolean maximize) {return ((x>y)&&maximize || ((x<y)&& !maximize));}
	private static int worst(boolean maximize) {return (maximize)? Integer.MIN_VALUE : Integer.MAX_VALUE;}
}
