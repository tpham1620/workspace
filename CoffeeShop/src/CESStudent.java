/**
 * CESStudent Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


public class CESStudent implements Student{
	int selectIndex = 0;
	String name;

	public CESStudent(String std){
		name = std;
	}

	@Override
	public MyQueue<Student> selectTill(CoffeeShop cfs) {
		MyQueue<Student> selected = cfs.till.get(0);

		for (int i = 0; i < cfs.till.size(); i++){
			if (cfs.till.get(i).size()<5) {
				selected = cfs.till.get(i);
				selectIndex = i;
				break;
			}
		}

		return selected;
	}

	@Override
	public boolean startStreak() {return false;}

	@Override
	public boolean continueStreak() {return false;}

	@Override
	public String toString(){
		return name;
	}

	@Override
	public int selectIndex() {
		return selectIndex;
	}
}
