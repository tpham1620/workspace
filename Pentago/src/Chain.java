/**
 * @author Tan Pham
 * A chain of 1 or more token with the same color on a line
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chain implements Iterable<Coordinate>{
	
	List<Coordinate> coords;
	Block.Unit unit;
	Board.Direction direction;

	public Chain(Block.Unit u, Board.Direction direction) {
		coords = new ArrayList<Coordinate>();
		unit = u;
		this.direction = direction;
	}
	
	public void add(Coordinate c) {
		coords.add(c);
	}
	
	public int getSize() {
		return coords.size();
	}
	
	public Board.Direction getDirection() {
		return direction;
	}

	@Override
	public Iterator<Coordinate> iterator() {
		return coords.iterator();
	}

	
	public List<Coordinate> getCoordinates() {
		return coords;
	}
	
	public String toString() {
		return unit + "/" + direction + ":" + coords;
	}

}
