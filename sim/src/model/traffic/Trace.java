package model.traffic;

import java.util.*;
/**
 * The trace class represents the actual route taken as polled at each interval in the form of a set Coordinates<p>
 * There is no remove, because the simulator cannot backtrack
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Trace {

	int mappedTo;
	List points;
	/**
	 * Creates a Trace and instantiates the Coordinates list
	 */
	public Trace()
	{
		points = new LinkedList();
	}
	/**
	 * Adds a point to the list of Coordinates
	 * @param p Coordinate to add
	 */
	public void addPoint(Coord p)
	{
		points.add(p);
	}
}
