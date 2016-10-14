/**
 * 
 * @author Tan Pham
 * Object representation of a move.
 */

public class Movement {
	
	private int block, unit;
	Coordinate coords;
	Integer twistBlock;
	Block.Twist twist;
	Player player;
	private int moveScore;
	
	
	public Coordinate getCoords() {
		return coords;
	}
	
	public void setCoords(Coordinate coords) {
		this.coords = coords;
	}
	public Integer getTwistBlock() {
		return twistBlock;
	}
	public void setTwistBlock(Integer twistBlock) {
		this.twistBlock = twistBlock;
	}
	public Block.Twist getTwist() {
		return twist;
	}
	public void setTwist(Block.Twist twist) {
		this.twist = twist;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public String toString() {
		return player + ": " + coords + ", " + twistBlock + "(" + twist + ")";
	}

	/**
	 * @return the block
	 */
	public int getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(int block) {
		this.block = block;
	}

	/**
	 * @return the unit
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(int unit) {
		this.unit = unit;
	}

	/**
	 * @return the moveScore
	 */
	public int getMoveScore() {
		return moveScore;
	}

	/**
	 * @param moveScore the moveScore to set
	 */
	public void setMoveScore(int moveScore) {
		this.moveScore = moveScore;
	}
	
}
