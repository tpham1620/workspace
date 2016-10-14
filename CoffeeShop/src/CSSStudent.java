/**
 * CSSStudent Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


public class CSSStudent implements Student{

	int selectIndex = 0;
	String name;
	
	public CSSStudent(String std){
		name = std;
	}
	@Override
	public MyQueue<Student> selectTill(CoffeeShop cfs) {
		MyQueue<Student> selected = cfs.till.get(0);
		for (int i = 1; i < cfs.till.size(); i++){
			if (selected.size()>cfs.till.get(i).size()){
				selected = cfs.till.get(i);
				selectIndex = i;
			}
		}
		return selected;
	}

	@Override
	public boolean startStreak() {

		return false;
	}

	@Override
	public boolean continueStreak() {

		return true;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public int selectIndex() {
		return selectIndex;
	}

}
