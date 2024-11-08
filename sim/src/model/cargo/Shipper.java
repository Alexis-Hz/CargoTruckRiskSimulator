package model.cargo;

import model.traffic.Coord;
/**
 * This class represents the shipper, in most cases the shipper will be a maquiladora, but in some it may be an actual warehouse or a deposit
 * @author Tempest
 *
 */
public class Shipper extends Location{
	Coord position;
	String name;
	/**
	 * 
	 * @param pos Coordinate position of the shipper
	 * @param namex name of the shipper
	 */
	public Shipper(Coord pos, String namex)
	{
		super(pos);
		name = namex;
	}
	public Coord getPosition()
	{
		return position;
	}
	public String getName()
	{
		return name;
	}
	/**
	 * @return "position: " + position + " name: " + name
	 */
	public String toString()
	{
		return "position: " + position + " name: " + name; 
	}
}
