/**
 * CodingTree Class for compressing project
 * @author Tan Pham
 * Created 02/07/2015
 * Last modified 02/18/2015
 * ***************************************************************************************************************************************************************
 * 
 */

/*
 * import needed libs
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class CodingTree{

	public Map<Character,String> codes = new HashMap<Character, String>();
	public String bits = "";
	public String decode = "";
	public String text;

	Node root;

	/*
	 * override the compare method.
	 */
	private static Comparator<Node> compareWeight = new Comparator<Node>(){
		@Override
		public int compare(Node n1, Node n2) {
			return n1.weight - n2.weight;
		}
	};

	//create a priority queue with comparer is comparing the weight of the nodes
	private MyPriorityQueue<Node> prq = new MyPriorityQueue<Node>(compareWeight);

	/**
	 * 
	 * constructor
	 * 
	 */
	public CodingTree(String message){
		text = message;
		createTree(text);				//Create a tree with pass in text.
		createCode();					//Generate code for each node of the tree.
		readCode();						//read the code of each character and store in the map 
		bits = encode();				//encode the message into bits string
	}

	/**
	 * create a tree using huffman's algorithm 
	 * @param message the input message
	 */
	private void createTree(String message){
		FreqCount count = new FreqCount(message); //count the frequency of each character in the message.

		//create one tree for each character and add to the priority queue.
		for(int i = 0; i < count.size(); i++){
			prq.offer(new Node(count.ch.get(i).toString(), count.freq.get(i)));
		}

		/*
		 * while the priority queue size is greater then 2,
		 * poll the two trees with smallest weights, create a new tree
		 * then push back to the priority queue.
		 */
		while (prq.size()>2){
			Node lft = prq.poll();
			Node rgt = prq.poll();
			root = new Node(lft,rgt);
			prq.offer(root);
		}

		/*
		 * when only have two tree in the queue, poll them out and make a single tree
		 * then store in the root of the coding tree
		 */
		Node lft = prq.poll();
		Node rgt = prq.poll();
		root = new Node(lft,rgt);
	}


	/**
	 * A recursive function that read the code of each node of the tree
	 * then store the code in to the map codes, if the node content a character.
	 * @param node
	 */
	private void readCode(Node node){
		if(node.isLeaf()){
			codes.put(node.val.charAt(0), node.code);
		}else{
			readCode(node.left);
			readCode(node.right);
		}
	}
	public void readCode(){
		readCode(root);
	}

	/**
	 * A recursive function to assign the code for each node of the tree
	 * @param node
	 */
	private void createCode(Node node) {	
		if(node.left != null ){
			node.left.code = node.code + "0";
			createCode(node.left);
		}
		if(node.right != null){
			node.right.code = node.code + "1";
			createCode(node.right);
		}

	}

	public void createCode() {
		createCode(root);
	}


	/**
	 * Encoding the text in to binary string
	 * @return a string of bits
	 */
	public String encode(){
		StringBuilder str = new StringBuilder();

		for(int i = 0; i<text.length();i++){
			str.append(codes.get(text.charAt(i)));
		}
		bits = str.toString();

		return  bits;
	}


	/**
	 * The object which is a node for the tree.
	 * @author Tan Pham
	 *
	 */
	private class Node {
		String code;
		String val;
		int weight;
		Node left;
		Node right;


		public Node( Node rgt, Node lft){
			code = "";
			val = null;
			weight = lft.weight + rgt.weight;
			left = lft;
			right = rgt;
		}
		public Node (String c, int w){
			code = "";
			val = c;
			weight = w;
			left = null;
			right = null;
		}

		public boolean isLeaf(){
			return left == null && right == null;
		}		

		public String toString(){
			return val + " - " + code + "\n";
		}
	}

	/**
	 * An object to calculate the frequency of character in a message
	 * @author Tan Pham
	 *
	 */
	private class FreqCount{
		String message = "";

		ArrayList<Character> ch = new ArrayList<Character>();
		ArrayList<Integer> freq = new ArrayList<Integer>();


		FreqCount(String sms){
			message = sms;
			countFreq();
		}

		void countFreq(){
			for (int i = 0; i < message.length(); i++ ){
				boolean found = false;
				if(ch.contains(message.charAt(i))){
						int id = ch.indexOf(message.charAt(i));
						int temp =freq.get(id)+1;
						freq.set(id, temp );
						found = true;
					}
				
				if(!found){
					ch.add(message.charAt(i));
					freq.add(1);
				}
			}
		}
		int size(){
			return ch.size();
		}

	}
}

