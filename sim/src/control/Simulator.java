package control;


import java.io.*;
import java.util.*;
//import jpl.Query;    // for prolog interface
//import jpl.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.cargo.Driver;
import model.cargo.Trip;
import model.cargo.Truck;
import model.traffic.Coord;
import model.traffic.Entry;
import model.traffic.Event;
import model.traffic.EventLog;
import model.traffic.Options;
import model.traffic.Route;
import model.traffic.Segment;
import model.poem.*;
import model.tds.*;
import model.cargo.*;
import model.traffic.*;
import edu.university.cs.iw.axis.CDBService5_jws.*;//importing the web client package

import java.awt.Image;
import java.awt.Toolkit;

import org.tempuri.ServiceSoapProxy;

import control.Settings;
/**
 * The heart and soul of the simulator, the main class used to instantiate and manage
 * a Simulator instance.
 * 
 * CreateEvents()- this method creates a list of events and puts them into a event queue so that whenever
 * 		a time constant passes by the queue is checked to see wheter there is an available event to be 
 * 		carried out
 * IsDone()- this method is used to see if the simulator is done running, based on the status of the queues
 * setAutomatic()- sets the simulator to run in automatic
 * getTime()- returns current simulator time
 * getLog()-  returns a copy of the current event log describing simulator events
 * getAvailableTrucks()- returns the available trucks in the form of an eventlog
 * getAvailableDrivers()-returns the available drivers in the form of an eventlog
 * getPositions()-returns the positions of the trips in the form of an eventlog
 * 
 * Simulator states:
 * <ul>
 * <li>0 = idle
 * <li>1 = stand by
 * <li>2 = running
 * <li>3 = paused
 * </ul>
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Simulator
{
    // global variable for options
	private Coord poe = new Coord(10, 10);
    private static Options simOpt = new Options();
    
    private List notRunning = new LinkedList(); // Trucks that haven't run yet
    private List onRoute = new LinkedList();    // Trucks that are onroute
    private List arrived = new LinkedList();    // Trucks that have arrived already
    //private List events = new LinkedList();     // Events that are going to happen
    //private static Query query; //= new Query("consult('cargoThreatDetection.pl')");
   
    private int time = 0;                       // current time
    
    
    
    
    private List trips = new LinkedList();
    private List blocks = new LinkedList();
    
    Image picture = null;
    ServiceSoapProxy proxy;
    
    PoolBoy poolboy;
    Logger logger;    
    EventHandler eventhandler;
    Responder responder;
    
    
    int sid;
    Connection con;
    QueueSim queue;
    TQConnection connection;
    
    public boolean test;   
    
    //for(int i = 0; i < 1000; i++)
	// 	System.out.println("Pool OK");
    final int IDLE = 0;
    final int STANDBY = 1;
    final int RUNNING = 2;
    final int PAUSED = 3;
    int state = IDLE;
    int per = 0;
    boolean running = false;
    int scenario = 0;
    String tier1 = "                                          ";
    /**
     * Instantiates a new Simulator with no options <p>
     * Initialize the simulator and preps the simulator for startup
     */ 
    public Simulator()
    {   
    		test = true;
	    	//System.out.println("~0~");
    		//SET SETTINGS FOR THE APPLICATION
    	    Settings.setMainSettings();
    	    System.out.println(Settings.settingsDigest());
	    	
	    	poolboy = new PoolBoy(test);
	    	System.out.println("~Poolboy~");
	    	time = 0;
	    	//pools are filled
	    	//start queues
	    	notRunning = new LinkedList(); // Trucks that haven't run yet
	        onRoute = new LinkedList();    // Trucks that are onRoute
	        arrived = new LinkedList();    // Trucks that have arrived already
	        
	        System.out.println("~SoapProxy~");
	    	
	    	proxy = new ServiceSoapProxy();
	    	System.out.println("~Logger~");
	    	logger = new Logger();
	    	System.out.println("~TQConnection~");
	    	//initialize Truck DB
	    	connection = new TQConnection();
	    	//test = true;
	    	System.out.println("~initConnection~");
	    	sid = connection.initConnection(test);
	    	System.out.println("~Queue~");
	    	//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%SID(Sim init): " + sid);
	    	queue = new QueueSim(sid);
	    	System.out.println("~SetTQC~");
	    	queue.setTQC(connection);
	    	System.out.println("~Responder~");
	    	state = STANDBY;
	    	responder = new Responder(this, onRoute);
	    	System.out.println(tier1+"~EventHandler~");
	    	
	    	eventhandler = new EventHandler(sid,test, responder, scenario);
	    	
	    	Iterator <Event>it = eventhandler.getEventIterator();
	    	//while(it.hasNext())
	    	//{
	    	//	System.out.println(tier1+it.next().toString());
	    	//}
	    	//System.out.println(tier1 + eventhandler.);
	    	
	    	System.out.println("~PoolStatus~");
	    	poolboy.printPoolStatus();
	    	
	    	System.out.println("{Done starting the Simulator}");
    }
    /**
     * resets the simulator and all the pools and necessary objects
     * @param s event scenario to use after reset
     */
    public void reset(int s)
    {
    	test = true;
    	
    	Settings.setMainSettings();
	    System.out.println(Settings.settingsDigest());
	    
    	//reset connection
    	connection = new TQConnection();
    	sid = connection.initConnection(test);
    	
    	//poolboy.reset();
    	poolboy = new PoolBoy(test);
    	System.out.println("~Poolboy~");
    	time = 0;
    	
    	//pools are filled
    	//start queues
    	notRunning = new LinkedList(); // Trucks that haven't run yet
        onRoute = new LinkedList();    // Trucks that are onRoute
        arrived = new LinkedList();    // Trucks that have arrived already
        
        System.out.println("~SoapProxy~");
    	
    	proxy = new ServiceSoapProxy();
    	System.out.println("~Logger~");
    	logger = new Logger();
    	System.out.println("~TQConnection~");
    	//initialize Truck DB
    	connection = new TQConnection();
    	//test = true;
    	System.out.println("~initConnection~");
    	sid = connection.initConnection(test);
    	System.out.println("~Queue~");
    	//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%SID(Sim init): " + sid);
    	queue = new QueueSim(sid);
    	System.out.println("~SetTQC~");
    	queue.setTQC(connection);
    	System.out.println("~Responder~");
    	state = STANDBY;
    	responder = new Responder(this, onRoute);
    	System.out.println(tier1+"~EventHandler~");
    	eventhandler = new EventHandler(sid,test, responder, scenario);
    	
    	Iterator <Event>it = eventhandler.getEventIterator();
    	//while(it.hasNext())
    	//{
    	//	System.out.println(tier1+it.next().toString());
    	//}
    	//System.out.println(tier1 + eventhandler.);
    	
    	System.out.println("~PoolStatus~");
    	poolboy.printPoolStatus();
    	
    	System.out.println("{Done starting the Simulator}");
    	
    	
    }
    
    
    /**
     * stop function from the front end
     * stops the simulator if running or paused
     */
    public void stop()
    {
    	if(state == IDLE)
    	{
    		state = IDLE;//if idle, stop does nothing
    	}
    	else if(state == STANDBY)
    	{
    		state = STANDBY;//if stand-by stop does nothing
    	}
    	else if(state == RUNNING)
    	{
    		reset(0);
    		state = STANDBY;//if running stop stand-by
    	}
    	else if(state == PAUSED)
    	{
    		reset(0);
    		state = STANDBY;// if paused stop stand-by
    	}
    }
    boolean inloop = false;
    /**
     * play function from the front end
     * starts or resumes the simulator when required
     */
    public void play()
    {
    	
    	if(state == IDLE)
    	{
    		state = STANDBY;// if idle play running
    	}
    	else if(state == STANDBY)
    	{
    		state = RUNNING;//if stand-by play running
    		if(!inloop)
    			loop(1000, 500); 
    	}
    	else if(state == RUNNING)
    	{
    		state = RUNNING;//if running play does nothing
    		if(!inloop)
    			loop(1000, 500);    		
    	}
    	else if(state == PAUSED)
    	{
    		state = RUNNING;//if paused play running
    		if(!inloop)
    			loop(1000, 500);    		
    	}
    		
    }
    /**
     * pause from the front end
     * pauses the simulator when required
     */
    public void pause()
    {
    	if(state == IDLE)
    	{
    		state = IDLE;// if idle pause nothing
    	}
    	else if(state == STANDBY)
    	{
    		state = STANDBY;//if stand-by paused nothing
    	}
    	else if(state == RUNNING)
    	{
    		state = PAUSED;//if running pause paused
    	}
    	else if(state == PAUSED)
    	{
    		state = PAUSED;//if paused pause nothing
    	}
    }
    /**
     * step function from the front end,
     * takes a step in time in the simulator
     */
    public void step()
    {
    	if(state == IDLE)
    	{
    		state = IDLE;// if idle pause nothing
    	}
    	else if(state == STANDBY)
    	{
    		run();
    		state = PAUSED;//if stand-by paused nothing
    	}
    	else if(state == RUNNING)
    	{
    		run();
    		state = PAUSED;//if running pause paused
    	}
    	else if(state == PAUSED)
    	{
    		run();
    		state = PAUSED;//if paused pause nothing
    	}
    }
    /**
     * moves the tmie forward and runs the simulator according to the specified units per tick
     */
    public void run()
    {
		//log.addEntry(new Entry(time,"minute",""));
    	//System.out.println("-->Time: " + time + " <--");
    	for(int i =0; i <per; i++)
    	{
    		sendTripRandom();
    	}
    	//poolboy.printPoolStatus();
		updateTrucks();
		//manage queues
    	queue.update();
    	//checkEvents();
    	time+=1;
    	eventhandler.setGlobalTime(time);		//keep track of simulator time in the event handler
    	System.out.println("finding events to execute");
    	eventhandler.findEventsToExecute();
    }
    /**
     * main loop for the simulator, loops for a specific time
     * @param timeout time out
     * @param iter number of iterations
     */
    public void loop(int timeout, int iter) 
    {
    	inloop = true;
    	long l = timeout;
    	int i = 0;
    	try
    	{
    		for(i = 0; i < iter; i++)
    		{
				if(state == RUNNING)
				{
	    			run();
	    			System.out.println();
				}
				Thread.sleep(l);
    			
    		}
    		state = PAUSED;
    	}
    	catch(InterruptedException e)
    	{
    		System.out.println("INTERRUPTED");
    		
    		state = IDLE;
    	}
    	inloop = false;
    	
    }
    //int rc = 0;
    /**
     * sends a trip at random
     */
    int tripCounter = 40;
    public void sendTripRandom()
    {
    	responder.sendTruck(tripCounter);
    	tripCounter++;
    	/*
    	//int ran = (int)(Math.random()*(poolboy.trips.size()));
    	//System.out.println(ran+"th trip choosen");
    	//poolboy.printPoolStatus();
    	Trip tr = (Trip)poolboy.trips.remove(0);
    	poolboy.trips.add(tr);
    	//System.out.println(tr.toString());
    	
    	//System.out.println("Tripin");
    	//eventhandler.sendTripOff(tr, poolboy, logger, onRoute, poe, time);
    	
    	//Route routet = poolboy.findRoute(new Coord(0,0), poe);//to change for a method that looks up routes in RL
    	int tidx = Integer.parseInt(tr.getTripId());
    	tidx--;
    	System.out.println("TripID: " + tidx);
    	Iterator iter = poolboy.segmentPool[tidx].iterator();
//    	rc++;
//    	if(rc <= poolboy.segmentPool.length)
//    		rc = 0;
//    		
    	//System.out.println("Segment gotten Size: "+ poolboy.segmentPool[tidx].size());
    	int c =0;
    	while(iter.hasNext())
    	{
    		c++;
    		iter.next();
    	}
    	//System.out.println("size: " + c);
    	Segment [] ro = new Segment[c];
    	iter = poolboy.segmentPool[tidx].iterator();
    	c =0;
    	while(iter.hasNext())
    	{
    		ro[c] = (Segment)iter.next();
    		c++;
    		
    	}
    	//System.out.println("Converted into Array");
    	Route routet = new Route(ro);
    	//System.out.println("Route Size: "+routet.getSize());
    	tr.setRoute(routet);//fake route set
    	onRoute.add(tr);//putting the trip onRoute
    	//System.out.println("Route Added");
    	tr.getTruck().start();
    	//System.out.println("Route Started");
		logger.log.addEntry(new Entry(time, "Truck(VIN): " + tr.getTruckvin(), "SENT"));
    	//System.out.println("Trip Succesfully Sent");
    	 */
    }
    /**
     * changes the amount of units time per simulator tick
     * @param p new steps per tick
     */
    public void changePer(int p)
    {
    	logger.log.addEntry(new Entry(time, "from: " + per + " to: " + p, "PER CHANGED"));
    	//System.out.println("PER CHANGED BY EVENT: " + p);
    	per = p;
    }
    public int getPer()
    {
    	return per;
    }
    /**
     * stops all trucks
     */
    public void stopAllTrucks()
    {
    	Iterator iter = onRoute.iterator();
    	System.out.println("Stopping all Trucks");
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		Truck temptt = tempt.getTruck();
    		temptt.stop();
    	}
    	
    }
    /** 
     * stops a particular trip
     * @param tripId
     */
    public void stopTruck(int tripId)
    {
    	Iterator iter = onRoute.iterator();
    	//System.out.println("Stopping all Trucks");
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		if(tripId == (Integer.parseInt(tempt.getTripId())))//Trip found
    		{
    			System.out.println("TRIP FOUND AND STOPPED");
    			Truck temptt = tempt.getTruck();
        		temptt.stop();
        		return;
    		}
    		
    	}
    	System.out.println("TRIP NOT FOUND");
    }
    /**
     * resumes movement in all trips
     */
    public void resumeAllTrucks()
    {
    	Iterator iter = onRoute.iterator();
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		Truck temptt = tempt.getTruck();
    		temptt.resume();
    	}
    }
    /**
     * resumes movement in a particular trip
     * @param tripId trip id
     */
    public void resumeTruck(int tripId)
    {
    	Iterator iter = onRoute.iterator();
    	System.out.println("Stopping all Trucks");
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		if(tripId == (Integer.parseInt(tempt.getTripId())))//Trip found
    		{
    			System.out.println("TRIP FOUND AND RESUMED");
    			Truck temptt = tempt.getTruck();
        		temptt.resume();
        		return;
    		}
    		
    	}
    	System.out.println("TRIP NOT FOUND");
    }
    /**
     * slows all trips
     */
    public void slowAllTrucks()
    {
    	Iterator iter = onRoute.iterator();
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		Truck temptt = tempt.getTruck();
    		temptt.slow();
    	}
    }
    /**
     * slows a particular truck
     * @param tripId trip id
     */
    public void slowTruck(int tripId)
    {
    	Iterator iter = onRoute.iterator();
    	System.out.println("Stopping all Trucks");
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		if(tripId == (Integer.parseInt(tempt.getTripId())))//Trip found
    		{
    			System.out.println("TRIP FOUND AND SLOWED");
    			Truck temptt = tempt.getTruck();
        		temptt.slow();
        		return;
    		}
    		
    	}
    	System.out.println("TRIP NOT FOUND");
    }
    /**
     * resumes normal speed for all slowed trucks
     */
    public void paceAllTrucks()
    {
    	Iterator iter = onRoute.iterator();
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		Truck temptt = tempt.getTruck();
    		temptt.pace();
    	}
    }
    /**
     * resumes movement speed for a particular slowed truck
     * @param tripId
     */
    public void paceTruck(int tripId)
    {
    	Iterator iter = onRoute.iterator();
    	//System.out.println("Stopping all Trucks");
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		if(tripId == (Integer.parseInt(tempt.getTripId())))//Trip found
    		{
    			System.out.println("TRIP FOUND AND PACED");
    			Truck temptt = tempt.getTruck();
        		temptt.pace();
        		return;
    		}
    		
    	}
    	System.out.println("TRIP NOT FOUND");
    }
    /**
     * returns all trips homes, rerouting them
     */
    public void returnAllHome()
    {
    	//reroute
    }
    /**
     * returns a particular trip home
     * @param tripId
     */
    public void returnHome(String tripId)
    {
    	//reroute
    }
    /**
     * adds a block
     * @param b block to add
     */
    public void addBlock(Block b)
    {
    	System.out.println(b.toString());
    	System.out.println("before " + blocks.size());
    	blocks.add(b);
    	System.out.println("after " + blocks.size());
    	System.out.println("EID: "+b.getID()+" Added @ " + b.atCoord().toString());
    }
    /**
     * removes a specific block
     * @param id id of the block
     */
    public void removeBlock(int id)
    {
    	Iterator iterB = blocks.iterator();
    	while(iterB.hasNext())
    	{
    		Block b = (Block)iterB.next();
    		if(b.getID() == id)
    		{
    			iterB.remove();
    			break;
    		}
    	}
    	System.out.println("EID: "+id+" Removed");
    }
    /**
     * blocks a specific trip
     * @param tid id of the trip to block
     */
    public void blockTrip(String tid)
    {
    	//stop this trip and create a 
    	int tripid =Integer.parseInt(tid);
    	stopTruck(tripid);
    	Trip t1 = null;
    	Iterator iter = onRoute.iterator();
    	while(iter.hasNext())
    	{
    		Trip tempt = (Trip)iter.next();
    		if(tripid == (Integer.parseInt(tempt.getTripId())))//Trip found
    		{
    			t1 = tempt;
    			System.out.println("TRIP FOUND");
        		break;
    		}
    		
    	}
    	if (t1 == null)
    	{
    		System.out.println("trip not found");
    		return;
    	}
    	Coord c1 = t1.getTruck().getSegment().getPointB();
    	Block b1 =  new Block(Integer.parseInt(tid), c1);
    	
    	addBlock(b1);
    	//create a block for all other trucks
    	
    	/*
    	Iterator iter = onRoute.iterator();
    	Truck truckt;
    	Trip tript;
    	
    	System.out.println("BLOCKING AND REROUTING TRIP");
    	while(iter.hasNext())
    	{
    		tript = (Trip)iter.next();
    		//System.out.println(tript.getTripId());
    		
    		if(tript.getTripId().equalsIgnoreCase(tid))
    		{
    			System.out.println("REROUTING SPECIFIC TRIP");
    			truckt = tript.getTruck();
    			//System.out.println(truckt.toString());
    			Coord nextPoint = truckt.getSegment().getPointB();
    			//System.out.println(nextPoint.toString());
    			reroute(truckt, nextPoint);
    			return;
    		}
    	}
    	System.out.println("TRIP " + tid+ " NOT FOUND");
    	*/
    }
    /**
     * updates all trucks moving them and handling events
     */
    public void updateTrucks()//update and move trucks in their routes
    {
    	logger.positions = new EventLog();
    	Iterator iter2 = onRoute.iterator();
    	Truck truckt;
    	Trip tript;
    	int i = 0;
    	while(iter2.hasNext())//each run is a truck on the onRoute queue
    	{
    		tript = (Trip)iter2.next();
    		truckt = tript.getTruck();
    		try
    		{
    			//System.out.println("HERE0!!!!");
    			if(truckt.getNextSegment() == null)//this is the last segment
    				System.out.println("LAST SEGMENT!!!!");
    			Coord nextPoint = truckt.getNextSegment().getPointB(); 
    			
    			if(isBlocked(nextPoint))
        		{
    				System.out.println(truckt.toString()+ " BLOCKED!!!");
        			logger.log.addEntry(new Entry(time,truckt.toString()+"BLOCKED" , "WAITING"));
        			//reroute(truckt, nextPoint);
        		}
        		else
        		{
        		
    	    		//System.out.println("Moving Trucks");
    	    		truckt.advanceSegment();
    	    		if(truckt.hasArrived())//a truck has arrived
    	    		{
    	    			arrived.add(truckt);
    	    			queue.arrival(tript, 1, time);//change the zero for the number of the actual poe
    	    			iter2.remove();
    	    			//truckt = (Truck)onRoute.remove(i);
    	    			logger.log.addEntry(new Entry(time,truckt.toString()+"ARRIVED" , ""));
    	    			
    	    		}
    	    		else//has not arrived yet
    	    		{
    	    			
    	    		}
        		}
    		}
    		catch(NullPointerException e)
    		{
    			
    			System.out.println("NULL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    			/*
    			Coord =  truckt.
    			if(isBlocked(nextPoint))
        		{
        			logger.log.addEntry(new Entry(time,truckt.toString()+"BLOCKED" , "REROUTED"));
        			reroute(truckt, nextPoint);
        		}
        		*/
    			//System.out.println("Moving Trucks");
	    		truckt.advanceSegment();
	    		if(truckt.hasArrived())//a truck has arrived
	    		{
	    			arrived.add(truckt);
	    			queue.arrival(tript, 1, time);//change the zero for the number of the actual poe 
	    			iter2.remove();
	    			//truckt = (Truck)onRoute.remove(i);
	    			//System.out.println("HERE7!!!");
	    			logger.log.addEntry(new Entry(time,truckt.toString()+"ARRIVED" , ""));
	    			//System.out.println("HERE8!!!");
	    			
	    		}
	    		else//has not arrived yet
	    		{
	    			
	    		}
    		}
    		
    		
    		
    		i++;
    		logger.positions.addEntry(new Entry(Integer.parseInt(tript.getTripId()), truckt.getID() + " " + 	truckt.getStat()+ " @", truckt.on().toString()+ " " + truckt.toSegment()));
    	}
    }
    /**
     * reroutes a particular trip
     * @param truck reroutes a truck
     * @param blockage blockage blocking the truck
     */
    public void reroute(Truck truck, Coord blockage)
    {
    	System.out.println("REROUTING");
    	Segment currentSegment = truck.getSegment();
    	Coord currentSegmentCoord = currentSegment.getPointA();
    	
    	System.out.println("Last Segment");
    	Segment lastSegment = truck.getLastSegment();
    	Coord lastSegmentCoord = lastSegment.getPointB();
    	
    	String start_x =currentSegmentCoord.getLongitude()+"";
    	String start_y =currentSegmentCoord.getLatitude()+"";
    	String end_x =lastSegmentCoord.getLongitude()+"";
    	String end_y =currentSegmentCoord.getLatitude()+"";
    	String block_x = blockage.getLatitude()+"";
    	String block_y= blockage.getLatitude()+"";
    	try
    	{
    		System.out.println("REROUTING "+ start_x+":"+start_y + " " + end_x+":"+end_y + " " + block_x+":"+block_y);
    		String alternateRoute = proxy.getAlternateShortestRoute(start_x, start_y, end_x, end_y, block_x, block_y);
    		//System.out.println(alternateRoute);
    		Segment [] diverted = segmentify(alternateRoute);
    		Route r = new Route(diverted);
    		System.out.println(r.toString());
    	}
    	catch(RemoteException e)
    	{
    		System.out.println("UNRESPONSIVE REROUTING FROM THE WEB SERVICE");
    	}
    }
    /**
     * takes the rorouted route ni string format and turns in into an array of segments
     * @param diverted rerouted  route
     * @return array of segments with the new route
     */
    public Segment [] segmentify(String diverted)
    {
    	String [] splitted = diverted.split("-");
    	Segment [] segs = new Segment[splitted.length-1];
    	String [] one;
    	String [] two;
    	Coord a;
    	Coord b;
    	for(int i =0; i < (segs.length); i++)
    	{
    		one = splitted[i].split(",");
    		two = one[1].split(" ");
    		
    		double a_x = Double.parseDouble(one[0]);
    		double a_y = Double.parseDouble(two[1]);
    		
    		one = splitted[i+1].split(",");
    		two = one[1].split(" ");
    		
    		double b_x = Double.parseDouble(one[0]);
    		double b_y = Double.parseDouble(two[1]);
    		
    		a = new Coord(a_x, a_y);
    		b = new Coord(b_x, b_y);
    		segs[i] = new Segment(a,b);
    	}
    	return segs;
    }
    /**
     * checks if a particular coordinate is blocked
     * @param c coordinate to check
     * @return tru if it is blocked<p>
     * false: otherwise
     */
    public boolean isBlocked(Coord c)
    {
    	//System.out.println("                                      IS IT BLOCKED");
    	try
    	{
    	Iterator iterB = blocks.iterator();
    	while(iterB.hasNext())
    	{
    		Block b = (Block)iterB.next();
    		//System.out.println("CHECKING>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + b.atCoord().toString() + "copmparing to " + c.toString());
    		if(b.atCoord().getLatitude() == c.getLatitude() && b.atCoord().getLongitude() == c.getLongitude())
    			return true;
    	}
    	return false;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace(System.out);
    		return false;
    	}
    }
    /**
     * @return a String representation of the list of blocks currently active in the system
     */
    public String blockListToString()
    {
    	String toRe = "Size" + blocks.size();
    	Iterator iterH = blocks.iterator();
    	while(iterH.hasNext())
    	{
    		Block b = (Block)iterH.next();
    		toRe += b.toString();
    		toRe += '\n';
    	}
    	toRe += "_";
    	return toRe;
    }
    /**
     * 
     * @return a Lst of all trucks currently in the system
     */
    public List getTruckList()
    {
        int temp = 0;
        LinkedList truckList = new LinkedList();
        
        for (int i = 0; i < notRunning.size(); i++)
        {
            truckList.add(notRunning.get(i));
        }
        
        for (int i = 0; i < onRoute.size(); i++)
        {
            truckList.add(onRoute.get(i));
        }
        
        for (int i = 0; i < arrived.size(); i++)
        {
            truckList.add(arrived.get(i));
        }
        
        return truckList;
    }
    public int getSid()
    {
    	return sid;
    }
    /** Return current simulator time
     * @return int current time of the simulator
     */
    public int getTime()
    {
        return time;
    }
    
    /** Returns a copy of the simulator log. 
     *  NOTE: this isn't the log the simulator is 
     *  using only a copy. If you run the simulator
     *  again and then query the log the 
     *  information will not be updated
     *  @return EventLog a copy of the log
     */
    public EventLog getLog()
    {
        return logger.getLogCopy();
    }
    public EventLog getAvailableTrucks()
    {
        return logger.getAvailableTrucksCopy(notRunning);
    }
    public EventLog getAvailableDrivers()
    {
        return logger.getAvailableDriversCopy(poolboy.driverPool);
    }  
    public EventLog getQueueStatus()
    {
    	return logger.getQueueStatusCopy(queue);
    }
    public EventLog getPositions()
    {
    	return logger.getPositionsCopy();
    }
    public LinkedList getSegmentPool()
    {
    	return (LinkedList)poolboy.segmentPool[0];
    }
        
    public int getNumBridges()
    {
    	return queue.getNBridges();
    }
    public int getNumLanes()
    {
    	return queue.getNLanes();
    }
    /**
     * this should be used whenwanting a quick way to  get the output of the simulator including state and contents
     * @return All the output from the simulator in a single String
     */
    public String output()
    {
    	String out  = "";
    	if(state == IDLE)
    		out += "Idle";
    	else if(state == STANDBY)
    		out += "Standby";
    	else if(state == RUNNING)
    		out += "Running";
    	else if(state == PAUSED)
    		out += "Paused";
    	out +="|" + sid + "|" + time + "|" + '\n';
    	//out += "----Simulator output----" + '\n';
    	out += getLog().toString() + '\n';
    	//out += "------------------------" + '\n' + "|";
    	out+="|";
    	//out += "------Trips Queued------" + '\n';
    	out += getQueueStatus().toString() + '\n';
    	//out += "------------------------" + '\n' + "|";
    	out+="|";
    	//out += "------Trips On Route------" + '\n';
    	
    	try
		{
			EventLog log4 = getPositions();
			out += log4.toString() + '\n';
		}
		catch(NullPointerException e)
		{
			
		}
    	
    	//out += "------------------------" + '\n';
		out += "|";
		
		try
		{
			Iterator iter = eventhandler.events.iterator();
			while(iter.hasNext())
			{
				Event next = (Event)iter.next();
				out += next.toString() + '\n';
			}
		}
		catch(NullPointerException e)
		{
			out += "Null Event List";
		}
		
    	return out;
    }
    /**
     * @return A succint version of the status of the simulator
     */
    public String digest()
    {
    	String tr = "";
    	Iterator truckIter = trips.iterator();
    	Trip t;
    	Truck truck;
    	Segment seg;
    	Coord c;
    	while(truckIter.hasNext())
    	{
    		t = (Trip)truckIter.next();
    		tr = "";
    		truck = t.getTruck();
    		seg = truck.on();
    		c = seg.getPointA();
    		tr += c.getLatitude()+"|"+c.getLongitude() + "|TID: " + t.getTripId() + "|VIN: " + t.getTruckvin() + "|Maquila: " + t.getMaquila();
    		tr += '\n';
    	}
    	return tr;
    }
    
}



