/**
 * 
 * @author Tan Pham
 *throw exception if coordinate is not within the board.
 */
public class InvalidCoordinateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Invalid Coordinates";
	}
}
