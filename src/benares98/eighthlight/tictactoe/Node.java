package benares98.eighthlight.tictactoe;

import java.util.ArrayList;
import java.util.List;

import benares98.eighthlight.tictactoe.Game.piece;
import benares98.eighthlight.tictactoe.Game.status;

public class Node {
	private List<Node> children;
	public status status;
	public int position = -1;
	public piece[] board;
	
	public Node(status state) {
		this.status = state;
	}

	public Node(status state, int position) {
		this.position = position;
		this.status = state;
	}
	
	public List<Node> getChildren(){return children;}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public void addChild(Node child){
		if (null == children) children = new ArrayList<Node>();
		if (!children.contains(child)) children.add(child);
	}

}
