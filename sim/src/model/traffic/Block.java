package model.traffic;
/**
 * This class represents a blockage, in a location, that creates detours and slows affected segments 
 * 
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Block {
	
	Coord at;
	int id;
	/**
	 * Simple constructor
	 * @param idx id for the block
	 * @param coordinate Coordinate of the location of the Block
	 */
	public Block(int idx, Coord coordinate)
	{
		id = idx;
		at = coordinate;
	}
	public int getID()
	{
		return id;
	}
	public Coord atCoord()
	{
		return at;
	}
	/**
	 * @return "Block [Id: " + id + " @ " + at.toString() + "]"
	 */
	public String toString()
	{
		return "Block [Id: " + id + " @ " + at.toString() + "]";
	}

}
