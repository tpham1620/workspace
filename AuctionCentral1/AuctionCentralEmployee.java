package AuctionCentral;

/**
 * 
 */

import java.util.Calendar;

/**
 * @author Indiana
 * Sets up the Auction Central Employee view for the Auction Central Project.
 */
public class AuctionCentralEmployee {
	
	/**
	 * Views the calendar of upcomming auctions.
	 */
	void viewCurrentCalendar() {
		createCalendar(Calendar.MONTH, Calendar.YEAR);
	}
	
	/**
	 * Views auction details.
	 */
	void viewCalendar(int month, int year) {
		createCalendar(month, year);
	}
	
	void viewAuction(String name) {
		System.out.println("Information for " + getOrg(name) + ": ");
		System.out.println("Start time: " + getStartTime(name));
		System.out.println("End time: " + getEndTime(name));
		System.out.println("Date time: " + getDate(name));
		System.out.println("List of items: " + retrieveItemList(name));
	}
}
