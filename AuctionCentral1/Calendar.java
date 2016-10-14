/**
 * 
 */
package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Jason Hall
 */
public class Calendar {

	// Class Constants
	/** A constant to tell how many auctions are allowed to come in the future.*/
	private static final int myMaxAuctions = 25;
	/** A constant to tell how many auctions are allowed per a 7 day period.*/
	private static final int myMaxPer7Days = 5;
	/** A constant to tell how many auctions are allowed per day.*/
	private static final int myMaxPerDay = 2;
	/** 
	 * A constant to tell how many hours there must be between two different
	 * auctions in one day.
	 */
	private static final int myMinHoursApart = 2;
	// Class variables
	/** 
	 * A map to hold all the auctions by date in the format "mm/yyyy" as the key
	 * and an ArrayList for all the the different auctions happening in that
	 * date.
	 */
	//private Map<String, ArrayList<Auction>> myAuctionsByDate;
	/** A list that holds all the current auctions that are comming up.*/
	private ArrayList<Auction> myCurrentAuctions;
	
	/** A printstream for a shortcut to print out to the console.*/
	private PrintStream myOut;
	/** 
	 * A scanner that is used to scan in different files and read from 
	 * the console.
	 * */
	private Scanner myIn;
	/** An array to hold all of the different months.*/
	private String[] month = new String[12];

	public Calendar() {
		FillMonth();
	}

	/**
	 * This method fills an Array with all the different months in the year. 
	 */
	private void FillMonth() {
		int i = 0;
		try {
			myIn = new Scanner(new File("Months.txt"));
			while (myIn.hasNext()) {
				month[i] = myIn.nextLine();
				i++;
			}
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("No months found");
		}
	}

	/**
	 * @return the myCurrentAuctions
	 */
	public ArrayList<Auction> getMyCurrentAuctions() {
		return myCurrentAuctions;
	}

	/**
	 * @param myCurrentAuctions the myCurrentAuctions to set
	 */
	public void setMyCurrentAuctions(ArrayList<Auction> myCurrentAuctions) {
		this.myCurrentAuctions = myCurrentAuctions;
	}
}