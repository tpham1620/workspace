/**
 *
 * @author Tan Pham
 * Generate the contain of one of 4 blocks of the board.
 * The block have 9 units (squares).
 */

import java.util.Arrays;

public class Block {
	public enum Unit{EMPTY("-"),BLACK("B"),WHITE("W");
		String str;
		Unit(String s){
		str = s;};
	}
	
	public enum Twist{LEFT,RIGHT};
	
	Unit[] units = new Unit[9];
	
	public Block() {
		for (int i=0;i<9;i++) {
			units[i] = Unit.EMPTY;
		}
	}
	
	public int coordToIndex(Coordinate c) {
		return c.getX() + 3* c.getY();
	}
	
	public void setUnit(Coordinate c, Unit u) {
		units[coordToIndex(c)] = u;
	}
	
	public Unit getUnit(Coordinate coord) {
		return units[coordToIndex(coord)];
	}
	
	public void rotate(Twist rot) {
		Unit[] temp = Arrays.copyOf(units, 9);
		if (rot == Twist.RIGHT) {
			units[0] = temp[6];
			units[1] = temp[3];
			units[2] = temp[0];
			units[3] = temp[7];
			units[4] = temp[4];
			units[5] = temp[1];
			units[6] = temp[8];
			units[7] = temp[5];
			units[8] = temp[2];
		} else {
			units[0] = temp[2];
			units[1] = temp[5];
			units[2] = temp[8];
			units[3] = temp[1];
			units[4] = temp[4];
			units[5] = temp[7];
			units[6] = temp[0];
			units[7] = temp[3];
			units[8] = temp[6];
		}
	}

	public String lineToString(int line) {
		String result = "";
		for (int i=0;i<3;i++) {
			result += " " + units[line*3 + i].str;
		}
		return result;
	}
	
	
	
	
	public Block copy() {
		Block result = new Block();
		for (int i=0;i<9;i++) {
			result.units[i] = units[i];
		}
		return result;
	}
	
}
