/**
 * Main Class for compressing project
 * @author Tan Pham
 * Created 02/17/2015
 * Last modified 02/18/2015
 * ***************************************************************************************************************************************************************
 * 
 */


import java.util.ArrayList;
import java.util.Comparator;


public class MyPriorityQueue <T>{

	public ArrayList <T> queue;
	public Comparator<T> comparator;

	/*
	 * Constructor.
	 */
	public MyPriorityQueue(){
		queue = new ArrayList <T>();
	}
	/*
	 * contructor that create a priority queue with a comparer.
	 */
	public MyPriorityQueue(Comparator<T> com){
		queue = new ArrayList <T>();
		comparator = com;
	}

	/*
	 * check if the queue is empty
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}

	/**
	 * offer new item and place it to a proper posision in queue.
	 * @param item
	 */
	public void offer(T item){
		queue.add(item);
		int itemIndex = queue.size() -1;
		while(itemIndex > 0){
			int parIndex = parentIndex(itemIndex);
			if( comparator.compare(queue.get(itemIndex),queue.get(parIndex)) > 0)break;
			swap(itemIndex,parIndex);
			itemIndex = parIndex;

		}
	}

	
	/**
	 * remove the first element and rearrange the array list.
	 * @return the first element in queue
	 */
	public T poll(){
		if( queue.size() == 0)  return null;        //no element to poll
		T rootItem = queue.get(0);
		ReplaceRoot(RemoveLast());
		return rootItem;
	}


	/**
	 * remove the last element is queue
	 * @return the last element is queue
	 */
	public T RemoveLast(){
		if( queue.size() == 0)  return null;   //no element to remove     
		return  queue.remove(queue.size()-1);
	}

	/**
	 * replace the first element (root of the heap) with a new element
	 * @param newRoot
	 */
	public void ReplaceRoot( T newRoot )
	{
		if (queue.size() == 0)	return; // cannot replace a nonexistent root

		queue.set(0, newRoot);
		Heapify(0);
	}

	/**
	 * this is a recursive method that re-arrange the array list so that the array store as a heap,
	 * with the first element is the root.
	 * for any node at the position i
	 * the left child is at position 2i+1,
	 * the right child is as position 2i+2 
	 * @param parentIndex the root index
	 */
	private void Heapify( int parentIndex )
    {
        int leastIndex = parentIndex;
        int leftIndex  = leftChildID(parentIndex);
        int rightIndex = rightChildID(parentIndex);

        // do we have a right child?
        if (rightIndex < queue.size()) 
            leastIndex = comparator.compare(queue.get(leastIndex),queue.get(rightIndex)) > 0 ? rightIndex : leastIndex;
        // do we have a left child?
        if (leftIndex < queue.size()) 
            leastIndex = comparator.compare(queue.get(leastIndex),queue.get(leftIndex)) > 0 ? leftIndex : leastIndex;

        if (leastIndex != parentIndex)
        {
            swap(leastIndex, parentIndex);
            Heapify(leastIndex);
        }
    }


	/*
	 * return the first element in queue but dont remove it.
	 */
	public T peek(){
		return queue.get(0);
	}

	/*
	 * return the size of the queue
	 */
	public int size(){
		return queue.size();
	}

	/**
	 * convert the queue into a string
	 */
	public String toString(){
		if(queue.size()==0) return "[]";
		String str = "[";

		for (int i = 0; i < queue.size(); i++){
			str += queue.get(i).toString() + ", ";
		}

		str = str.substring(0, str.length()-2) + "]";				
		return str;
	}

	
	/*
	 * get the parent index of a child
	 */
	private int parentIndex(int childIndex){
		return (childIndex-1)/2;
	}

	/*
	 * get the left child index
	 */
	private int leftChildID(int parID){
		return parID*2 +1;
	}

	/*
	 * get the right child index
	 */
	private int rightChildID(int parID){
		return parID*2 +2;
	}
	
	/**
	 * swap the element at position id1 and id2
	 * @param id1
	 * @param id2
	 */
	private void swap(int id1, int id2){
		T temp = queue.get(id1);
		queue.set(id1, queue.get(id2));
		queue.set(id2, temp);
	}
}