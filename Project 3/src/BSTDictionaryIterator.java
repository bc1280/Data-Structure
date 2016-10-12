///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// Files:            BSTDictionaryIterator.java
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
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 * 
 * <p>Bugs: none known
 * 
 * @author Bill Chang
 */

import java.util.*;

public class BSTDictionaryIterator<K> implements Iterator<K> {

	private Stack<BSTnode<K>> stack;

	/**
	 * Constructor:
	 * Constructs an iterator for BSTDictionary
	 * @param root - root of the BST
	 */
	public BSTDictionaryIterator(BSTnode<K> root) {
		stack = new Stack<BSTnode<K>>();
		/** 
		 * Push nodes along the left route so that the constructor has a 
		 * worst-case complexity of O(height of BST).
		 */
		BSTnode<K> n = root;
		while (n != null) {
			stack.push(n);
			n = n.getLeft();
		}
	}
	
	/**
	 * Check if the iteration has more elements
	 * @return true if the iteration has more elements
	 * 		   false otherwise
	 */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     * If the stack is null or empty, throw an NoSuchElementException 
     * @return the next element in the iteration
     */
    public K next() throws NoSuchElementException {
    	if (!hasNext()) {
    		throw new NoSuchElementException();
    	}
    	BSTnode<K> temp = stack.pop();
    	if (temp.getRight() != null) {
    		BSTnode<K> n = temp.getRight();
    		while (n != null) {
    			// if right of temp exists, add it to the stack
    			stack.push(n);
    			n = n.getLeft();
    		}
    	}
    	return temp.getKey();
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
