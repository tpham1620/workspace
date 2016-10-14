package model;
import java.util.ArrayList;


public class Driver {

	public static enum Status {ON_DUTY, OFF_DUTY, HAS_PASSENGER}
	private int driverID;
	private String password;
	private String driverName;
	private String phoneNumber;
	private Status status;
	private GPS currentLocation;
	private Job currentJob;
	private ArrayList<Job> jobAccepted;
	private ArrayList<Job> jobRejected;
	
	public Driver(int id, String pwd, String name, String phone){
		driverID = id;
		password = pwd;
		driverName = name;
		phoneNumber = phone;
		status = Status.OFF_DUTY;
		jobAccepted = new ArrayList<>();
		jobRejected = new ArrayList<>();
		currentJob = null;
		
	}
	
	
	
	/**
	 * @return the driverID
	 */
	public int getDriverID() {
		return driverID;
	}
	/**
	 * @param driverID the driverID to set
	 */
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the currentJob
	 */
	public Job getCurrentJob() {
		return currentJob;
	}
	/**
	 * @param currentJob the currentJob to set
	 */
	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
	}
	/**
	 * @return the jobAccepted
	 */
	public ArrayList<Job> getJobAccepted() {
		return jobAccepted;
	}
	/**
	 * @param jobAccepted the jobAccepted to set
	 */
	public void setJobAccepted(ArrayList<Job> jobAccepted) {
		this.jobAccepted = jobAccepted;
	}
	/**
	 * @return the jobRejected
	 */
	public ArrayList<Job> getJobRejected() {
		return jobRejected;
	}
	/**
	 * @param jobRejected the jobRejected to set
	 */
	public void setJobRejected(ArrayList<Job> jobRejected) {
		this.jobRejected = jobRejected;
	}

	/**
	 * @return the phoneNumer
	 */
	public String getPhoneNumer() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumer the phoneNumer to set
	 */
	public void setPhoneNumer(String phoneNumer) {
		this.phoneNumber = phoneNumer;
	}



	/**
	 * @return the currentLocation
	 */
	public GPS getCurrentLocation() {
		return currentLocation;
	}



	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(GPS currentLocation) {
		this.currentLocation = currentLocation;
	}
	
}
