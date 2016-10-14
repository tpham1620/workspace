/**
 * MyQueue Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */




public class MyQueue<T> {
	public class Node<R>{
		R item;
		Node<R> next;		

		public Node(R it) {
			item = it;
			next = null;
		}

		public String toString(){
			return item.toString();
		}
	}

	public Node<T> front;
	public Node<T> back;
	public int size;

	boolean open = false;

	/*
	 * Constructor.
	 */
	public MyQueue(){
		front = back = null;
		size = 0;
	}


	public boolean isEmpty(){
		if(front == null && back == null){
			return true;
		}else return false;

	}

	public void offer(T it){
		Node<T> node = new Node<T>(it);
		if(this.isEmpty()){
			front = back = node;
			size++;
		}else{
			node.next = back;
			back = node;
			size++;
		}

	}	

	public Node<T> poll(){
		if(isEmpty()) return null;
		Node<T> temp = front;
		if(front == back){
			front = back = null; 
			size--;
		}else{
			Node<T> conductor = back;
			while(conductor.next!=front) conductor = conductor.next;
			front = conductor;
			size--;
		}
		
		return temp;
	}

	public Node<T> peek(){
		return front;
	}

	public MyQueue<T> split(){
		MyQueue<T> odd = new MyQueue<T>();
		MyQueue<T> even = new MyQueue<T>();
		
		int s = size;
		for(int i = 0; i<s; i++){
			if(i%2 == 0) odd.offer(this.poll().item);
			if(i%2 == 1) even.offer(this.poll().item);
		}
		int k = odd.size;
		for(int i=0;i<k;i++){
			this.offer(odd.poll().item);
		}
		return even;
	}

	public int size(){
		return size;
	}

	public String toString(){
		if(this.size()==0) return "[]";
		String str = "[";
		
		Node<T> conductor = this.back;
		for (int i = 0; i < this.size(); i++){
			str += conductor.toString() + ", ";
			conductor = conductor.next;
		}

		str = str.substring(0, str.length()-2) + "]";				
		return str;
	}
}
