package model;
import java.util.ArrayList;



/**
 * @author Tan Pham
 *
 */
public final class Dispatcher {
	
	private ArrayList<Driver> offDriver;
	private ArrayList<Driver> ondutyDriver;
	private final ArrayList<Driver> hasPassenger;
	public ArrayList<Driver> getHasPassenger(){
		return hasPassenger;
	}
	private final ArrayList<Job> unassignedJob;
	public ArrayList<Job> getUnassignedJob(){
		return unassignedJob;
	}
	private final ArrayList<Job> assignedJob;
	public ArrayList<Job> getAssignedJob(){
		return assignedJob;
	}
	private final ArrayList<Job> completedJob;
	public ArrayList<Job> getCompletedJob(){
		return completedJob;
	}
	

	
	public Dispatcher(){
		setOffDriver(new ArrayList<>());
		setOndutyDriver(new ArrayList<>());
		hasPassenger = new ArrayList<>();
		unassignedJob = new ArrayList<>();
		assignedJob = new ArrayList<>();
		completedJob = new ArrayList<>();
	}
	
	/**
	 * create new driver.
     * @param id
	 * @param pwd
	 * @param name
	 * @param phone
	 * @return
	 */
	public Driver createDriver(int id, String pwd, String name, String phone){
		Driver driver = new Driver(id, pwd, name, phone);
		return driver;
	}
	
	/**
	 * add new driver to a list
	 * if driver status is onduty, add to the onduty list.
	 * @param driver
     * @param list
	 */
	public void addDriver(Driver driver, ArrayList<Driver> list){
		list.add(driver);
	}
	
	/**
	 * remove a driver from a list
     * @param driverID
     * @param list
     * @return 
	 */
	public Driver removeDriver(int driverID, ArrayList<Driver> list){
		for(int i = 0; i < list.size(); i++){
			if(driverID == list.get(i).getDriverID()){
				return list.remove(i);
			}
		}
		return null;
	}
	
	
	/**
	 * create new job
     * @param id
	 * @param name
	 * @param pick
	 * @param drop
	 * @return
	 */
	public Job createJob(int id, String name, GPS pick, GPS drop){
		Job job = new Job(id, name, pick, drop);
		return job;
	}
	
	/**
	 * add new job to a list.
	 * @param job
	 * @param list
	 */
	public void addJob(Job job, ArrayList<Job> list){
		list.add(job);
	}
	
	
	/**
	 * remove a job from a list.
     * @param jobID
	 * @param list
     * @return 
	 */
	public Job removeJob(int jobID, ArrayList<Job> list){
		for(int i = 0; i < list.size(); i++){
			if(jobID == list.get(i).getJobID()){
				return list.remove(i);
			}
		}
		return null;
	}
	
	
	/**
	 * get the job with ID from list
	 * @param jobID
	 * @param list
	 * @return
	 */
	public Job getJob(int jobID, ArrayList<Job> list){
		for(int i = 0; i < list.size(); i++){
			if(jobID == list.get(i).getJobID()){
				return list.get(i);
			}
		}
		return null;
	}
	
	public String unassignedJobToString(){
		String toreturn = "";
		for(int i = 0; i < unassignedJob.size(); i++){
			toreturn += "Job ID: " + unassignedJob.get(i).getJobID() + ", Job Name: " + unassignedJob.get(i).getJobName() 
					+ ", Pick Location: " + unassignedJob.get(i).getPickLocation().toString() + ", Drop Location: " + unassignedJob.get(i).getDropLocation().toString() + "\n\n";
		}
		
		return toreturn;
	}
	
	
	/**
	 * get the closet available driver from the onduty list.
	 * @param job
	 * @return
	 */
	public Driver findClosestDriver(Job job){
		Driver driver = ondutyDriver.get(0);
		for(int i = 1; i < ondutyDriver.size(); i ++){
			if(driver.getCurrentLocation().getDistanceTo(job.getPickLocation()) > 
					ondutyDriver.get(i).getCurrentLocation().getDistanceTo(job.getPickLocation())){
				driver = ondutyDriver.get(i);
			}
		}
		
		return driver;
	}
	
	
	/**
	 * assign job to the driver
	 * return true if job is assigned.
	 * @param job
	 * @param driver
	 * @return
	 */
	public boolean assignJob(Job job, Driver driver){
		boolean assigned = false;
		driver.setCurrentJob(job);
		updateDriverStatus(driver, Driver.Status.HAS_PASSENGER);
		//driver.setStatus(Driver.Status.HAS_PASSENGER);	
		updateJobStatus(job, Job.Status.ASSIGNED);	
		//job.setStatus(Job.Status.ASSIGNED);
		assigned = true;
		return assigned;
	}
	
	/**
	 * update the status of driver and move to the appropriate list of driver. 
	 * @param driver
	 * @param newStatus
	 */
	public void updateDriverStatus(Driver driver, Driver.Status newStatus){
		ArrayList<Driver> currtList = null;
		ArrayList<Driver> newList = null;
		if(null != driver.getStatus())switch (driver.getStatus()) {
                case HAS_PASSENGER:
                    currtList = hasPassenger;
                    break;
                case OFF_DUTY:
                    currtList = offDriver;
                    break;
                default:
                    currtList = ondutyDriver;
                    break;
            }
		if(null != newStatus)switch (newStatus) {
                case HAS_PASSENGER:
                    newList = hasPassenger;
                    break;
                case OFF_DUTY:
                    newList = offDriver;
                    break;
                default:
                    newList = ondutyDriver;
                    break;
            }
		for(int i = 0;i < currtList.size(); i++){
			if(currtList.get(i).getDriverID() == driver.getDriverID()){
				currtList.get(i).setStatus(newStatus);
				addDriver(removeDriver(driver.getDriverID(), currtList), newList);
			}
		}
	}
	
	
	/**
	 * update the job status and move to the appropriate list of job.
	 * @param job
	 * @param newStatus
	 */
	public void updateJobStatus(Job job, Job.Status newStatus){
		ArrayList<Job> currtList;
		ArrayList<Job> newList = null;
		if(job.getStatus() == Job.Status.ASSIGNED){
			currtList = assignedJob;
		}else{
			currtList = unassignedJob;
		}
		if(null != newStatus)switch (newStatus) {
                case ASSIGNED:
                    newList = assignedJob;
                    break;
                case UNASSIGNED:
                    newList = unassignedJob;
                    break;
                default:
                    newList = completedJob;
                    break;
            }
		for(int i = 0; i < currtList.size(); i++){
			if(currtList.get(i).getJobID() == job.getJobID()){
				currtList.get(i).setStatus(newStatus);
				addJob(removeJob(job.getJobID(), currtList), newList);
			}
		}
	}
	
	
	public void updateGPS(int driverID, GPS gps){
		for(int i = 0; i < ondutyDriver.size(); i++){
			if(ondutyDriver.get(i).getDriverID() == driverID){
				ondutyDriver.get(i).setCurrentLocation(gps);
			}
		}
	}

	/**
	 * @return the offDriver
	 */
	public ArrayList<Driver> getOffDriver() {
		return offDriver;
	}

	/**
	 * @param offDriver the offDriver to set
	 */
	public void setOffDriver(ArrayList<Driver> offDriver) {
		this.offDriver = offDriver;
	}

	/**
	 * @return the ondutyDriver
	 */
	public ArrayList<Driver> getOndutyDriver() {
		return ondutyDriver;
	}

	/**
	 * @param ondutyDriver the ondutyDriver to set
	 */
	public void setOndutyDriver(ArrayList<Driver> ondutyDriver) {
		this.ondutyDriver = ondutyDriver;
	}
}
