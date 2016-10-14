/**
 * CodingTree Class for compressing project
 * @author Tan Pham
 * Created 03/01/2015
 * Last modified 03/5/2015
 * ***************************************************************************************************************************************************************
 * 
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;


public class CodingTree{
	/*
	 * This is the array of character that is considered as a letter.
	 */
	private final char letter[] = { 'a','b','c','d','e','f','g','h','j','i','k','l','m','n','o','p','q','u','r','t','s','v','w','x','y','z',
									'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M',
									'0','1','2','3','4','5','6','7','8','9','\'','-'};
	
	/**
	 * This function take in a string and convert this string to a linked list
	 *  with each element of the list is a single word.
	 * @param sms - a raw text string
	 * @return a linked list of strings (words)
	 */
	private LinkedList<String> getWords(String sms){
		LinkedList<String> arrStr = new LinkedList<String>();

		String word = "";
		for(int i = 0; i < sms.length(); i++){

			char ch = sms.charAt(i);
			if(!isLetter(ch)){
				if(word != ""){
					arrStr.add(word);
					word = "";
				}

				arrStr.add(""+ch);
			}else{
				word+= ""+ch;
			}
		}
		return arrStr;
	}
	
	/**
	 * This function check if a char is a letter or not
	 * @param ch - input character
	 * @return true or false
	 */
	private boolean isLetter(char ch){
		for(int i = 0; i < letter.length; i++){
			if(ch == letter[i]) return true;
		}	
		return false;
	}


	/*
	 * override the compare method.
	 */
	private static Comparator<Node> compareWeight = new Comparator<Node>(){
		@Override
		public int compare(Node n1, Node n2) {
			return n1.weight - n2.weight;
		}
	};

	public MyHashTable<String,String> codes = new MyHashTable<String, String>(32768);
	public String bits = "";
	public String decode = "";
	public LinkedList<String> text;

	//create a priority queue with comparer is comparing the weight of the nodes
	private PriorityQueue<Node> prq = new PriorityQueue<Node>(compareWeight);

	private Node root;	//The root of the Huffman's Tree


	/**
	 * 
	 * constructor
	 * 
	 */
	public CodingTree(String message){
		text = getWords(message);		//
		createTree(text);				//Create a tree with pass in text.
		createCode();					//Generate code for each node of the tree.
		readCode();						//read the code of each character and store in the map 
		bits = encode();				//encode the message into bits string
	}

	/**
	 * create a tree using huffman's algorithm 
	 * @param message the input message
	 */
	private void createTree(LinkedList<String> message){
		
		FreqCount count = new FreqCount(message); //count the frequency of each character in the message.
		String next = count.word.poll();
		//create one tree for each character and add to the priority queue.
		while(next != null){
			prq.offer(new Node(next, count.wordCount.get(next)));
			next = count.word.poll();
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
			codes.put(node.val, node.code);
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

		String next = text.poll();
		while(next!=null){
			str.append(codes.get(next));
			next = text.poll();
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


		LinkedList<String> message = new LinkedList<String>();

		LinkedList<String> word = new LinkedList<String>();
		Map<String,Integer> wordCount = new HashMap<String,Integer>(32768);


		FreqCount(LinkedList<String> sms){
			message.addAll(sms);
			countFreq();
		}

		private void countFreq(){

			String next;
			boolean found;
			while (!message.isEmpty()){
				next = message.poll();
				found = false;

				if (wordCount.containsKey(next)){
					wordCount.put(next, wordCount.get(next)+1);
					found = true;
				}

				if(!found){
					wordCount.put(next, 1);
					word.add(next);
					
				}
			}
		}
	}
}

