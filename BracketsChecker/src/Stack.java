import java.util.ArrayList;

/**
 * Builds in Stack data structure to use for this program.
 * This stack is implemented using an ArrayList.
 * 
 * @author Tan Pham
 * @version 2016.11.1
 */
public class Stack {

	private ArrayList<Character> stack;
	
	/*
	 * Stack constructor.
	 */
	public Stack(){
		stack = new ArrayList<Character>();
	}
	
	/**
	 * this method pushes the value to the top of the stack (at the end of the ArrayList).
	 * @param value : the new value to be added into the stack.
	 */
	public void push(Character value){
		stack.add(value);
	}
	
	/**
	 * this method gets the value on the top of the stack without removing it from the stack.
	 * @return the value on top of the stack.
	 */
	public Character top(){
		return stack.get(stack.size()-1);
	}
	
	/**
	 * this method removes the item on the top of the stack.
	 * @return the removed item.
	 */
	public Character pop(){
		return stack.remove(stack.size()-1);
	}
	
	/**
	 * this method get the size of the stack.
	 * @return the size of the stack.
	 */
	public int size(){
		return stack.size();
	}
}
