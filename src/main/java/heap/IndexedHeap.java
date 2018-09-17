/*

	Assignment 1

Name:		Yanis Hattab

Student ID:	260535922

*/




package heap;
import java.util.ArrayList;
import java.util.HashMap;

/*
 *   Here is the posted code for Assignment 1 in COMP 251  Winter 2014.
 * 
 *   Written by Michael Langer.
 *   This basic heap implementation is a modified version of Wayne and Sedgewick's code 
 *   (from their book, see link from their Coursera Algorithms course website).
 *   See other comments at the top of the Heap.java class.
 */

public class IndexedHeap{   

	private ArrayList<Double>    priorities;
	private ArrayList<String>  	 names;     //   Think of this as a map:  indexToNames

	/*  
	 * 	This is not just a heap;  it is an indexed heap!  To index directly into the heap,
	 *  we need a map. 
	 */
	
	private HashMap<String,Integer>  nameToIndex;    

	// constructor

	public IndexedHeap(){
		
		//  A node in the heap keeps track of a object name and the priority of that object. 
		
		names = new ArrayList<String>();
		priorities = new ArrayList<Double>();

		/*
		 * Fill the first array slot (index 0) with dummy values, so that we can use usual 
		 * array-based heap parent/child indexing.   See my COMP 250 notes if you don't know 
		 * what that means.
		 */
								   
		names.add( null );    	
		priorities.add( 0.0 );      

		//  Here is the map that we'll need when we want to change the priority of an object.
		
		nameToIndex  = new HashMap<String,Integer>();
	}

	private int parent(int i){     
		return i/2;
	}
	    		
	private int leftChild(int i){ 
	    return 2*i;
	}
	
	private int rightChild(int i){ 
	    return 2*i+1;
	}
	
	private boolean is_leaf(int i){
		return (leftChild(i) >= priorities.size()) && (rightChild(i) >= priorities.size());
	}
	
	private boolean oneChild(int i){ 
	    return (leftChild(i) < priorities.size()) && (rightChild(i) >= priorities.size());
	}
	
	/* 
	 *  The upHeap and downHeap methods use the swap method which you need to implement.
	 */
	
	private void upHeap(int i){
		if (i > 1) {   // min element is at 1, not 0
			if ( priorities.get(i) < priorities.get(parent(i)) ) {

				swap(parent(i),i);
				upHeap(parent(i));
			}
		}
	}

	private void downHeap(int i){

		// If i is a leaf, heap property holds
		if ( !is_leaf(i)){

			// If i has one child...
			if (oneChild(i)){
				//  check heap property
				if ( priorities.get(i) > priorities.get(leftChild(i)) ){
					// If it fails, swap, fixing i and its child (a leaf)
					swap(i, leftChild(i));
				}
			}
			else	// i has two children...

				// check if heap property fails i.e. we need to swap with min of children

				if  (Math.min( priorities.get(leftChild(i)), priorities.get(rightChild(i))) < priorities.get(i)){ 

					//  see which child is the smaller and swap i's value into that child, then recurse

					if  (priorities.get(leftChild(i)) < priorities.get(rightChild(i))){
						swap(i,   leftChild(i));
						downHeap( leftChild(i) );
					}
					else{
						swap(i,  rightChild(i));
						downHeap(rightChild(i));
					}
				}
		}
	}	

	public boolean contains(String name){
		if (nameToIndex.containsKey( name ))
			return true;
		else
			return false;
	}
	
	public int sizePQ(){
		return priorities.size()-1;   //  not to be confused with the size() of the underlying ArrayList, which included a dummy element at 0
	}

	public boolean isEmpty(){
		return sizePQ() == 0;   
	}
	
	public double getPriority(String name){
		if  (!contains( name ))
			throw new IllegalArgumentException("nameToIndex map doesn't contain key " + String.valueOf(name));
		return priorities.get( nameToIndex.get(name) );	
	}
	
	public double getMinPriority(){
		return priorities.get( 1 );	
	}

	public String nameOfMin(){
		return names.get(1);
	}

	/*
	 *   Implement all methods below
	 */
	
	/*
	 *   swap( i, j) swaps the values in the nodes at indices i and j in the heap.   
	 */

	private void swap(int i, int j){
		//I save in temporary variables the attributes of i
		double tempPriority = priorities.get(i);
		String tempName = names.get(i);

		//changing i to j in both lists
		priorities.set(i, priorities.get(j));
		names.set(i, names.get(j));

		//updating hashmap for i and j
		nameToIndex.remove(names.get(j));
		nameToIndex.put(names.get(j), i);
		nameToIndex.remove(tempName);
		nameToIndex.put(tempName, j);

		//changing j to i (that I saved)
		priorities.set(j, tempPriority);
		names.set(j, tempName);

	}

	
	
	//  returns (and removes) the name of the element with lowest priority value, and updates the heap
	
	public String removeMin(){
		//I save the name with lowest priority to return it later 
		String minName = nameOfMin();
		
		if(priorities.size() > 2){
		//I only update the heap if it contains more than 1 node
		//To remove the node at top of heap, I first swap it with the last ..
		//.. node of the heap, then I bubble down that one

			swap(1, (priorities.size()-1));

			//I remove the the node with lowest priority (now at the end of the heap) 
			//from the names and priorities lists and from the hashmap

			nameToIndex.remove(names.remove(priorities.size()-1));
			priorities.remove(priorities.size()-1);
			downHeap(1);
					
		}
		else{
		//I just remove the node as it's the only one in the heap
			names.remove(1);
			priorities.remove(1);
		}
		return minName; 
	}	

	/*
	 * There are two add methods.  The first assumes a specific priority.  That's the one
	 * you need to implement.   The second gives a default priority of Double.POSITIVE_INFINITY	  
	 */
	
	public void  add(String name, double priority){

		if  (contains( name ))
			throw new IllegalArgumentException("Trying to add " + String.valueOf(name) + ", but its already there.");

		//I add the new values to the end of the lists and update the hashmap
		priorities.add(priority);
		names.add(name);
		nameToIndex.put(name, names.indexOf(name));
		//I fix the heap by bubbling up the last node (the one i just added)
		upHeap(priorities.size()-1);

		
	}
	
	public void  add(String name){
		add(name, Double.POSITIVE_INFINITY);
	}

	/*
	 *   If new priority is different from the current priority then change the priority (and possibly modify the heap). 
	 *   If the name is not there, then throw an exception.
	 */
	
	public void changePriority(String name, double priority){

		if  (!contains( name ))
			throw new IllegalArgumentException("Trying to change priority of " + String.valueOf(name) + ", but its not there.");

		//I save the index of the name
		int index = nameToIndex.get(name);

		if (!(priorities.get(index) == priority)){
			//If the the new priority is not the same as the one already stored,
                        // I change it
			priorities.set(index, priority);
			
			//Check if node is too high or too low, and fix heap accordingly
			if(priorities.get(parent(index)) > priority) {
				upHeap(index);			
			}else{
				downHeap(index);
			}
			
			
		}

	}
	
}
