package model.cargo;

import model.traffic.Coord;
import model.*;
/**
 * This class represents a port of entry, trips arrival points are ports of entry unless they are returned mid flight
 * While referring to ports of entry what it's meant are bridges, in the case of this particular application, 
 * the bridges joining Cd Juarez and El PAso Texas. a port of entry is not reduced to this purpose. a port of entry 
 * can be any port of entry be it, a customs handling air imports on planes, or ships at a harbor
 * 
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class PortOfEntry extends Location
{
	private String name;
	/**
	 * 
	 * @param pos Coordinate position of the Port Of Entry
	 * @param namex name of the port of entry
	 */
	public PortOfEntry(Coord pos, String namex)
	{
		super(pos);
		name = namex;
	}
	public Coord getCoord()
	{
		return loc;
	}
	public String getName()
	{
		return name;
	}
}
