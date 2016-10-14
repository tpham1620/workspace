package model;


public class Job {

	public static enum Status {UNASSIGNED, ASSIGNED, COMPLETE};
	private int jobID;
	private String jobName;
	private Status status;
	private GPS pickLocation;
	private GPS dropLocation;
	
	public Job(int id, String name, GPS pick, GPS drop){
		jobID = id;
		jobName = name;
		pickLocation = pick;
		dropLocation = drop;
		status = Status.UNASSIGNED;
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
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
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
