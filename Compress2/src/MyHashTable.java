/**
 * MyHashTable Class for compressing project
 * @author Tan Pham
 * Created 03/01/2015
 * Last modified 03/5/2015
 * ***************************************************************************************************************************************************************
 * 
 */


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;




public class MyHashTable<K,V> {

	/**
	 * This is a class to create entry node objects for the table.
	 * @author Tan Pham
	 *
	 */
	private class Entry{
		private K key;
		private V value;
		private int prob;

		public Entry() {
			key = null;
			value = null;
			prob = 0;
		}

		public K getKey(){
			return key;
		}

		public V getValue(){
			return value;
		}
		public void setKey(K k){
			key = k;
		}
		public void setValue(V v){
			value = v;
		}

		public void setProb(int prob) {
			this.prob = prob;
		}
		public String toString(){
			return "(" + key.toString() + "-" + value.toString() + ")";
		}
	}

	/*
	 * This Array list is the hash table. each bucket (entry) contents a key and a value, and additional properties.
	 */
	private ArrayList<Entry> table = new ArrayList<Entry>();
	
	/*
	 * this linked list keep track of the key set.
	 */
	private LinkedList<K> keys = new LinkedList<K>();
	
	private int capacity;
	private int entries = 0, probing = 0, max_prob = 0;

/**
 * Constructor.
 * Create a table with c buckets
 * @param c
 */
	public MyHashTable(int c){
		capacity = c;
		for(int i = 0; i < capacity; i++){
			table.add(new Entry());
		}
	}

	/**
	 * put a new entry to the table if it has not exist
	 * Otherwise, just update the value. 
	 * @param k
	 * @param v
	 */
	public void put(K k, V v){
		int id = hash(k);
		if(table.get(id).getKey() != null){
			table.get(id).setValue(v);
		}else{
			table.get(id).setKey(k);
			table.get(id).setValue(v);
			table.get(id).setProb(probing);
			if(max_prob < probing){
				max_prob = probing;
			}
			probing = 0;
			entries++;
			keys.add(k);
		}
	}

	/**
	 * return the value that the input key maps to,
	 * if no value match the input key, return null.
	 * @param k - key
	 * @return value
	 */
	public V get(K k){
		int id = hash(k);
		if(table.get(id).getKey() == null){
			return null;
		}else{
			return table.get(id).getValue();
		}
	}

	/**
	 * return true if the table contains the input key
	 * return false if it does not.
	 * @param k
	 * @return
	 */
	public boolean contains(K k){
		if(table.get(hash(k)).getKey() == null){
			return false;
		}else{
			return true;
		}
	}

/**
 * Output to the console the table statistic.
 */
	public void stat(){
		int stat[] = new int[max_prob+1];
		int total_prob = 0;
		
		LinkedList<K> keyList = new LinkedList<K>();
		keyList.addAll(keys);
		K next = keyList.poll();
		while(next!=null){
			int id = hash(next);
			stat[table.get(id).prob]++;
			next = keyList.poll();
		}
		
		String statString = "[";
		for(int i = 0; i< stat.length; i++){
			total_prob += i*stat[i];
			statString += stat[i] + ", ";
		}
		statString = statString.substring(0, statString.length()-2) + "]";
		
		DecimalFormat fm = new DecimalFormat("#.#####");
		System.out.println("Hash Table Stats\n================");
		System.out.println("Number of entries: " + entries);
		System.out.println("Number of buckets: " + capacity);
		System.out.println("Histogram of Probes: " + statString);
		System.out.println("Fill percentage: " + fm.format(((double)entries/capacity)*100) + "%");
		System.out.println("Max probing: " + max_prob);
		System.out.println("Average probing: " + fm.format((double)total_prob/entries));
		System.out.println("\n");
	}
	
	/**
	 * Convert the table to a string.
	 */
	public String toString(){
		StringBuilder str = new StringBuilder();
		LinkedList<K> keyList = new LinkedList<K>();
		keyList.addAll(keys);
		K next = keyList.poll();
		while(next!=null){
			int id = hash(next);
			str.append(table.get(id).toString());
			str.append(",");
			next = keyList.poll();
		}
		
		return str.toString();
	}
	
	
	/**
	 * the hash function with linear probing.
	 * this function take in a input key, then create a unique index between 0 and capacity.
	 * @param key
	 * @return
	 */
	private int hash(K key){
		int id = key.hashCode()%capacity;
		if(id<0) id = id + capacity;
		int first = id;
		boolean fullRound = false;
		while(table.get(id).getKey() != null){
			if(table.get(id).getKey().toString().equals(key.toString())){
				probing = 0;
				break;
			}
			id++;
			probing++;
			if((id==first && fullRound)){
				System.out.println("fails! Out of bucket.");
				System.exit(0);
			}
			if(id >= capacity-1){
				id = id - capacity + 1;
				fullRound = true;
			}

		}
		return id;
	}
}
