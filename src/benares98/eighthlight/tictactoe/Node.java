package benares98.eighthlight.tictactoe;

import java.util.List;

import benares98.eighthlight.tictactoe.Game.status;

public class Node {
	private List<Node> children;
	private status status;
	public status getStatus() {
		return status;
	}

	public int getPosition() {
		return position;
	}

	private int position = -1;
	
	public Node(status state, int position) {
		this.status = state; this.position = position;
	}
	
	public Node(status state, int position, List<Node> children) {
		this.status = state; this.position = position; this.children=children;
	}

	public List<Node> getChildren(){return children;}
}
