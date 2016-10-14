/**
 * ITSStudent Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


public class ITSStudent implements Student{
	int selectIndex = 0;
	String name;

	public ITSStudent(String std){
		name = std;
	}
	@Override
	public MyQueue<Student> selectTill(CoffeeShop cfs) {

		MyQueue<Student> selected = new MyQueue<Student>();

		if (cfs.allFull()) {
			selected = cfs.till.get(0);
		}else{
			for (int i = 0; i < cfs.till.size(); i++){
				if (selected.size() < cfs.till.get(i).size() && cfs.till.get(i).size()<5){
					selected = cfs.till.get(i);
					selectIndex = i;
				}
			}
		}
		return selected;
	}

	@Override
	public boolean startStreak() {return true;}

	@Override
	public boolean continueStreak() {return true;}

	@Override
	public String toString(){
		return name;
	}

	@Override
	public int selectIndex() {
		return selectIndex;
	}
}
