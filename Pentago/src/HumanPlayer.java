/**
 * @author Tan Pham
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HumanPlayer extends Player {
	
	BufferedReader br;

	public HumanPlayer(String name) {
		super(name);
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * Human input move method.
	 */
	@Override 
	public Movement move(Board board) {
		boolean successful = false;
		int b = -1;
		int u = -1;
		int tBlock = -1;
		Block.Twist t = null;
		String input ="";
		while (!successful) {
			try{
				System.out.print("Enter move as a form: Block/Unit TwistBlock+Direction.");
	            input = br.readLine();

	            b = Integer.parseInt(input.substring(0, 1));
	            u = Integer.parseInt(input.substring(2, 3)) - 1;
	            
	            tBlock = Integer.parseInt(input.substring(4, 5));
	            
	            if (!(1<=b &&  4>=b && 0<=u && 8>= u && 1<=tBlock && 4 >= tBlock)) {
	            	System.out.println("Invalid Inputs");
	            	continue;
	            }
	            
	            String temp = input.substring(5, 6);
	            if (temp.equalsIgnoreCase("l") || temp.equalsIgnoreCase("r")) {
	            	successful = true;
	            	t = temp == "r" ? Block.Twist.RIGHT : Block.Twist.LEFT;
	            }
			} catch(NumberFormatException | IOException nfe){
	            System.err.println("Invalid Input!");
	        }
		}
		
		Movement move = new Movement();
		move.setPlayer(this);
		move.setBlock(b);
		move.setUnit(u);
		
		//translate block and unit to board coordinate.
		if(b == 1){
			move.setCoords(new Coordinate(u%3,u/3));
		}else if(b == 2){
			move.setCoords(new Coordinate(u%3 + 3,u/3));
		}else if (b ==3){
			move.setCoords(new Coordinate(u%3,u/3 + 3));
		}else if(b == 4){
			move.setCoords(new Coordinate(u%3 + 3,u/3 + 3));
		}
		move.setTwistBlock(tBlock - 1);
		move.setTwist(t);
		return move;
	}

}
