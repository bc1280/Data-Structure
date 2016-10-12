///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  MessageLoopEditor.java
// Files:            LinkedLoopIterator.java
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your partner's lab section number)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedLoopIterator<E> implements Iterator<E>{
	private DblListnode<E> curr;
	private int num;
	private int pos;
	
	LinkedLoopIterator(DblListnode<E> curr, int num) {
		this.curr = curr;
		this.num = num;
		this.pos = 0;
	}
	
	public boolean hasNext() {
		if (this.pos < this.num) {
			return true;
		} else {
			return false;
		}
	}
	
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E temp = curr.getData();
		curr = curr.getNext();
		pos++;
		return temp;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}


}
