package model.cargo;

import model.traffic.*;
/**
 * This class is the superclass for all movable objects, be it trailers, trucks, and eventually airplanes or ships
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class MovableObject {

	//to add- 
	//movable object should contain the trip
	//should be the connection to traffic
	/**
	 * Should be overridden whenever this class is extended  
	 * @return The status of the object, moving or stationary
	 */
	public  boolean isMoving()//overwrite
	{
		return false;
	}
	/**
	 * Should be overridden whenever this class is extended  
	 * @return the position for the last known location of the object
	 */
	public Coord lastKnownLocation()//the top of the location array
	{
		return null;
	}
}
