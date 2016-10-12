///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  InteractiveDBTester.java
// File:             CustomerDatabase.java
// Semester:         Summer 2016
//
// Author:           Bill Chang
// Email:            bchang24@wisc.edu
// CS Login:         billc
// Lecturer's Name:  Amanda Strominger
// Lab Section:      (your lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Represents the customer database that stores customers in a list 
 * Bugs: none known
 * @author Bill Chang
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CustomerDatabase implements Iterable<Customer> {
	
	/** The list of customers */
	private ArrayList<Customer> list;
	
	/**
     * Constructor:constructs a database to hold the customers in ArrayList.
     */
	public CustomerDatabase(){
		list = new ArrayList<Customer>();
	}
	
	/**
	 * Adds a customer with specified username c to the end of the database. 
	 * If a customer with username c is already in the database, return
	 * from the method.
	 * @param c Username of the customer
	 */
	public void addCustomer(String c){
		if(!containsCustomer(c)){
			Customer e = new Customer(c);
			list.add(e);
		}
	}
	
	/**
	 * Adds the product with specified name to the wish list for customer 
	 * specified with username in the database. If customer is not in the 
	 * database, throws a java.lang.IllegalArgumentException. If product is 
	 * already in the wish list for customer, return from the method.
	 * @param c Username of customer
	 * @param p Name of product
	 */
	public void addProduct(String c, String p){
		try {
			if(!hasProduct(c, p)){
				for (int i = 0; i < list.size(); i++) {
					if( c.equals(list.get(i).getUsername()) ){
						list.get(i).getWishlist().add(p);
					}
				}
			}
		} catch (IllegalArgumentException e){
			System.out.println("Customer" + c + "is not in the datavase.");
		}
	}
	
	/**
	 * Returns true if and only if customer c is in the database.
	 * @param c Username of customer
	 * @return True if database has the customer c, otherwise returns false.
	 */
	public boolean containsCustomer(String c){
		for (int i = 0; i < this.list.size(); i++){
			if( c.equals(list.get(i).getUsername()) ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if and only if product p appears in at least one customer's
	 * wish list in the database.
	 * @param p Name of product
	 * @return True if any customer has the following product with specified 
	 * name, otherwise returns false.
	 */
	public boolean containsProduct(String p){
		for (int i = 0; i < this.list.size(); i++){
			for (int j = 0; j < list.get(i).getWishlist().size(); j++) {
				if( p.equals(list.get(i).getWishlist().get(j)) ){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns true if and only if product p is in the wish list for customer c.
	 * @param c Username of customer
	 * @param p Name of product
	 * @return True if customer with specified username has the product 
	 * with specified name in their wish list, otherwise returns false.
	 */
	public boolean hasProduct(String c, String p){
		if(!containsCustomer(c)) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if( c.equals(list.get(i).getUsername()) ){
				for (int j = 0; j < list.get(i).getWishlist().size(); j++) {
					if( p.equals(list.get(i).getWishlist().get(j)) ){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Return the list of customers who have product p in their wish list.
	 * @param p Name of product
	 * @return Null if product p is not in the database, otherwise returns
	 * the list of customers which have product p in their wish list.
	 */
	public List<String> getCustomers(String p){
		if( containsProduct(p)){
			List<String> custlist = new ArrayList<String>();
			for(int i = 0; i < list.size(); i++){
				if(hasProduct(list.get(i).getUsername(), p)){
					custlist.add(list.get(i).getUsername());
				}
			}
			return custlist;
		}
		return null;
	}
	
	/**
	 * Returns the wish list for the customer c.
	 * @param c Username of customer
	 * @return Null if customer c is not in the database, otherwise returns the
	 * wish list for specified customer .
	 */
	public List<String> getProducts(String c){
		if( containsCustomer(c)){
			for (int i = 0; i < this.list.size(); i++){
				if( c.equals(list.get(i).getUsername())){
					return list.get(i).getWishlist();
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns an Iterator over the Customer objects in the database.
	 * @return Iterator for the Customer database objects.
	 */
	public Iterator<Customer> iterator(){
		return list.iterator();
	}
	
	/**
	 * Removes customer c from the database.
	 * @param c Username of customer
	 * @return False if customer c is not in the database, otherwise if the 
	 * removal is successful, returns true.
	 */
	public boolean removeCustomer(String c){
		if(containsCustomer(c)){
			for (int i = 0; i < list.size(); i++) {
				if( c.equals(list.get(i).getUsername()) ){
					list.remove(i);
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes product p from every wish list in which it appears.
	 * @param p Name of product
	 * @return True if the removal is successful, otherwise returns false(if 
	 * the product is not in the database).
	 */
	public boolean removeProduct(String p){
		if(containsProduct(p)){
			for (int i = 0; i < this.list.size(); i++){
				for (int j = 0; j < list.get(i).getWishlist().size(); j++) {
					if( p.equals(list.get(i).getWishlist().get(j)) ){
						list.get(i).getWishlist().remove(j);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the number of customers in this database.
	 * @return The size of the customer database
	 */
	public int size(){
		return list.size();
	}
}
