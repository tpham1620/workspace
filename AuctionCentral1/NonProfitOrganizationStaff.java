package AuctionCentral;

/**
 * 
 */


import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Indiana
 * Sets up the Non-Profit view of the Auction Central Project.
 */
public class NonProfitOrganizationStaff {

	/**
	 * Holds the current acution for this non-profit.
	 */
	private Auction currentAuction;
	
	/**
	 * Adds a new auction to the calendar for this non profit organization.
	 */
	void addNewAuction() {
		Scanner theScn = null;
		System.out.println("Enter the auction month: ");
		int month = theScn.nextInt();
		while(month < 1 || month > 12) {
			System.out.println("Error. Could not set date. Enter the auction month: ");
			month = theScn.nextInt();
		}
		currentAuction.mon = month;
		System.out.println("Enter the auction day: ");
		int day = theScn.nextInt();
		while(day < 1 || day > 30) {
			System.out.println("Error. Could not set date. Enter the auction day: ");
			day = theScn.nextInt();
		}
		currentAuction.d = day;
		System.out.println("Enter the auction year: ");
		int year = theScn.nextInt();
		while(year < Calendar.YEAR) {
			System.out.println("Error. Could not set date. Enter the auction year: ");
			year = theScn.nextInt();
		}
		currentAuction.y = year;
		System.out.println("Enter the auction hour: ");
		int hour = theScn.nextInt();
		while(hour < 0 || hour > 24) {
			System.out.println("Error. Could not set date. Enter the auction hour: ");
			hour = theScn.nextInt();
		}
		currentAuction.h = hour;
		System.out.println("Enter the auction minute: ");
		int minute = theScn.nextInt();
		while(minute < 0 || minute > 60) {
			System.out.println("Error. Could not set date. Enter the auction minute: ");
			minute = theScn.nextInt();
		}
		currentAuction.min = minute;
	}
	
	/**
	 * Edits a currently existing auction.
	 */
	
	void editAuction() {
		System.out.println("Please enter the key of what you want to edit "
				+ "(mon for month, d for day, y for year, h for hour, min for minute, and e to exit): ");
		Scanner theScn = null;
		String change = theScn.next();
		while(change != "e") {
			if(change == "mon") {
				System.out.println("Enter the auction month: ");
				int month = theScn.nextInt();
				while(month < 1 || month > 12) {
					System.out.println("Error. Could not set date. Enter the auction month: ");
					month = theScn.nextInt();
				}
				currentAuction.mon = month;
			} else if(change == "d") {
				System.out.println("Enter the auction day: ");
				int day = theScn.nextInt();
				while(day < 1 || day > 30) {
					System.out.println("Error. Could not set date. Enter the auction day: ");
					day = theScn.nextInt();
				}
				currentAuction.d = day;
			} else if(change == "y") {
				System.out.println("Enter the auction year: ");
				int year = theScn.nextInt();
				while(year < Calendar.YEAR) {
					System.out.println("Error. Could not set date. Enter the auction year: ");
					year = theScn.nextInt();
				}
				currentAuction.y = year;
			} else if(change == "h") {
				System.out.println("Enter the auction hour: ");
				int hour = theScn.nextInt();
				while(hour < 0 || hour > 24) {
					System.out.println("Error. Could not set date. Enter the auction hour: ");
					hour = theScn.nextInt();
				}
				currentAuction.h = hour;
			} else if(change == "min") {
				System.out.println("Enter the auction minute: ");
				int minute = theScn.nextInt();
				while(minute < 0 || minute > 60) {
					System.out.println("Error. Could not set date. Enter the auction minute: ");
					minute = theScn.nextInt();
				}
				currentAuction.min = minute;
			} else {
				System.out.println("Please enter the key of what you want to edit "
						+ "(mon for month, d for day, y for year, h for hour, min for minute, and e to exit): ");
				change = theScn.next();
			}
		}
	}
	
	/**
	 * Adds an item to the current available auction for this non profit organization.
	 */
	void addNewItem() {
		Item salesItems = new Item();
		Scanner theScn = null;
		System.out.println("Please enter the item name: ");
		String name = theScn.nextLine();
		salesItem.n = name;
		System.out.println("Please enter the item description: ");
		String description = theScn.nextLine();
		salesItem.d = description;
		System.out.print("Please enter the minimum bid for this item: ");
		int minimumBid = theScn.nextInt();
		salesItem.min = minimumBid;
	}
	
	/**
	 * Edits a currently existing item.
	 */
	void editItem() {
		System.out.println("Please enter the key of what you want to edit "
				+ "(n for name, d for description, b for minimum bid, and e to exit): ");
		Scanner theScn = null;
		String change = theScn.next();
		while(change != "e") {
			if(change == "n"){
				System.out.println("Please enter the item name: ");
				String name = theScn.nextLine();
				salesItem.n = name;
			} else if(change == "d") {
				System.out.println("Please enter the item description: ");
				String description = theScn.nextLine();
				salesItem.d = description;
			} else if(change == "b") {
				System.out.print("Please enter the minimum bid for this item: ");
				int minimumBid = theScn.nextInt();
				salesItem.min = minimumBid;
			} else {
				System.out.println("Please enter the key of what you want to edit "
						+ "(n for name, d for description, b for minimum bid, and e to exit): ");
				change = theScn.next();
			}
		}
	}
}