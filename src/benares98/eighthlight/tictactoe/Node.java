package benares98.eighthlight.tictactoe;

import java.util.ArrayList;
import java.util.List;

import benares98.eighthlight.tictactoe.Game.piece;
import benares98.eighthlight.tictactoe.Game.status;

public class Node {

	public piece player;
	private List<Node> children;
	public int score;
	public int otherPlayerScore;
	public status status;
	public piece[] board;
	public int position;
	
	public void addChild(Node node) {
		if (null==children){
			children = new ArrayList<Node>();
		}
		if(!children.contains(node))
			children.add(node);
	}
	
	public List<Node> getChildren(){return children;}

}
