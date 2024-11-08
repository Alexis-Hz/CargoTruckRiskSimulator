package model.poem;

import java.util.*;
import model.cargo.*;

//
/**
 * This class is the simulator of bridges and lanes and holds the current status of the trucks within the brides lanes
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class QueueSim {

	int def_bridges;
	int def_lanes;
	private Bridge[] bridges;
	TQConnection tqc;
	int sid;
	/**
	 * Creates a new bridge with a specified session id and the following defaults:
	 * <ul>
	 * <li>3 Bridges
	 * <li>5 Lanes per Bridge
	 * </ul>	
	 * also initializes the bridges
	 * @param sidx
	 */
	public QueueSim(int sidx)
	{
		sid = sidx;
		def_bridges = 3;
		def_lanes = 5;
		init();
	}
	/**
	 * Sets the TQConnection to be used in the QueueSimulator
	 * @param con The available Trip Queue connection
	 */
	public void setTQC(TQConnection con)
	{
		tqc = con;		
	}
	public int getNBridges()
	{
		return def_bridges;
	}
	public int getNLanes()
	{
		return def_lanes;
	}
	/**
	 * Gets the nth bridge
	 * @param n the position of the bridge to fetch
	 * @return the bridge at the nth position
	 */
	public Bridge getNBridge(int n)
	{
		return bridges[n];
	}
	/**
	 * Gets the name of nth bridge
	 * @param i the position of the bridge to fetch
	 * @return the name of the bridge at the nth position
	 */
	public String getBridgeName(int i)
	{
		return bridges[i].name;
	}
	/**
	 * Initializes the bridges in the Queue simulator, initializes the default ammount of bridges 3 as follows:
	 * <ul>
	 * <li> Cordova
	 * <li> Santa Fe
	 * <li> Zaragosa
	 * </ul>
	 * All with the default number of lanes 5
	 */
	public void init()
	{
		bridges = new Bridge[def_bridges];
		bridges[0] = new Bridge("Cordova", def_lanes);
		bridges[1] = new Bridge("Santa Fe", def_lanes);
		bridges[2] = new Bridge("Zaragosa", def_lanes);
		updatePOETable();
		
		
		
	}
	/**
	 * resets all bridges, this method should be called on a simulator reset
	 */
	public void reset()
	{
		bridges = new Bridge[def_bridges];
		bridges[0] = new Bridge("Cordova", def_lanes);
		bridges[1] = new Bridge("Santa Fe", def_lanes);
		bridges[2] = new Bridge("Zaragosa", def_lanes);
		updatePOETable();
	}
	/**
	 * Empty method
	 */
	public void updatePOETable()
	{
		//update the poe table on the server
	}
	/**
	 * called when a new trip has finished it's route and has "arrived" at a port of entry
	 * @param o The arrived trips
	 * @param at the port of entry at wich the trip arrived
	 * @param timex the time of arrival for the trip
	 */
	public void arrival(Trip o, int at, int timex)
	{
		at--;
		bridges[at].insert(o);
		//tqc.insert(sid, (Trip)o, timex);
		System.out.println("fake adding: " + sid+ " : " + o.toString() + " @ " + timex);
		//if(bridges[at].numLanes == 1)//the one we just added is the only car in line
		//{
			bridges[at].headRecom();
		//}
		//System.out.println(">>after fake adding");
	}
	/**
	 * removes and returns the next trip at a bridge to be serviced
	 * @param from bridge number
	 * @return the Trip that was at the front of the queue at that bridge
	 */
	public Trip next(int from)
	{
		Trip tr = (Trip)bridges[from].remove(0);
		return tr;
	}
	/**
	 * Empty method
	 */
	public void update()
	{
		
	}
	/**
	 * @return A String digest of all bridges and the Trips in each of them
	 */
	public  String toString()
	{
		String tr = "Bridges: ";
		for(int i = 0; i < def_bridges; i++)
		{
			tr += bridges[i].name + ", ";
		}
		return tr;
	}
}
