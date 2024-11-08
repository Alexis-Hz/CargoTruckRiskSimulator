package model.traffic;

/**
 * A class to represent coordinates 
 * 
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */

public class Coord implements Comparable {

	private double longitude;
	private double latitude;
	/**
	 * Simple class
	 * @param lon longitude
	 * @param lat latitude
	 */
	public Coord(double lon, double lat)
	{
		longitude = lon;
		latitude = lat;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setLongitude(double lon){
		longitude = lon;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public void setLatitude(double lat){
		latitude = lat;
	}
	/**
	 * Always returns 0, fix this
	 */
	public int compareTo(Object arg0) {
		
		return 0;
	}
	/**
	 * Compares the Coordinate with the object coordinate
	 * @param cx object
	 * @return true: if latitude and longitude on both are equal
	 */
	public boolean equals(Object cx)
	{
		Coord c = (Coord)cx;
		System.out.println("Check blocks: " + c.getLatitude() + "?=" + latitude + " && " + c.getLongitude() + "?=" + longitude);
		if((c.getLatitude() == latitude) && (c.getLongitude() == longitude))
			return true;
		else
			return false;
	}
	/**
	 * @return "("+longitude+","+latitude+")"
	 */
	public String toString()
	{
		return "("+longitude+","+latitude+")";
	}

}
