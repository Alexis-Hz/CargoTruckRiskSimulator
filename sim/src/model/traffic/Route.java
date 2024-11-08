package model.traffic;
/**
 * The route class represents an expected route, it is basically an array of segments and methods to support it as a Route
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Route {
	public Segment[] array;
	/**
	 * Builds a route with an array of segments
	 * @param arrayx array of segments conforming the route
	 */
	public Route(Segment[] arrayx)
	{
		array = arrayx;
	}
	public Segment getSegment(int index)
	{
		return array[index];
	}
	public int getSize()
	{
		return array.length;
	}
	/**
	 * @return A String representation of the  route printing all the beginning Coordinates for the segments in the route
	 */
	public String toString()
	{
		String toRe = "lenght" + array.length +"\n";
		for(int c = 0; c<array.length; c++)
		{
			toRe += array[c].toString() + "\n";
		}
		return toRe;
	}
}
