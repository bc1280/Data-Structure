///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// Files:            ArrayHeap.java
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your partner's lab section number)
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements the PriorityQueueADT interface using an array-based 
 * implementation of a max heap
 * 
 * <p>Bugs: none known
 * 
 * @author Bill Chang
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;

	private E[] heap; // a heap meant for prioritize stored in array
	private int size; // size of the heap

	/**
	 * Constructs the array heap with no parameters
	 */
	public ArrayHeap() {
		heap = (E[]) (new Prioritizable[INIT_SIZE + 1]);
		size = 0;
	}

	/**
	 * Constructor:
	 * Constructs the array heap with a given initial size for the array
	 * throw an IllegalArgumentException if the input is not nonnegative
	 * @param size_of_array - the size of the array to initialize
	 */
	public ArrayHeap(int size_of_array) {
		if (size_of_array < 0) {
			throw new IllegalArgumentException();
		}
		// Initial the array and size
		heap = (E[]) (new Prioritizable[size_of_array + 1]);
		size = 0;
	}
	
	/**
	 * @return true if ArrayHeap is empty; false otherwise
	 */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * insert the item into the heap and follow the rule of PriorityQueueADT to
     * sort the heap
     * @param item - item to insert into the heap
     * @exception - NullPointerException is thrown if item is null
     */
    public void insert(E item) throws NullPointerException {
    	if (item == null) {
    		throw new NullPointerException();
    	}
    	// resize if the heap already reaches its capacity
        if (heap.length == size + 1) {
    		E[] temp = (E[]) (new Prioritizable[2* size + 1]);
        	for (int i = 1; i <= size; i++) {
        		temp[i] = heap[i];
        	}
        	heap = temp;
        }
        // add item to the end of the heap
        heap[size + 1] = item;
        size ++;
        //sort according to PriorityQueueADT
        int depth = size;
        while ( depth > 1 && heap[depth/2].getPriority() < 
        		heap[depth].getPriority()) {
        	E temp = heap[depth];
        	heap[depth] = heap[depth/2];
        	heap[depth/2] = temp;
            depth = depth/2;
        } 
    }

    /**
     * remove the element with the highest priority in the heap and return the
     * removed element
     * @return the removed element
     */
    public E removeMax() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		// set the root as the value to return
		E max = heap[1];

		// eliminate the root and swap with the very last element of the heap
		heap[1] = heap[size];
		heap[size] = null;
		size --;

		// swap parent with max priority of child if parent has lower priority
		int i = 1;
		while (i * 2 + 1 < size &&( heap[i].getPriority() <
				heap[i * 2].getPriority() ||
				heap[i].getPriority() < heap[i * 2 + 1].getPriority() )) {
			// swap with the left child since left child has higher priority
			if (heap[i * 2 + 1].getPriority() < heap[i * 2].getPriority()) {
				E temp = heap[i * 2];
				heap[i * 2] = heap[i];
				heap[i] = temp; 
				i = i * 2;
			} else {
				// swap with the right child since left child has higher
				// priority
				E temp = heap[i * 2 + 1];
				heap[i * 2 + 1] = heap[i];
				heap[i] = temp; 
				i = i * 2 + 1;
			}
		}
		return max;
	}

    /**
     * @return the maximum prioritized element of the heap
     * @exception NoSuchElementException is thrown if the heap is empty
     */
    public E getMax() throws NoSuchElementException {
    	if (isEmpty()) {
    		throw new NoSuchElementException();
    	}
        return heap[1];
    }

    /**
     * @return the size of the heap
     */
    public int size() {
        return size;
    }
}
