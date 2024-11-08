package model.cargo;
/**
 * This is the Trailer Class, a trailer is the actual box affixed to a truck, 
 * the trailer contains the cargo and may have it's own vin number.
 * vin might be added in the future to reflect this
 * @author Jorge Alexis Hernandez
 * @version April 2010 
 */
public class Trailer extends MovableObject {

	Truck myTruck;
	boolean att;
	/**
	 * creates an unattached trailer
	 */
	public Trailer()
	{
		att = false;
	}
	/**
	 * @return true: if the trailer is moving with a truck
	 * false: otherwise
	 */
	public boolean isMoving()
	{
		return myTruck.isMoving();
	}
	/**
	 * 
	 * @return true: if the trailer is attached to a truck
	 * false: otherwise
	 */
	public boolean isAttached()
	{
		return att;
	}
	/**
	 * attaches this trailer to a truck
	 * @param t truck to attach the trailer to
	 */
	public void attach(Truck t)
	{
		myTruck = t;
		att = true;
	}
}
