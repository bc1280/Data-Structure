///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Project1
// Files:            CustomerDatabase.java, InteractiveDBTester.java
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Main class file that contains all options to read, write, edit, and delete
 * the data stored in the database and user interface.
 *
 * <p>Bugs: none known
 *
 * @author Bill Chang
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InteractiveDBTester {
    public static void main(String[] args) {
    	
    	/**
    	 * Detect command-line argument and exit the program if fail to execute
    	 */
    	if (args.length != 1){
    		System.out.println("Please provide input file as command-line "
    				+ "argument");
    		System.exit(0);
    	}

    	CustomerDatabase db = new CustomerDatabase();
		File sampleInput = new File(args[0]);
		
		/**
		 * Use scanner to read data by line, and then split it up by parts. 
		 * The first part would be the customer and the rest of the parts from 
		 * the same line would be included in the wishlist of the customer.
		 * import them all into the customer list.
		 */
		try(Scanner fileScan = new Scanner(sampleInput).useDelimiter("\\n")){
			while(fileScan.hasNext()){
				String temp = fileScan.next();
				String[] parts = temp.split(",");
				if( !db.containsCustomer(parts[0]) ){
					db.addCustomer(parts[0]);
				}
				if( parts.length > 1) {
					if( parts[1] != null) {
						for (int i = 1; i < parts.length; i++) {
							db.addProduct(parts[0], parts[i]);
						}
					}
				}
			}
			fileScan.close();
		}

		/**
		 * Throw FileNotFoundException if we cannot access the file 
		 */
		catch (FileNotFoundException e) {
			System.out.println("Error: Cannot access input file\n");
			System.exit(0);
		}

        Scanner stdin = new Scanner(System.in);  // for reading console input
        printOptions();
        boolean done = false;
        while (!done) {
            System.out.print("Enter option ( dfhisqr ): ");
            String input = stdin.nextLine();
            input = input.toLowerCase();  // convert input to lower case

            // only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                if (input.length() > 1) {
                    // trim off any leading or trailing spaces
                    remainder = input.substring(1).trim(); 
                }

                switch (choice) {
                
                /**
                 * If the product is found in the database, discontinue all
                 * of them that are in the database and print out the 
                 * message. Otherwise, just print out "product not found".
                 */
                case 'd':
                	if (!db.containsProduct(remainder) || remainder.length() 
                			< 1) {
                		System.out.println("product not found");
                	} else {
                		db.removeProduct(remainder);
                		System.out.println("product discontinued");
                	}
                    break;

                /**
                 * Print out the wishlist of the customer if the customer is in
                 * the database. Otherwise, print out "customer not found".
                 */
                case 'f':
                	if (!db.containsCustomer(remainder) || remainder.length() 
                			< 1){
                		System.out.println("customer not found");
                	} else {
                		System.out.print(remainder + ":");
                		System.out.print(db.getProducts(remainder).toString().
                				replace("[", "").replace("]", "").
                				replace(" ", "") + "\n");
                	}
                    break;

                case 'h': 
                    printOptions();
                    break;

                /**
                 * Display information about this customer database.
                 * This case contains 4 major parts.
                 */
                case 'i':
                	/*
                	 * Display on a line: "Customers: integer, Products: 
                	 * integer"
                	 */
                	/**
                	 * We can retrieve the number of customers by finding the 
                	 * size of the database.
                	 * We can create a productlist and use iterator to go
                	 * thorough all the wishlists to determine if a product
                	 * is already in our productlist. If it's not, then we add
                	 * it to the productlist. In the end, just return
                	 * the size of productlist.
                	 */
                	List<String> productList = new ArrayList<String>();
                	Iterator<Customer> itr = db.iterator();
                	while (itr.hasNext()) {
                		List<String> customerWishlist = db.getProducts(
                				itr.next().getUsername());
                		if (customerWishlist.size() > 0) {
                    		for (int i = 0; i < customerWishlist.size(); i++) {
                    			if (!productList.contains(
                    					customerWishlist.get(i))) {
                    				productList.add(customerWishlist.get(i));
                    			}
                    		}
                		}
                	}
                	System.out.println("Customers: " + db.size() + ", Products:"
                			+ " " + productList.size());
                	/*
                	 * Display on a line: "# of products/customer: most integer,
                	 *  least integer, average integer"
                	 */
                	/**
                	 * Create pMax, pMin, pAvg and initiate. Then update their
                	 * values while we go through all the customers in the 
                	 * database.
                	 */
                	int pMax = 0;
                	int pMin = productList.size();
                	double pAvg = 0;
                	int pCounter = 0;
                	itr = db.iterator();
                	while (itr.hasNext()) {
                		int temp = db.getProducts(
                				itr.next().getUsername()).size();
                		pMax = Math.max( pMax, temp );
                		pMin = Math.min( pMin, temp );
                		pAvg = ( pAvg * pCounter + temp ) / ( pCounter + 1 );
                		pCounter ++;
                	}
                	System.out.println("# of products/customer: most " + pMax + 
                			", least " + pMin + ", average " + 
                			Math.round(pAvg));
                	/*
                	 * Display on a line: "# of customers/product: most integer,
                	 *  least integer, average integer"
                	 */
                	/**
                	 * Create cMax, cMin, cAvg and initiate. Then update their
                	 * values while we go through all the products in the 
                	 * productlist.
                	 */
                	int cMax = 0;
                	int cMin = Math.min( productList.size(), db.size());
                	double cAvg = 0;
                	int cCounter = 0;
                	Iterator<String> itr2 = productList.iterator();
                	while (itr2.hasNext()) {
                		int temp = db.getCustomers(itr2.next()).size();
                		cMax = Math.max( cMax, temp);
                		cMin = Math.min( cMin, temp);
                		cAvg = ( cAvg * cCounter + temp ) / ( cCounter + 1);
                		cCounter ++;
                	}
                	System.out.println("# of customers/product: most " + cMax + 
                			", least " + cMin + ", average " + 
                			Math.round(cAvg));
                	/*
                	 * Display on a line: "Most popular product: product(s)
                	 *  [integer]"
                	 */
                	/**
                	 * Create a new hotlist, and go through the productlist.
                	 * If a product's size of the wishlist matches cMax, then
                	 * we add it to the hotlist. In the end, we print out the
                	 * hotlist and cMax.
                	 */
                	List<String> hotList = new ArrayList<String>();
                	itr2 = productList.iterator();
                	while (itr2.hasNext()) {
                		String tempP = itr2.next();
                		int temp = db.getCustomers(tempP).size();
                		if ( temp == cMax ) {
                			hotList.add(tempP);
                		}
                	}
                	System.out.print("Most popular product: ");
                	System.out.print( hotList.toString().replace("[", "").
                			replace("]", "").replace(" ", "") );
                	System.out.println(" [" + cMax + "]");
                    break;
                
                /**
                 * If the product is found in the database, print out all the
                 * customers whose wishlist contains that product. Otherwise, 
                 * print out "product not found".
                 */
                case 's':
                	if (!db.containsProduct(remainder) || remainder.length() 
                			< 1){
                		System.out.println("product not found");
                	} else {
                		System.out.print(remainder + ":");
                		System.out.print(db.getCustomers(remainder).toString().
                				replace("[", "").replace("]", "").
                				replace(" ", "") + "\n");
                	}
                    break;

                case 'q':
                    done = true;
                    System.out.println("quit");
                    break;

                /**
                 * If the customer is found in the database, remove the customer
                 * from the database and print out the message.
                 * Otherwise, just print out "customer not found".
                 */
                case 'r':
                	if (!db.containsCustomer(remainder) || remainder.length() 
                			< 1) {
                		System.out.println("customer not found");
                	} else {
                		db.removeCustomer(remainder);
                		System.out.println("customer removed");
                	}
                    break;

                default:  // ignore any unknown commands
                    break;
                }
            }
        }
        
        stdin.close();
    }

    /**
     * Prints the list of command options along with a short description of
     * one.  This method should not be modified.
     */
    private static void printOptions() {
        System.out.println("d <product> - discontinue the given <product>");
        System.out.println("f <customer> - find the given <customer>");
        System.out.println("h - display this help menu");
        System.out.println("i - display information about this customer "
        		+ "database");
        System.out.println("s <product> - search for the given <product>");
        System.out.println("q - quit");
        System.out.println("r <customer> - remove the given <customer>");
    }
}
