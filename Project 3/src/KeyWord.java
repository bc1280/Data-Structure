///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// Files:            KeyWord.java
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
 * A KeyWord consists of a word and an integer (representing the number of
 * occurrences of the word). A word is a non-empty sequence of characters whose
 * letters are all lower-case. This class implements both the Comparable
 * <KeyWord> interface and the Prioritizable interface (more below) and that
 * you will need to override the equals method inherited from the Object class.
 * 
 * <p>Bugs: none known
 * 
 * @author Bill Chang
 */

public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	private String word; // non-empty lower-case word
	private int occurrence; // the number of occurrences of the word
	
	/**
	 * Constructor:
	 * Constructs a KeyWord with the given word (converted to lower-case) and 
	 * zero occurence. If the word is null or an empty string, an 
	 * IllegalArgumentException is thrown.
	 * @param word - the word for this KeyWord
	 */
	public KeyWord(String word) throws IllegalArgumentException {
		if ( word.equals(null) || word.equals("") ) {
			throw new IllegalArgumentException();
		}
		this.word = word.toLowerCase();
		this.occurrence = 0;
	}
	
	/**
	 * @return the word for this KeyWord
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * @return the number of occurrences for this KeyWord
	 */
	public int getOccurrences() {
		return occurrence;
	}
	
	/**
	 * Adds one to the number of occurrences for this KeyWord.
	 */
	public void increment() {
		occurrence ++;
	}
	
	/**
	 * @return the priority for this item
	 */
	public int getPriority() {
		return getOccurrences();
	}
	
	/**
	 * Compares the KeyWord with the one given. Two KeyWords are compared by 
	 * comparing the word associated with the two KeyWords, ignoring case 
	 * differences in the names.
	 * @param other - the KeyWord with which to compare this KeyWord
	 * @return - 1 if the word has higher priority
	 * 			 0 if they have the same priority
	 * 			-1 if other has higher priority
	 */
	public int compareTo(KeyWord other) {
		return word.compareTo(other.getWord().toLowerCase());
	}
	
	/**
	 * Compares this KeyWord to the specified object. The result is true if and
	 * only if the argument is not null and is a KeyWord object whose word is
	 * the same as the word of this KeyWord, ignoring case differences.
	 * @param other - the object with which to compare this KeyWord
	 * @return true if not null and the word and other are the same
	 * 		   false otherwise
	 */
	public boolean equals(Object other) {
		return (other != null && word.equals(((KeyWord) other).getWord()));
	}
}
