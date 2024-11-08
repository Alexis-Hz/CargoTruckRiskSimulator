package control;

import java.util.*;

import model.cargo.Trip;
import control.PoolBoy;
import model.traffic.Entry;
import model.traffic.Route;
import model.traffic.Segment;
import model.traffic.EventLog;
import model.traffic.Coord;
import model.traffic.Block;
/**
 * The responder is the way the system abstracts actions within the system, 
 * instead of having all actions have their own methods the responder has abstract simple 
 * actions that together may construct more complicated actions. 
 * Using the responder we minimize duplicated code and maximize reuse of code
 * <p>
 * This class is the utility class supporting the simulator<p>
 * There are some actions that have been either disabled or there is no code for it, this part of the system is still in progress
 * @author Jorge Alexs Hernandez
 * @version April 2010
 */
public class Responder {
	
	//This class responds contains the actions that the system
	PoolBoy poolboy;
	LinkedList onRoute;
	Logger logger;
	Simulator sim;
	/**
	 * The only constructor for the responder, it holds references for the simulator and most of the data the simulator has access to
	 * @param simx simulator
	 * @param or  list of trucks on route
	 */
	public Responder(Simulator simx, List or)
	{
		sim = simx;
		logger = sim.logger;
		onRoute = (LinkedList)or;
		poolboy = sim.poolboy;
	}
	/**
	 * Blocks a particular trip. code removed, uncomment and fix errors
	 * @param tid trip id
	 */
	public void blockTrip(String tid)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Blocked trip", "Trip Id: " + tid));
		sim.blockTrip(tid);
	}
	/** 
	 * creates a block that blocks the path of every route on its way, code removed, uncomment and fix errors
	 * @param id block id
	 * @param c location Coordinate of the blockage
	 */ 
	public void createBlock(int id, Coord c)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Block Created ", "Block ID: "+ id +" at: " + c.toString()));
		Block b = new Block(id, c);
		sim.addBlock(b);
		System.out.println("block created");
	}
	/**
	 * deletes or removes a particular block by id
	 * @param id block id
	 */
	public void deleteBlock(int id)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Blockage Removed", "Block ID: " + id));
		sim.resumeTruck(id);
		sim.removeBlock(id);
	}
	/**
	 * creates a delay at a point that affects all  trips passing thorough that coordinate
	 * @param coord coordinate of the delay
	 * @param deltaTime time that the delay will stay put
	 */
	public void createGlobalDelay(Coord coord, int deltaTime)
	{
		//this should create a delay at a specific point that affects all trips
	}
	/**
	 * stops a truck with  a particular trip id
	 * @param tripId trip id
	 */
	public void stopTruck(int tripId)
	{	
		logger.log.addEntry(new Entry(sim.getTime(), "Trip Stopped", "Trip Id: " + tripId));
		//stop the trip with that trip Id
		sim.stopTruck(tripId);
	}
	/**
	 * resumes a stopped trip by its trip id
	 * @param tripId trip id
	 */
	public void resumeTruck(int tripId)
	{	
		logger.log.addEntry(new Entry(sim.getTime(), "Trip Resumed", "Trip Id: " + tripId));
		//stop the trip with that trip Id
		sim.resumeTruck(tripId);
	}
	/** 
	 * resumes the standard speed of a slowed trip by id
	 * @param tripId trip id
	 */ 
	public void paceTruck(int tripId)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Trip Pace Resumed", "Trip Id: " + tripId));
		sim.paceTruck(tripId);
	}
	/**
	 * stops all trips currently running
	 */
	public void stopAll()
	{
		logger.log.addEntry(new Entry(sim.getTime(), "All Trips Stopped", "Global Stop"));
		sim.stopAllTrucks();
	}
	/**
	 * resumes movement in all stopped trips
	 */
	public void resumeAll()
	{
		logger.log.addEntry(new Entry(sim.getTime(), "All Trips Resumed Movement", "All Trips"));
		sim.resumeAllTrucks();
	}
	/** 
	 * finds a trip in the trip pool and starts it on its route,  according tothe given id
	 * @param tripId trip id
	 */
	public void sendTruck(int tripId)
	{		
		System.out.println("                                                     SEND TRUCK "+tripId);
		Iterator it = poolboy.trips.iterator();

		Trip tr = null; //(Trip)poolboy.trips.remove(tripId);
		while(it.hasNext())
		{
			tr = (Trip)it.next();
			int trid = Integer.parseInt(tr.getTripId());
			System.out.println("search: "+ trid);
			if(trid == tripId)
				break;//found
			else
				tr = null;
		}
		if(tr == null)
		{
			System.out.println("NOPE");
			return;
		}
    	poolboy.trips.add(tr);
    	System.out.println(tr.toString());
    	//eventhandler.sendTripOff(tr, poolboy, logger, onRoute, poe, time);
    	System.out.println(tr.getPRID());
    	//Route routet = poolboy.findRoute(new Coord(0,0), poe);//to change for a method that looks up routes in RL
    	Iterator iter = poolboy.segmentPool[tr.getPRID()].iterator();
    	int c =0;
    	while(iter.hasNext())
    	{
    		c++;
    		iter.next();
    	}
    	Segment [] ro = new Segment[c];
    	iter = poolboy.segmentPool[0].iterator();
    	System.out.println();
    	c =0;
    	iter = poolboy.segmentPool[tr.getPRID()].iterator();
    	while(iter.hasNext())
    	{
    		ro[c] = (Segment)iter.next();
    		c++;
    		
    	}
    	Route routet = new Route(ro);
    	//System.out.println(routet.toString());
    	tr.setRoute(routet);//fake route set
    	onRoute.add(tr);//putting the trip onRoute
    	System.out.println("Route Added");
    	tr.getTruck().start();
    	System.out.println("Route Started");
		logger.log.addEntry(new Entry(sim.getTime(), "Truck Sent", "Truck: " + tr.getTruckvin() + "=Driver: " + tr.getDriverName()));
    	System.out.println("Trip Succesfully Sent");
	}
	/**
	 * sets a deploy event, that sends a random trip every n time units
	 * @param per interval between deployments
	 */
	public void setDeploy(int per)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Deploy Rate per Minute Changed", "Rate Change" + per));
		int p = sim.getPer();
		p += per;
		sim.changePer(p);
	}
	/**
	 * routes a random trip and sends it
	 */
	public void sendAnyTruck()
	{
		Trip tr = (Trip)poolboy.trips.remove(0);
    	poolboy.trips.add(tr);
    	System.out.println(tr.toString());
    	
    	System.out.println("Tripin");
    	//eventhandler.sendTripOff(tr, poolboy, logger, onRoute, poe, time);
    	
    	//Route routet = poolboy.findRoute(new Coord(0,0), poe);//to change for a method that looks up routes in RL
    	Iterator iter = poolboy.segmentPool[0].iterator();
    	int c =0;
    	while(iter.hasNext())
    	{
    		c++;
    		iter.next();
    	}
    	Segment [] ro = new Segment[c];
    	iter = poolboy.segmentPool[0].iterator();
    	c =0;
    	while(iter.hasNext())
    	{
    		ro[c] = (Segment)iter.next();
    		c++;
    		
    	}
    	Route routet = new Route(ro);
    	System.out.println(routet.toString());
    	tr.setRoute(routet);//fake route set
    	onRoute.add(tr);//putting the trip onRoute
    	System.out.println("Route Added");
    	tr.getTruck().start();
    	System.out.println("Route Started");
		logger.log.addEntry(new Entry(sim.getTime(), "Random Truck Sent", "Truck: " + tr.getTruckvin() + "=Driver: " + tr.getDriverName()));
    	System.out.println("Trip Succesfully Sent");
	}
	/**
	 * slows  all trips
	 */
	public void slowAll()
	{
		logger.log.addEntry(new Entry(sim.getTime(), "All Trips Slowed", "Global Slow"));
		sim.slowAllTrucks();
	}
	/**
	 * resumes the normal speed of  all trips that are slowed
	 */
	public void paceAll()
	{
		logger.log.addEntry(new Entry(sim.getTime(), "All Trips Pace Resumed", "Global Pace Resume"));
		sim.paceAllTrucks();
	}
	/**
	 * slows a particular trip by trip id
	 * @param trip trip id
	 */
	public void slowTrip(String trip)
	{
		logger.log.addEntry(new Entry(sim.getTime(), "Trip Slowed", "Trip Id: " + trip));
		sim.slowTruck(Integer.parseInt(trip));
	}
	/**
	 * reroutes a particular trip to go home, by trip id
	 * @param trip trip id
	 */
	public void rerouteHome(String trip)
	{
		sim.returnHome(trip);
	}
	/**
	 * returns all trips home
	 */
	public void rerouteAllHome()
	{
		sim.returnAllHome();
	}
}