/**
 * 
 * @author Tan Pham
 * Give the coordinate representation of each position on the board
 * the board is 6x6, so there is 36 positions each represent by a coordinate.
 */

public class Coordinate {
	
	int x;
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	// walk dx unit on x exist and dy unit on y exist 
	public Coordinate translate(int dx, int dy) {
		return new Coordinate(x+dx,y+dy);
	}
	
	//walk left one unit
	public Coordinate left() throws InvalidCoordinateException {
		if (x == 0) {
			throw new InvalidCoordinateException();
		}
		return translate(-1,0);
	}
	
	//walk right one unit
	public Coordinate right() throws InvalidCoordinateException {
		if (x == 5) {
			throw new InvalidCoordinateException();
		}
		return translate(1,0);
	}
	
	//walk up one unit
	public Coordinate up() throws InvalidCoordinateException {
		if (y == 0) {
			throw new InvalidCoordinateException();
		}
		return translate(0,-1);
	}
	
	//walk down one unit
	public Coordinate down() throws InvalidCoordinateException {
		if (y == 5) {
			throw new InvalidCoordinateException();
		}
		return translate(0,1);
	}
	
	//walk diagnose left-up one unit
	public Coordinate leftUp() throws InvalidCoordinateException {
		if (y == 0 || x == 0) {
			throw new InvalidCoordinateException();
		}
		return translate(-1,-1);
	}
	
	//walk diagnose left-down one unit
	public Coordinate leftDown() throws InvalidCoordinateException {
		if (y == 5 || x == 0) {
			throw new InvalidCoordinateException();
		}
		return translate(-1,1);
	}
	
	//walk diagnose right-up one unit
	public Coordinate rightUp() throws InvalidCoordinateException {
		if (y == 0 || x == 5) {
			throw new InvalidCoordinateException();
		}
		return translate(1,-1);
	}
	
	//walk diagnose right-down one unit
	public Coordinate rightDown() throws InvalidCoordinateException {
		if (y == 5 || x == 5) {
			throw new InvalidCoordinateException();
		}
		return translate(1,1);
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}

}
