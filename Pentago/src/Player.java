/**
 * @author Tan Pham
 * Object representation of a player.
 */

import java.util.HashSet;
import java.util.Set;


public abstract class Player{

	private int nodesExpanded;
	String name;
	boolean white;

	public Player(String name) {
		this.name = name;
		setNodesExpanded(0);
	}

	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

	public String toString() {
		return name;
	}

	public Block.Unit getUnit() {
		if (isWhite()) {
			return Block.Unit.WHITE;
		}
		return Block.Unit.BLACK;
	}

	/*
	 * player move. to be override.
	 */
	public Movement move(Board board){
		return null;
	}


	/*
	 * get all possible moves can make for player in the current board state.
	 */
	public Set<Movement> getPossibleMoves(Board board) {
		Set<Movement> result = new HashSet<Movement>();
		for (Integer rt = 0; rt < 4;rt++) {
			for (Block.Twist t: Block.Twist.values()) {
				for (Coordinate possible: board.getFreeCoordinates()) {
					Movement current = new Movement();
					current.setPlayer(this);
					current.setCoords(possible);
					current.setBlock(board.blockIndex(possible));
					current.setUnit(board.getUnitIndex(possible));
					current.setTwist(t);
					current.setTwistBlock(rt);
					result.add(current);
				}
			}
		}
		return result;
	}

	/*
	 * evaluate the current score of the board for this player
	 */
	protected int evaluate(Board board){
		Set<Chain> myChains = board.getAllChains(this.getUnit());
		int myScore = getScore(myChains);

		Block.Unit oppUnit = (this.isWhite())? Block.Unit.BLACK: Block.Unit.WHITE;
		Set<Chain> oppChains = board.getAllChains(oppUnit);
		int oppScore = getScore(oppChains);

		return myScore - oppScore;
	}

	/*
	 * get score form a set of chains.
	 */
	private int getScore(Set<Chain> chains){
		int score = 0;
		for(Chain c: chains){
			switch (c.getSize()){
			case 1: 
				score += 1;
				break;
			case 2:
				score += 10;
				break;
			case 3:
				score += 100;
				break;
			case 4: 
				score += 1000;
				break;
			case 5:
				score += 10000;
				break;
			default:
				break;
			}
		}
		return score;
	}

	/**
	 * @return the nodesExpanded
	 */
	public int getNodesExpanded() {
		return nodesExpanded;
	}

	/**
	 * @param nodesExpanded the nodesExpanded to set
	 */
	public void setNodesExpanded(int nodesExpanded) {
		this.nodesExpanded = nodesExpanded;
	}

}
