/**
 * CoffeeShop Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */

import java.util.ArrayList;


public class CoffeeShop {

	public ArrayList<MyQueue<Student>> till;
	public ArrayList<MyQueue<Student>> streak;
	MyQueue<Student> door, exit1, exit2, served;

	/**
	 * Constructor.
	 */
	public CoffeeShop(){
		till = new ArrayList<MyQueue<Student>>();
		streak = new ArrayList<MyQueue<Student>>();
		till.add(new MyQueue<Student>());
		for(int i = 0; i<5;i++){
			streak.add(new MyQueue<Student>());
		}
		door = new MyQueue<Student>();
		exit1 = new MyQueue<Student>();
		exit2 = new MyQueue<Student>();



	}

	public boolean tillFull(MyQueue<Student> till){
		if (till.size<5){
			return false;
		}else return true;
	}


	public boolean allFull(){
		for(int i=0; i<till.size();i++){
			if (!tillFull(till.get(i))) return false;
		}
		return true;
	}

	public void offer(Student std){
		MyQueue<Student> selected = std.selectTill(this);
		if(!tillFull(selected)){
			selected.offer(std);
			return;
		}else if(till.size()<5){
			MyQueue<Student> newQueue = selected.split();
			newQueue.offer(std);
			till.add(newQueue);
			till.set(std.selectIndex(), selected);
			return;
		}else{
			door.offer(std);
		}
	}

	public MyQueue<Student> poll(){
		served = new MyQueue<Student>();

		while(!exit1.isEmpty()){
			served.offer(exit1.poll().item);	
		}
		while(!exit2.isEmpty()){
			exit1.offer(exit2.poll().item);
		}
		for(int i=till.size()-1; i >= 0;i--){
			if(!till.get(i).isEmpty()){


				if(till.get(i).peek().item.startStreak()) startStreak(till.get(i).peek().item,i);
				if(!till.get(i).peek().item.continueStreak()){
					endStreak(till.get(i).peek().item,i);
				}else{
					continueStreak(till.get(i).peek().item,i);
				}

				exit2.offer(till.get(i).poll().item);
			}else if (i!=0 && till.get(i-1).isEmpty()){
				till.remove(i);
			}		
		}
		while(!this.allFull()  && !door.isEmpty()){
			this.offer(door.poll().item);
		}

		return served;
	}

	public String toString(){
		String str = "";
		str = "Door Queue: " + door.toString() +"\n";
		for(int i = 0; i<5;i++){
			if (i < till.size()){
				str += "Till " + i + ": " + till.get(i).toString() +"\n";
			}else{
				str += "Till " + i + ": [CLOSE]\n";
			}
		}
		for(int i = 0; i<5;i++){
			str += "Longest Streak Till " + i + ": " + streak.get(i).toString() +"\n";

		}
		str += "Exit Queue 2 min: " + exit2.toString() +"\n";
		str += "Exit Queue 1 min: " + exit1.toString() +"\n";
		if(served != null){
			str += "Satisfied Customers: " + served.toString() + "\n";
		}

		return str;
	}

	private void startStreak(Student std, int i){
		if(!streak.get(i).open) {
			streak.set(i, new MyQueue<Student>());
			streak.get(i).offer(std);
			streak.get(i).open = true;
		}
	}
	private void endStreak(Student std, int i){
		if(streak.get(i).open){
			streak.get(i).offer(std);
			streak.get(i).open = false;
		}
	}
	private void continueStreak(Student std, int i){
		if(streak.get(i).open) streak.get(i).offer(std);
	}
}
