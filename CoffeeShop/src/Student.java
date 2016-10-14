/**
 * Student interface.
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 ************************************************************************************
 *This is the interface to be implemented by the types of students.
 */
public interface Student {
	public MyQueue<Student> selectTill(CoffeeShop cfs);
	public boolean startStreak();
	public boolean continueStreak();
	public String toString();
	public int selectIndex();
	
}
