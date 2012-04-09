package benares98.eighthlight.tictactoe;

import java.util.List;

import benares98.eighthlight.tictactoe.Game.status;

public class Node {

	public char player;
	private List<Node> children;
	public int score;
	public int otherPlayerScore;
	public status status;
	public char[] board;
	
	public void addChild(Node node) {
		children.add(node);
		this.otherPlayerScore += node.score;
		this.score += node.otherPlayerScore;
	}

}
