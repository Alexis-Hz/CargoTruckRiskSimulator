package model.traffic;
/**
 * A String Version of the Coordinate, it is used mainly in all calls that do not require any coordinate math and instead are printed somewhere.
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class CoordStr implements Comparable {

	private String longitude;
	private String latitude;
	public CoordStr(String lon, String lat)
	{
		longitude = lon;
		latitude = lat;
	}
	public String getLon()
	{
		return longitude;
	}
	public String getLat()
	{
		return latitude;
	}
	/**
	 * returns 0 alway, change it, or use the other Coordinate implementation
	 */
	public int compareTo(Object arg0) {
		
		return 0;
	}
	/**
	 * @return "("+latitude+","+longitude+")"
	 */
	public String toString()
	{
		return "("+latitude+","+longitude+")";
	}

}
