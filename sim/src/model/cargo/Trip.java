package model.cargo;

import java.util.*;

import model.traffic.Route;
import java.util.Random;
import model.traffic.*;

/**
 * This class is a trip and consists of just gets and sets for a trip
 *   
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Trip {
	
	String tripId;
	
	String maquila_id;
	String maquila;
	String poe_id;
	String bridge;
	String driver_id;
	String driver_name;
	String planned_routeid;
	
	Truck truck;
	
	CoordStr from;
	CoordStr to;
	
	Trace trace;
	/**
	 * This is the complete Trip constructor, this constructor is preferred in most cases, but the shorter one is used in some instances
	 * @param trip_idx the trip id
	 * @param maquila_idx the maquila id
	 * @param maquila_namex the maquila's name
	 * @param poe_idx the id for the por of entry
	 * @param bridgex the bridge in use for this trip
	 * @param vehicle_owner_idx the id of the vehicle owner
	 * @param vehicle_owner_namex the name of the vehicle owner
	 * @param vinx the vin for the truck
	 * @param planned_route_idx planned route from the set of planned routes
	 * @param f coordinate of departure "from"
	 * @param t coordinate of destination "to"
	 */
	public Trip(String trip_idx,String maquila_idx,String maquila_namex,String poe_idx,String bridgex, String vehicle_owner_idx,String vehicle_owner_namex, String vinx, String planned_route_idx, CoordStr f, CoordStr t)
	{
		tripId = trip_idx;
		maquila_id = maquila_idx;
		maquila = maquila_namex;
		poe_id = poe_idx;
		bridge = bridgex;
		driver_id = vehicle_owner_idx;
		driver_name = vehicle_owner_namex;
		planned_routeid = planned_route_idx;
		
		to = t;
		from = f;
		
		truck =  new Truck(vinx);
		trace = new Trace();
	}
	/**
	 *  see previous constructor for the descriptions of the parameters. 
	 *  
	 *  this is a abbreviated version of the previous constructor, to be used when you are missing some data, 
	 *  using the previous one is advised if most of the required data is available
	 * @param trip_idx
	 * @param maquila_idx
	 * @param maquila_namex
	 * @param poe_idx
	 * @param bridgex
	 * @param vehicle_owner_idx
	 * @param vinx
	 * @param planned_route_idx
	 */
	public Trip(String trip_idx,String maquila_idx,String maquila_namex,String poe_idx,String bridgex, String vehicle_owner_idx, String vinx, String planned_route_idx)
	{
		tripId = trip_idx;
		maquila_id = maquila_idx;
		maquila = maquila_namex;
		poe_id = poe_idx;
		bridge = bridgex;
		driver_id = vehicle_owner_idx;
		driver_name = "VehicleOwnerX";
		planned_routeid = planned_route_idx;
		
		to = new CoordStr("A","B");
		from = new CoordStr("A","B");
		
		truck =  new Truck(vinx);
		trace = new Trace();
	}
	/** 
	 * adds a point to the trace of the trip, this method should be called whenever the truck moves, 
	 * and will be the same as a the planned route unless an event creates a reroute on the trip and the truck has to take an alternate route
	 * @param p
	 */
	public void addPoint(Coord p)
	{
		trace.addPoint(p);
	}
	public String getDriverName()
	{
		return truck.getDriver().getName();
	}
	public String getMaquilaID()
	{
		return maquila_id;
	}
	public String getMaquila()
	{
		return maquila;
	}
	public String getTruckvin()
	{
		return truck.getID();
	}
	public String getPoeId()
	{
		return poe_id;
	}
	public String getBridge()
	{
		return bridge;
	}
	public Truck getTruck()
	{
		return truck;
	}
	public void setRoute(Route routex)
	{
		truck.setRoute(routex);
	}
	public String getTripId()
	{
		return tripId;
	}
	/**
	 * 
	 * @return the coordinate of the departure point of this particular trip-- String Coordinate
	 */
	public CoordStr getSource()
	{
		return from;
	}
	/**
	 * 
	 * @return the coordinate for the destination of this particular trip--String coordinate
	 */
	public CoordStr getDestination()
	{
		return to;
	}
	/**
	 * 
	 * @return Planned Route ID
	 */
	public int getPRID()
	{
		return Integer.parseInt(planned_routeid);
	}
	/**
	 * @return a String containing relevant data of this TRip
	 */
	public String toString()
	{
		return "Trip ID: " + tripId + " |Maquila ID: " + maquila_id + " |Maquila: " + maquila + " |POE ID: " + poe_id + " |Bridge: " + bridge + " |Driver: " + driver_name + " | Driver ID: " + driver_id + " |VIN: " + truck.getID() +" |Planned Route ID: " + planned_routeid + " |Source: " + from.toString() + " |Destination: " + to.toString();
	}
	/**
	 * 
	 * @return a String containing a resumed version of relevant data of this Trip
	 */
	public String info()
	{		
		return truck.getID() + "<" + driver_name + "-" + driver_id + ">" + maquila;
	}
}
