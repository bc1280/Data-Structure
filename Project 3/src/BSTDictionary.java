///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// Files:            BSTDictionary.java
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
 * This class implements the DictionaryADT interface using a binary search tree
 * of BSTnodes. 
 *
 * <p>Bugs: none known
 *
 * @author Bill Chang
 */

import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>>
implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary

	/**
	 * Constructor:
	 * Constructs a BSTDictionary with root to be null and numbers of elements
	 * is zero.
	 */
    public BSTDictionary() {
    	root = null;
    	numItems = 0;
    }
    
    /**
     * insert the designated key to the tree (calling private method)
     * @param key - the K object we want to insert
     */
    public void insert(K key) throws DuplicateException {
    	root = insert(key, root);
    }
	/**
	 * Helper method for insert option by being called recursively
	 * @param key - the key to insert
	 * @param n - root of the (sub)tree we want to add key into
	 * @return the new root for n
	 */
	private BSTnode<K> insert(K key, BSTnode<K> n) throws DuplicateException {
		// null case
		if (n == null) {
			numItems++;
			return new BSTnode<K>(key);
		}
		// Add to the left if it's smaller, add to the right if it's bigger
		// and DuplicateException is thrown if it's the same
		if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(insert(key, n.getLeft()));
			return n;
		} else if (key.compareTo(n.getKey()) == 0) {
			throw new DuplicateException();
		} else {
			n.setRight(insert(key, n.getRight()));
			return n;
		}
	}

    /**
     * delete the designated key from the tree (calling private method)
     * @param key - the K object we want to delete
     */
	public boolean delete(K key) {
		if (lookup(key) != null) {
			root = delete(key, root);
			numItems--;
			return true;
		}
		// return false if the key cannot be found in the tree
		return false;
	}

	/**
	 * Helper method for delete option by being called recursively
	 * @param key - the key we want to remove from the tree
	 * @param n - root of the (sub)tree to remove the key from
	 * @return the new root for n
	 */
	private BSTnode<K> delete(K key, BSTnode<K> n) {
		// null case
		if (n == null) {
			return null;
		}
		// if the key matches the current node, then we return according to the
		// smallest existed one
		if (key.compareTo(n.getKey()) == 0) {
			if (n.getLeft() == null && n.getRight() == null) {
				// no children case
				return null;
			} else if (n.getLeft() == null) {
				// single child case (right)
				return n.getRight();
			} else if (n.getRight() == null) {
				// single child case (left)
				return n.getLeft();
			} else {
				// two children case
				BSTnode<K> temp = n.getRight();
				while (temp.getLeft() != null) {
					temp = temp.getLeft();
				}
				n.setKey(temp.getKey());
				n.setRight(delete(temp.getKey(), n.getRight()));
				return n;
			}
		}

		// search the left subtree if the key is smaller than current node
		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(delete(key, n.getLeft()));
			return n;
		}
		// search the right subtree if the key is smaller than current node
		else {
			n.setRight(delete(key, n.getRight()));
			return n;
		}
	}

    /**
     * lookup the designated key (calling private method)
     * @param key - the K object we want to look up
     */
    public K lookup(K key) {
		return lookup(key, root);
	}
	/**
	 * Helper method to look up specific key by being called recursively
	 * @param key - the key we want to look up
	 * @param n - root of the tree
	 * @return the found key
	 */
	private K lookup(K key, BSTnode<K> n) {
		// null case
		if (n == null) {
			return null;
		}
		// Return n if it's the same
		// Lookup the right tree if it's bigger than n
		// Lookup the left tree if it's smaller than n
		if (key.compareTo(n.getKey()) == 0) {
			return n.getKey();
		} else if (key.compareTo(n.getKey()) < 0) {
			return lookup(key, n.getLeft());
		} else {
			return lookup(key, n.getRight());
		}
	}

	/**
	 * return true if BSTDictionary is empty; false otherwise
	 */
    public boolean isEmpty() {
        return numItems == 0; 
    }
    
    /**
     * return the number of nodes in BSTDictionary
     */
    public int size() {
        return numItems; 
    }
    
    /**
     * find the sum of the lengths of the paths to each key in the dictionary.
     * (calling private method)
     */
    public int totalPathLength() {
    	if (root == null) {
    		return 0;
    	} else {
            return totalPathLength(1, root);
    	}
    }
    /**
	 * Helper method to add path length accumulatively
     * @param depth - depth of the node (or path length of the node)
     * @param n - root of the (sub)tree we want to count the total path length 
     * @return the total path length
     */
    private int totalPathLength(int depth, BSTnode<K> n) {
    	// null case
		if (n == null) {
			return 0;
		}
		// add its own depth and goes to the next child if exists recursively
		if (n.getLeft() == null && n.getRight() == null) {
			return depth;
		} else if (n.getLeft() == null) {
			return depth + totalPathLength(depth + 1, n.getRight());
		} else if (n.getRight() == null) {
			return depth + totalPathLength(depth + 1, n.getLeft());
		} else {
			return depth + totalPathLength(depth + 1, n.getLeft()) +
					totalPathLength(depth + 1, n.getRight());
		}
		
    }
    
    /**
     * iterator of BSTDictionary
     */
    public Iterator<K> iterator() {
        return new BSTDictionaryIterator<K>(root);
    }
}
