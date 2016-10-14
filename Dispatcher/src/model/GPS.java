package model;

public class GPS {

	private static final double d2r = Math.PI/180;
	private double latitude;
	private double longitude;

	public GPS(double lati, double longi){
		latitude = lati;
		longitude = longi;
	}

	public double getDistanceTo(GPS location){
		double toreturn;
		double dlong = (this.longitude - location.longitude) * d2r;
		double dlat = (this.latitude - location.latitude) * d2r;
		double a = Math.pow(Math.sin(dlat/2.0), 2) + 
				Math.cos(location.latitude*d2r) * Math.cos(this.latitude*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		toreturn = 3956 * c; 	
		return toreturn;
	}
	
        @Override
	public String toString(){
		return latitude + ", " + longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
