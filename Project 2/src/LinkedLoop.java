///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  MessageLoopEditor.java
// Files:            LinkedLoop.java
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your partner's lab section number)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;

public class LinkedLoop<E> implements Loop<E>, Iterable<E> {
	/**
	 * Current node (doubly-linked)
	 */
	private DblListnode<E> curr;
	/**
	 * Numbers of nodes inside the loop
	 */
	private int num;
	/**
	 * A constructor that takes no arguments and creates an empty loop
	 */
	public LinkedLoop(){
		curr = null;
		num = 0;
	}
	/**
	 * Moves the current item backward one item resulting in the item that is
	 * immediately before the current item becoming the current item.
	 */
	public void backward() {
		curr = curr.getPrev();
	}
	
	public void forward() {
		curr = curr.getNext();
	}
	
	public E getCurrent() throws EmptyLoopException {
		// throw exception if empty
		if (isEmpty()) {
			throw new EmptyLoopException();
		}
		return curr.getData();
	}
	
	public void insert(E item) {
		DblListnode<E> itemToAppend = new DblListnode<E>(item);
		if(isEmpty()) {
			curr = itemToAppend;
			curr.setNext(curr);
			curr.setPrev(curr);
			num ++;
		} else {
			itemToAppend.setNext(curr);
			itemToAppend.setPrev(curr.getPrev());
			curr.getPrev().setNext(itemToAppend);
			curr.setPrev(itemToAppend);
			curr = itemToAppend;
			num ++;
		}
	}
	
	public boolean isEmpty() {
		if (num == 0) {
			return true;
		}
		return false;
	}
	
	public Iterator<E> iterator() {
		return new LinkedLoopIterator<E>(curr, num);
	}
	
	public E removeCurrent() throws EmptyLoopException {
		if (isEmpty()) {
			throw new EmptyLoopException();
		}
		if (num == 1) {
			DblListnode<E> temp = curr;
			curr = null;
			num--;
			return temp.getData();
		} else {
			DblListnode<E> temp = curr;
			curr = temp.getNext();
			curr.setPrev(temp.getPrev());
			temp.getPrev().setNext(curr);
			num--;
			return temp.getData();
		}
	}
	
	public int size() {
		return num;
	}
}
