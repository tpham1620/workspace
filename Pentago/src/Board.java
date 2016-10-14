/**
 * @author Tan Pham
 *
 * This class create a board object 
 * control game rule and detect winner
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	public enum Direction {L,R,U,D,LU,LD,RU,RD};
	
	Block[] blocks = new Block[4];
	
	private Block.Unit winnerUnit;
	boolean isWon = false;
	
	public Board() {
		for (int i=0;i<4;i++) {
			blocks[i] = new Block();
		}
	}
	
	public Block getBlock(int pos) {
		return blocks[pos];
	}
	
	public Iterable<Coordinate> getBoardCoordinates() {
		List<Coordinate> temp = new ArrayList<Coordinate>();
		for (int i = 0;i<6;i++) {
			for (int j = 0;j<6;j++) {
				temp.add(new Coordinate(i,j));
			}
		}
		return temp;
	}
	
	public String toString() {
		String result = " ";
		for (int i=0;i<6;i++) {
			if (i==3) {
				result += " | ";
			}
			result += "-"+i;
		}
		result += "\n";
		for (int i=0;i<3;i++) {
			result += i + blocks[0].lineToString(i) + " | " + blocks[1].lineToString(i) + "\n";
		}
		for (int i=0;i<8;i++) {
			result += " -";
		}
		result += "\n";
		for (int i=0;i<3;i++) {
			result += (3+i) + blocks[2].lineToString(i) + " | " + blocks[3].lineToString(i) + "\n";
		}
		return result;
	}
	
	/*
	 * check if there is any token at this coordinate.
	 */
	public boolean checkCoordinate(Coordinate coord) {
		return getUnitAtCoord(coord) == Block.Unit.EMPTY;
	}
	
	/*
	 * move the board (update) when player made a move.
	 */
	public void move(Movement move) {
		Coordinate c = move.getCoords();
		if (checkCoordinate(c)) {
			Block moveBlock = getBlockAtCoord(c);
			Coordinate blockCoord = translateCoordToBlock(c, moveBlock);
			if (move.getPlayer().isWhite()) {
				moveBlock.setUnit(blockCoord, Block.Unit.WHITE);
			} else {
				moveBlock.setUnit(blockCoord, Block.Unit.BLACK);
			}
			Integer rotBlock = move.getTwistBlock();
			blocks[rotBlock].rotate(move.getTwist());
		}
	}
	
	/*
	 * reverse the move
	 */
	public void undoMove(Movement move) {
		Block.Twist temp = move.getTwist();
		if (temp == Block.Twist.LEFT){
			move.setTwist(Block.Twist.RIGHT);
		}else{
			move.setTwist(Block.Twist.LEFT);
		}
		Integer tBlock = move.getTwistBlock();
		blocks[tBlock].rotate(move.getTwist());
		
		Coordinate c = move.getCoords();
		Block moveBlock = getBlockAtCoord(c);
		Coordinate tileCoord = translateCoordToBlock(c, moveBlock);
		moveBlock.setUnit(tileCoord, Block.Unit.EMPTY);
	}
	
	
	public Direction opposite(Direction d) {
		if (d == Direction.L) return Direction.R;
		if (d == Direction.R) return Direction.L;
		if (d == Direction.U) return Direction.D;
		if (d == Direction.D) return Direction.U;
		if (d == Direction.LU) return Direction.RD;
		if (d == Direction.LD) return Direction.RU;
		if (d == Direction.RU) return Direction.LD;
		if (d == Direction.RD) return Direction.LU;
		throw new RuntimeException("Unknown Direction " + this);
	}
	
	public Coordinate walk(Coordinate c, Direction d) throws InvalidCoordinateException {
		if (d == Direction.L) return c.left();
		if (d == Direction.R) return c.right();
		if (d == Direction.U) return c.up();
		if (d == Direction.D) return c.down();
		if (d == Direction.LU) return c.leftUp();
		if (d == Direction.LD) return c.leftDown();
		if (d == Direction.RU) return c.rightUp();
		if (d == Direction.RD) return c.rightDown();
		throw new RuntimeException("Unknown Direction " + this);
	}

	
	public boolean detectWin() {
		//a win is any straight line of 5 same color stones
		// Let's start at any coord and check if its the start of a winning line
		for (Coordinate c: getBoardCoordinates()) {
			Block.Unit p = getUnitAtCoord(c);
			if (p != Block.Unit.EMPTY) {
				// 8 possible ways to win from this position
				for (Direction direction : Direction.values()) {
					int count = 1;
					Coordinate nextC = c;
					while (count != 5) {
						try {
							nextC = walk(nextC, direction);
							if (getUnitAtCoord(nextC).equals(p)) {
								count++;
							} else {
								break;
							}
						} catch (InvalidCoordinateException ex) {
							break;
						}
					}
					if (count == 5) {
						// winner
						setWinnerUnit(p);
						isWon = true;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	
	public int blockIndex(Coordinate coord){
		if (coord.getX() > 2) {
			if (coord.getY() > 2) {
				return 3;
			} else {
				return 1;
			}
		} else {
			if (coord.getY() > 2) {
				return 2;
			} else {
				return 0;
			}
		}
	}
	public Block getBlockAtCoord(Coordinate coord) {
		Block block;
		if (coord.getX() > 2) {
			if (coord.getY() > 2) {
				block = blocks[3];
			} else {
				block = blocks[1];
			}
		} else {
			if (coord.getY() > 2) {
				block = blocks[2];
			} else {
				block = blocks[0];
			}
		}
		return block;
	}
	
	public Coordinate translateCoordToBlock(Coordinate coord, Block block) {
		Coordinate c = null;
		if (block == blocks[0]) {
			c = coord;
		} else if (block == blocks[1]) {
			c = coord.translate(-3, 0);
		} else if (block == blocks[2]) {
			c = coord.translate(0, -3);
		} else if (block == blocks[3]) {
			c = coord.translate(-3, -3);
		} else {
			System.out.println("Unknown block for Coordinate translation.");
		}
		return c;
	}
	
	public Block.Unit getUnitAtCoord(Coordinate coord) {
		Block block = getBlockAtCoord(coord);
		Coordinate c = translateCoordToBlock(coord, block);
		return block.getUnit(c);
	}
	
	public int getUnitIndex(Coordinate coord){
		Block block = getBlockAtCoord(coord);
		Coordinate c = translateCoordToBlock(coord, block);
		return block.coordToIndex(c);
	}
	
	public List<Coordinate> getFreeCoordinates() {
		List<Coordinate> result = new ArrayList<Coordinate>();
		for (Coordinate c: getBoardCoordinates()) {
			if (getUnitAtCoord(c) == Block.Unit.EMPTY) {
				result.add(c);
			}
		}
		return result;
	}
	
	
	public Board copy() {
		Board result = new Board();
		result.setWinnerUnit(winnerUnit);
		result.isWon = isWon;
		for (int i=0;i<4;i++) {
			result.blocks[i] = blocks[i].copy();
		}
		return result;
	}


	public Block.Unit getWinnerUnit() {
		return winnerUnit;
	}

	private void setWinnerUnit(Block.Unit winnerUnit) {
		this.winnerUnit = winnerUnit;
	}
	
	/*
	 * return a set of all chains of a token  color.
	 */
	public Set<Chain> getAllChains(Block.Unit unit) {
		Set<Chain> result = new HashSet<Chain>();
		for (Coordinate c: getBoardCoordinates()) {
			Block.Unit p = getUnitAtCoord(c);
			if (p == unit) {
				// there are 8 possible ways from this position 
				for (Direction direction : Direction.values()) {
					// That is the chain from the position itself
					Chain chain = new Chain(unit, direction);
					chain.add(c);
					int count = 1;
					Coordinate nextC = c;
					while (count <= 5) {
						try {
							nextC = walk(nextC, direction);
							if (getUnitAtCoord(nextC).equals(p)) {
								count++;
								chain.add(nextC);
							} else {
								break;
							}
						} catch (InvalidCoordinateException ex) {
							break;
						}
					}
					if (chain.getSize() >= 1) {
						result.add(chain);
					}
				}
			}
		}
		return result;
	}
	
	

}
