///////////////////////////////////////////////////////////////////////////////
// Title:            Project4
// Files:            ComparisonSort.java, SortObject.java, TestSort.java (Main) 
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Online Reading
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements six different comparison sorts (and an optional
 * seventh sort for extra credit):
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort</li>
 * <li>heap sort</li>
 * <li>selection2 sort</li>
 * <li>(extra credit) insertion2 sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {
	private static int moves = 0;

    /**
     * Sorts the given array using the selection sort algorithm. You may use
     * either the algorithm discussed in the on-line reading or the algorithm
     * discussed in lecture (which does fewer data moves than the one from the
     * on-line reading). Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selectionSort(E[] A) {
        for (int i = 0; i < A.length - 1; i++) {
        	int minIndex = i;
        	// A[0],..., A[i-1] is sorted and all smaller than A[i],..., A[n-1]
        	for (int j = i+1; j < A.length; j++) {
        		if (A[j].compareTo(A[minIndex]) < 0) {
        			minIndex = j;
        		}
        	}
        	// swap between A[i] and A[minIndex]
			swap(A, minIndex, i);
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm. Note: after
     * this method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void insertionSort(E[] A) {
    	for (int i = 1; i < A.length; i++) {
    		E temp = A[i];
			moves++;
    		// A[0],..., A[i-1] are sorted
			int j;
    		for (j = i-1; j >= 0 && A[j].compareTo(temp) > 0; j--) {
    			A[j+1] = A[j];
    			moves++;
    			// move A[j] over
    		}
    		A[j+1] = temp;
			moves++;
    	}
    }

    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void mergeSort(E[] A) {
        // call the aux. function to do all the work
        mergeAux(A, 0, A.length - 1); 
    }
     
    private static <E extends Comparable<E>> void mergeAux(E[] A, int low, 
    	int high) {
        // base case
        if (low == high) return;
     
        // recursive case
        
     // Step 1: Find the middle of the array (conceptually, divide it in half)
        int mid = (low + high) / 2;
         
     // Steps 2 and 3: Sort the 2 halves of A
        mergeAux(A, low, mid);
        mergeAux(A, mid+1, high);
     
     // Step 4: Merge sorted halves into an auxiliary array
        E[] temp = (E[])(new Comparable[high-low+1]);
        int left = low;    // index into left half
        int right = mid+1; // index into right half
        int pos = 0;       // index into temp
         
        while ((left <= mid) && (right <= high)) {
        // choose the smaller of the two values "pointed to" by left, right
        // copy that value into temp[pos]
        // increment either left or right as appropriate
        // increment pos
			if (A[left].compareTo(A[right]) >= 0) {
				temp[pos] = A[right];
				moves++;
				right++;
			} else {
				temp[pos] = A[left];
				moves++;
				left++;
			}
			pos++;
        }
        
        // when one of the two sorted halves has "run out" of values, but
        // there are still some in the other half, copy all the remaining 
        // values to temp
        // Note: only 1 of the next 2 loops will actually execute
        while (left <= mid) { 
			temp[pos] = A[left];
			moves++;
			left++;
			pos++;
        }
        while (right <= high) {
			temp[pos] = A[right];
			moves++;
			right++;
			pos++;
        }
     
        // all values are in temp; copy them back into A
        moves = moves + temp.length;
        System.arraycopy(temp, 0, A, low, temp.length);
    }


    /**
     * Sorts the given array using the quick sort algorithm, using the median of
     * the first, last, and middle values in each segment of the array as the
     * pivot value. Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A   the array to sort
     */
    public static <E extends Comparable<E>> void quickSort(E[] A) {
		quickAux(A, 0, A.length - 1);
	}

	/**
	 * Recursive method initially called by the wrapper method quickSort which
	 * starts the quick sort algorithm.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to quick sort
	 * @param high
	 *            the index of the high end of array to quick sort
	 */
	private static <E extends Comparable<E>> void quickAux(E[] A, int low,
			int high) {
		if (high - low < 2) {
			// Base case
			// If there are two items sort manually
			if (high - low == 1) {
				if (A[low].compareTo(A[high]) > 0) {
					swap(A, low, high);
				}
			}
			// If there is only one ignore
		} else {
			// Recursive case
			int right = partition(A, low, high);
			quickAux(A, low, right);
			quickAux(A, right + 2, high);
		}
	}

	/**
	 * Partition method of quick sort which sorts based on a pivot determined by
	 * a median of three.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to quick sort
	 * @param high
	 *            the index of the high end of array to quick sort
	 * @return position of where the swapped pivot was moved to
	 */
	private static <E extends Comparable<E>> int partition(E[] A, int low,
			int high) {
		// precondition: A.length > 3

		E pivot = medianOfThree(A, low, high); // this does step 1
		int left = low + 1, right = high - 2;
		while (left <= right) {
			while (A[left].compareTo(pivot) < 0)
				left++;
			while (A[right].compareTo(pivot) > 0)
				right--;
			if (left <= right) {
				swap(A, left, right);
				left++;
				right--;
			}
		}
		swap(A, left, high - 1); // step 4
		return right;
	}

	/**
	 * Perform the median of three by the median of the first, middle, and last
	 * values of the array. Swaps the values when appropriate.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to compare
	 * @param high
	 *            the index of the high end of array to compare
	 * @return the median object
	 */
	private static <E extends Comparable<E>> E medianOfThree(E[] A, int low,
			int high) {
		// Find the mid of the array
		int mid = (low + high) / 2;
		// order low & mid
		if (A[low].compareTo(A[mid]) > 0) {
			swap(A, low, mid);
		}
		// order low & high
		if (A[low].compareTo(A[high]) > 0) {
			swap(A, low, high);
		}
		// order mid & high
		if (A[mid].compareTo(A[high]) > 0) {
			swap(A, mid, high);
		}

		swap(A, mid, high - 1); // order mid and high - 1 (pivot)
		return A[high - 1]; // return pivot
	}


    /**
     * Sorts the given array using the heap sort algorithm outlined below. Note:
     * after this method finishes the array is in sorted order.
     * <p>
     * The heap sort algorithm is:
     * </p>
     * 
     * <pre>
     * for each i from 1 to the end of the array
     *     insert A[i] into the heap (contained in A[0]...A[i-1])
     *     
     * for each i from the end of the array up to 1
     *     remove the max element from the heap and put it in A[i]
     * </pre>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void heapSort(E[] A) {
		int length = A.length;
		int size = 0;
		E[] heap = (E[]) (new Comparable[length + 1]);

		// for each i from 1 to the end of the array
		// insert A[i] into the heap (contained in A[0]...A[i-1])
		for (int i = 0; i < length; i++) {
			// Put the item in the next spot in the array/heap
			size++;
			heap[size] = A[i];
			moves++;

			// Swapping the value up
			int child = size;
			while (heap[child / 2] != null
					&& heap[child / 2].compareTo(heap[child]) < 0) {
				E temp = heap[child / 2];
				moves++;
				heap[child / 2] = heap[child];
				moves++;
				heap[child] = temp;
				moves++;
				child = child / 2;
			}
		}

		// for each i from the end of the array up to 1
		// remove the max element from the heap and put it in A[i]
		for (int i = length - 1; i >= 0; i--) {
			// Save the root as the value to put at the end of the array
			A[i] = heap[1];
			moves++;
			// Set the last child as the root
			heap[1] = heap[size];
			moves++;
			// Set the old last child as null
			heap[size--] = null;

			// Heapify process
			int parent = 1;
			while (parent * 2 + 1 < heap.length
					&& ((heap[parent * 2] != null && heap[parent * 2]
							.compareTo(heap[parent]) > 0) ||
							(heap[parent * 2 + 1] != null &&
							heap[parent * 2 + 1].compareTo(heap[parent]) 
							> 0))) {
				// swap if the children are bigger
				E temp = heap[parent];
				moves++;
				// find the max child to swap
				if (heap[parent * 2] != null && heap[parent * 2 + 1] != null) {
					if (heap[parent * 2].compareTo(heap[parent * 2 + 1]) > 0) {
						// swap if the left is bigger
						heap[parent] = heap[parent * 2];
						moves++;
						heap[parent * 2] = temp;
						moves++;
						parent = parent * 2;
					} else {
						// swap if the right is bigger
						heap[parent] = heap[parent * 2 + 1];
						moves++;
						heap[parent * 2 + 1] = temp;
						moves++;
						parent = parent * 2 + 1;
					}

				} else if (heap[parent * 2] != null) {
					// swap if left child is bigger
					heap[parent] = heap[parent * 2];
					moves++;
					heap[parent * 2] = temp;
					moves++;
					parent = parent * 2;
				} else {
					// swap if right child is bigger
					heap[parent] = heap[parent * 2 + 1];
					moves++;
					heap[parent * 2 + 1] = temp;
					moves++;
					parent = parent * 2 + 1;
				}
			}
		}
    }

    /**
     * Sorts the given array using the selection2 sort algorithm outlined
     * below. Note: after this method finishes the array is in sorted order.
     * <p>
     * The selection2 sort is a bi-directional selection sort that sorts
     * the array from the two ends towards the center. The selection2 sort
     * algorithm is:
     * </p>
     * 
     * <pre>
     * begin = 0, end = A.length-1
     * 
     * // At the beginning of every iteration of this loop, we know that the 
     * // elements in A are in their final sorted positions from A[0] to
     * // A[begin-1]  and from A[end+1] to the end of A.  That means that 
     * // A[begin] to A[end] are still to be sorted.
     * do
     *     use the MinMax algorithm (described below) to find the minimum and
     *     maximum values between A[begin] and A[end]
     *     
     *     
     *     swap the maximum value and A[end]
     *     swap the minimum value and A[begin]
     *     
     *     ++begin, --end
     * until the middle of the array is reached
     * </pre>
     * <p>
     * The MinMax algorithm allows you to find the minimum and maximum of N
     * elements in 3N/2 comparisons (instead of 2N comparisons). The way to do
     * this is to keep the current min and max; then
     * </p>
     * <ul>
     * <li>take two more elements and compare them against each other</li>
     * <li>compare the current max and the larger of the two elements</li>
     * <li>compare the current min and the smaller of the two elements</li>
     * </ul>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selection2Sort(E[] A) {
		int begin = 0; 
		int length = A.length;
		int end = length - 1;
		int max = length - 1; // position
		int min = 0; // position
		 while (end >= begin) {
			int left = begin;
			int right = end;
			while (left <= right) {
				if (A[left].compareTo(A[right]) > 0) {
					if (A[left].compareTo(A[max]) > 0) 
						max = left;
					if (A[right].compareTo(A[min]) < 0) 
						min = right;
				}
				else if(A[left].equals(A[right])){
					if (A[left].compareTo(A[min]) < 0) 
						min = left;
					if (A[right].compareTo(A[max]) > 0) 
						max = right;
				}
				else {
					if (A[right].compareTo(A[max]) > 0) 
						max = right;
					if (A[left].compareTo(A[min]) < 0) 
						min = left;
				}
				left++; 
				right--;
			}
			// 6 cases depends on the state of begin and end
			if (begin != max && end != min){
				swap(A, min, begin);
				swap(A, max, end);
			}
			else if(begin == max && end != min){
				swap(A, max, end);
				swap(A, min, begin);
			}
			else if(begin != max && end == min){
				swap(A, min, begin);
				swap(A, max, end);
			}
			else if(begin == min && end != max){
				swap(A, max, end);
			}
			else if(begin != min && end == max) {
				swap(A, min, begin);
			}
			else if(begin == min && end == max){
				swap(A, min, begin);
				swap(A, max, end);
			}
			else {
				E tmp = A[min];
				moves++;
				swap(A, max, end);
				A[begin] = tmp;
				moves++;
			}
			begin++; 
			min = begin;
			end--;
			max = end;
		}
	}
    
    /**
     * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort 
     * algorithm outlined below.  Note: after this method finishes the array 
     * is in sorted order.
     * <p>
     * The insertion2 sort is a bi-directional insertion sort that sorts the 
     * array from the center out towards the ends.  The insertion2 sort 
     * algorithm is:
     * </p>
     * <pre>
     * precondition: A has an even length
     * left = element immediately to the left of the center of A
     * right = element immediately to the right of the center of A
     * if A[left] > A[right]
     *     swap A[left] and A[right]
     * left--, right++ 
     *  
     * // At the beginning of every iteration of this loop, we know that the
     * // elements in A from A[left+1] to A[right-1] are in relative sorted
     * // order.
     * do
     *     if (A[left] > A[right])
     *         swap A[left] and A[right]
     *  
     *     starting with with A[right] and moving to the left, use insertion sort 
     *     algorithm to insert the element at A[right] into the correct location 
     *     between A[left+1] and A[right-1]
     *     
     *     starting with A[left] and moving to the right, use the insertion sort 
     *     algorithm to insert the element at A[left] into the correct location 
     *     between A[left+1] and A[right-1]
     *  
     *     left--, right++
     * until left has gone off the left edge of A and right has gone off the right 
     *       edge of A
     * </pre>
     * <p>
     * This sorting algorithm described above only works on arrays of even 
     * length.  If the array passed in as a parameter is not even, the method 
     * throws an IllegalArgumentException
     * </p>
     *
     * @param  A the array to sort
     * @throws IllegalArgumentException if the length or A is not even
     */    
    public static <E extends Comparable<E>> void insertion2Sort(E[] A) { 	
    	int left = A.length / 2, right = A.length / 2 + 1;
		if ( A.length % 2 == 1) {
			throw new IllegalArgumentException();
		}
		// Sort the initial left and right values
		if (A[left].compareTo(A[right]) > 0) {
			swap(A, left, right);
		}
		left--;
		right++;
		
		// until left has gone off the left edge of A and right has gone off
		// the right 
		while (left >= 0 && right < A.length) {
			if (A[left].compareTo(A[right]) > 0) {
				swap(A, left, right);
			}

			
			// starting with with A[right] and moving to the left, use
			// insertion sort algorithm to insert the element at A[right] into
			// the correct location between A[left+1] and A[right-1]
			E temp = A[right];
			moves++;
			int i = right - 1;
			while ((i >= left + 1) && (A[i].compareTo(temp) > 0)) {
				A[i + 1] = A[i];
				moves++;
				i--;
			}
			A[i+1] = temp;
			moves++;

			// starting with A[left] and moving to the right, use the insertion
			// sort algorithm to insert the element at A[left] into the correct
			// location between A[left+1] and A[right-1]
			temp = A[left];
			moves++;
			i = left + 1;
			while ((i < right) && (A[i].compareTo(temp) < 0)) {
				 // move one value over one place to the left
				A[i-1] = A[i];
				moves++;
				i++;
			}
			A[i-1] = temp;
			moves++;

			left--;
			right++;
		}
    }
    
	private static <E extends Comparable<E>> void swap(E[] A, int left,
			int right) {
		// Swap the values with a temp variable and record the movements
		E temp = A[left];
		moves++;
		A[left] = A[right];
		moves++;
		A[right] = temp;
		moves++;
	}

    /**
     * Internal helper for printing rows of the output table.
     * 
     * @param sort          name of the sorting algorithm
     * @param compares      number of comparisons performed during sort
     * @param moves         number of data moves performed during sort
     * @param milliseconds  time taken to sort, in milliseconds
     */
    private static void printStatistics(String sort, int compares, int moves,
                                        long milliseconds) {
        System.out.format("%-23s%,15d%,15d%,15d\n", sort, compares, moves, 
                          milliseconds);
    }
    
    /**
     * To acquire total moves of the method
     * @return moves - numbers of total move
     */
    private static int getMoves() {
    	return moves;
    }
    
    /**
     * testing if the sort is ascending sort
     * @param A
     * @return true if it's ascending sort; false otherwise
     */
	private static <E extends Comparable<E>> boolean ascendingSort(E[] A) {
		int length = A.length;
		for (int i = 0; i < length - 1; i++) {
			if (A[i].compareTo(A[i+1]) > 0) {
				return false;
			}
		}		
		return true;
	}
    
    /**
     * To reset moves
     */
    private static void resetMoves() {
    	moves = 0;
    }

    /**
     * Sorts the given array using the six (seven with the extra credit)
     * different sorting algorithms and prints out statistics. The sorts 
     * performed are:
     * <ul>
     * <li>selection sort</li>
     * <li>insertion sort</li>
     * <li>merge sort</li>
     * <li>quick sort</li>
     * <li>heap sort</li>
     * <li>selection2 sort</li>
     * <li>(extra credit) insertion2 sort</li>
     * </ul>
     * <p>
     * The statistics displayed for each sort are: number of comparisons, 
     * number of data moves, and time (in milliseconds).
     * </p>
     * <p>
     * Note: each sort is given the same array (i.e., in the original order) 
     * and the input array A is not changed by this method.
     * </p>
     * 
     * @param A  the array to sort
     */
    static public void runAllSorts(SortObject[] A) {
        System.out.format("%-23s%15s%15s%15s\n", "algorithm", "data compares", 
                          "data moves", "milliseconds");
        System.out.format("%-23s%15s%15s%15s\n", "---------", "-------------", 
                          "----------", "------------");

        SortObject[] selectionA = A.clone();
		long before = System.currentTimeMillis();
		selectionSort(selectionA);
		long after = System.currentTimeMillis();
		printStatistics("selection", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(selectionA)) {
			System.err.println("Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] insertionA = A.clone();
        before = System.currentTimeMillis();
		insertionSort(insertionA);
		after = System.currentTimeMillis();
		printStatistics("insertion", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(insertionA)) {
			System.err.println("Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] mergeA = A.clone();
        before = System.currentTimeMillis();
		mergeSort(mergeA);
		after = System.currentTimeMillis();
		printStatistics("merge", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(mergeA)) {
			System.err.println("Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] quickA = A.clone();
        before = System.currentTimeMillis();
		quickSort(quickA);
		after = System.currentTimeMillis();
		printStatistics("quick", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(quickA)) {
			System.out.println("quickSort is Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] heapA = A.clone();
        before = System.currentTimeMillis();
		heapSort(heapA);
		after = System.currentTimeMillis();
		printStatistics("heap", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(heapA)) {
			System.out.println("heapSort is Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] selection2A = A.clone();
        before = System.currentTimeMillis();
        selection2Sort(selection2A);
		after = System.currentTimeMillis();
		printStatistics("selection2", SortObject.getCompares(), getMoves(),
				after - before);
		if (!ascendingSort(selection2A)) {
			System.out.println("selction2Sort is Not Ascending Sort");
		}
		SortObject.resetCompares();
		resetMoves();
		
        SortObject[] insertion2A = A.clone();
        before = System.currentTimeMillis();
		insertion2Sort(insertion2A);
		after = System.currentTimeMillis();
		printStatistics("insertion2", SortObject.getCompares(), getMoves(),
				after - before);
		SortObject.resetCompares();
		resetMoves();
    }
}
