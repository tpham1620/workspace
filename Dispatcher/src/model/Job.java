package model;


public class Job {

	public static enum Status {UNAS, TASK, INCR, COMP};
	private int jobID;
	private Status status;
	private GPS pickLocation;
	private GPS dropLocation;
	
	public Job(int id, GPS pick, GPS drop, Status status){
		jobID = id;
		pickLocation = pick;
		dropLocation = drop;
		this.status = status;
	}
	
	
	/**
	 * @return the jobID
	 */
	public int getJobID() {
		return jobID;
	}
	/**
	 * @param jobID the jobID to set
	 */
	public void setJobID(int jobID) {
		this.jobID = jobID;
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
	 * @return the pickLocation
	 */
	public GPS getPickLocation() {
		return pickLocation;
	}
	/**
	 * @param pickLocation the pickLocation to set
	 */
	public void setPickLocation(GPS pickLocation) {
		this.pickLocation = pickLocation;
	}
	/**
	 * @return the dropLocation
	 */
	public GPS getDropLocation() {
		return dropLocation;
	}
	/**
	 * @param dropLocation the dropLocation to set
	 */
	public void setDropLocation(GPS dropLocation) {
		this.dropLocation = dropLocation;
	}
	
}
