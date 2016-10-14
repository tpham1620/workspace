package model;
import java.util.ArrayList;



/**
 * @author Tan Pham
 *
 */
public final class Dispatcher {
	

	private final ArrayList<Driver> ondutyDriver;

	
	private final ArrayList<Job> unassignedJob;
	public ArrayList<Job> getUnassignedJob(){
		return unassignedJob;
	}
	
	

	
	public Dispatcher(){
		ondutyDriver = new ArrayList<>();
		unassignedJob = new ArrayList<>();
	}
	
	/**
	 * create new driver.
     * @param id
     * @param status
	 * @param pwd
	 * @param phone
     * @param gps
	 * @return
	 */
	public Driver createDriver(int id, Driver.Status status, String pwd, GPS gps, String phone){
		Driver driver = new Driver(id, status, pwd, gps, phone);
		return driver;
	}
	
	/**
	 * add new driver to a list
	 * if driver status is onduty, add to the onduty list.
	 * @param driver
	 */
	public void addDriver(Driver driver){
		ondutyDriver.add(driver);
	}
	
	/**
	 * remove a driver from a list
     * @param driverID
     * @return 
	 */
	public Driver removeDriver(int driverID){
		for(int i = 0; i < ondutyDriver.size(); i++){
			if(driverID == ondutyDriver.get(i).getDriverID()){
				return ondutyDriver.remove(i);
			}
		}
		return null;
	}
	
	
	/**
	 * create new job
     * @param id
	 * @param pick
	 * @param drop
     * @param status
	 * @return
	 */
	public Job createJob(int id, GPS pick, GPS drop, Job.Status status){
		Job job = new Job(id, pick, drop, status);
		return job;
	}
	
	/**
	 * add new job to a list.
	 * @param job
	 */
	public void addJob(Job job){
		unassignedJob.add(job);
	}
	
	
	/**
	 * remove a job from a list.
     * @param jobID
     * @return 
	 */
	public Job removeJob(int jobID){
		for(int i = 0; i < unassignedJob.size(); i++){
			if(jobID == unassignedJob.get(i).getJobID()){
				return unassignedJob.remove(i);
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
	public Job getJob(int jobID){
		for(int i = 0; i < unassignedJob.size(); i++){
			if(jobID == unassignedJob.get(i).getJobID()){
				return unassignedJob.get(i);
			}
		}
		return null;
	}
	
	public String unassignedJobToString(){
		String toreturn = "";
		for(int i = 0; i < unassignedJob.size(); i++){
			toreturn += "Job ID: " + unassignedJob.get(i).getJobID() 
					+ ", Pick Location: " + unassignedJob.get(i).getPickLocation().toString() + 
					", Drop Location: " + unassignedJob.get(i).getDropLocation().toString() + "\n\n";
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
	public  void assignJob(Job job, Driver driver){
		removeJob(job.getJobID());
		removeDriver(driver.getDriverID());
	}
	
	
	
	public void updateGPS(int driverID, GPS gps){
		for(int i = 0; i < ondutyDriver.size(); i++){
			if(ondutyDriver.get(i).getDriverID() == driverID){
				ondutyDriver.get(i).setCurrentLocation(gps);
			}
		}
	}
        
        
	/**
	 * @return the ondutyDriver
	 */
	public ArrayList<Driver> getOndutyDriver() {
		return ondutyDriver;
	}
}
