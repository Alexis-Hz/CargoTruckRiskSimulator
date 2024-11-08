package control;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.university.cs.iw.axis.CDBService5_jws.CDBService5Proxy;
import javax.xml.rpc.ServiceException;


import model.cargo.Trip;
import model.traffic.Coord;
import model.traffic.Route;
import model.traffic.Segment;
import model.cargo.*;

/**
 * The Poolboy manages all the pools in the simulator. <p>
 * <b>Pool Workings</b> <p>
 * When the simulator is initialized drivers, trucks, routes, shippers and receivers are downloaded from the cargo database.<p>
 * This data is all put into appropriate pools or Holding Lists<p>
 * Wnenever the simulator requires a specific truck or driver or anything else, it looks it up in the pool instead of 
 * Querying the database. This was implemented to keep the Queries to  databases to a minimum since connecting is costly,
 * Pools are kept for this purpose, to expedite fetching objects once the Simulator is running.<p>
 * This however makes starting the simulator slow, but speeds up the simulator in general
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class PoolBoy {
	
	List driverPool = new LinkedList();
    List truckPool = new LinkedList();
    List [] segmentPool = {};
    List rouniversityool = new LinkedList();
    List shipperPool = new LinkedList();
    List receiverPool = new LinkedList();
    
	List trips = new LinkedList(); 
    
    boolean test;
    CargoConnection dbc;
    boolean mock = true;
    /**
     * The constructor initialized the Poolboy class creates a CArgoConnection initializes the connection and fills both the 
     * TripPool and Segment pool
     * @param testx tells the Constructor if the Poolboy should run in test mode
     */
	public PoolBoy(boolean testx)
	{
		test = testx;
		System.out.println("Initializing Cargo Conection");
		dbc = new CargoConnection();
		dbc.initConnection();
		System.out.println("Testing Cargo Connection");
		dbc.test();
		System.out.println("Starting To fill Trip Pool");
		fillTripPool();
		fillSegmentPool();
	}
	/**
	 * fills the trip pool with the data from the cargo database
	 */
	public void fillTripPool()
    {
    	System.out.println("Filling Trip Pool from DB");
    	trips = new LinkedList();
    	String driver_name;
    	String maquila_id;
    	String maquila;
    	String truck_vin;
    	String poe_id;
    	String bridge;
    	String [] yarp;
    	
    	trips = dbc.populate();
    	
    }
	/**
	 * Picks a trip from the database by its position in the trip pool and returns it
	 * @param ith the number in the trip pool
	 * @return the trip at the ith place
	 */
	public Trip pickTrip(int ith)
	{
		Iterator iter = trips.iterator();
		int i = 0;
		while(iter.hasNext())
		{	
			if(i == ith)
			{
				return (Trip)iter.next();
			}
			else
			{
				iter.next();
				i++;
			}
		}
		return null;
	}
	/**
	 * resets the pools, to empty
	 */
	public void reset()
	{
		driverPool = new LinkedList();
	    truckPool = new LinkedList();
	    rouniversityool = new LinkedList();
	    shipperPool = new LinkedList();
	    receiverPool = new LinkedList();
	}
	/**
	 * Finds a particular route by starting and ending Coordinates
	 * @param start starting coordinate
	 * @param end ending coordinate
	 * @return the Route Found null if not found
	 */
	public Route findRoute(Coord start, Coord end)
    {
		for(int h =0; h < segmentPool.length;h++)
		{
	    	Iterator iter0 = segmentPool[h].iterator();
	    	Segment[] array1 =  new Segment[segmentPool[h].size()];
	    	int i = 0;
	    	Segment st = (Segment)iter0.next();
	    	i++;
	    	array1[0] = st;
	    	Coord s = st.getPointA();
	    	if((s.getLatitude() == start.getLatitude()) && (s.getLongitude() == start.getLongitude()))
	    	{
	    		
	    		while(iter0.hasNext())
		    	{
		    		Segment tempS = (Segment)iter0.next();
		    		array1[i] = tempS;
		    		i++;
		    	}
	    		Coord ctemp = array1[i].getPointB();
	    		if((ctemp.getLatitude() == end.getLatitude()) && (ctemp.getLongitude() == end.getLongitude()))
	    		{
		    	Route rou1 = new Route(array1);
	    		}
	    	}
		}
    	return null;
    }
	/**
	 * Fills the segment pool from the cargo database
	 */
	public void fillSegmentPool()
    {
		//fill segments from real deal
		//368989.5414, 3492348.7959
		//355680.6128, 3516685.6238
		//JuarWebServiceSoapProxy proxy = new JuarWebServiceSoapProxy();
		
		String route;
		
		List routes = dbc.getRoutes();
		segmentPool =  new LinkedList[routes.size()];
		for(int c=0; (c <routes.size());c++)
		{
			segmentPool[c] = new LinkedList();
//			try
//			{
//				System.out.println("Trying to get Route!!!");
//				route = proxy.getRoute("370370.5334", "3497869.6521", "374147.2688", "3502157.8691");
//				//System.out.println(route);
//					
//					
//			}
//			catch(java.rmi.RemoteException e)
//			{
//				System.out.println("Didnt get Route!!!");
//				//e.printStackTrace();
//				
//				route = "370370.5334, 3497869.6521 370544.7949, 3498050.9385 370526.7015, 3498121.1908 370507.6395, 3498158.442 370488.6711, 3498187.693 370464.7654, 3498215.6939 370716.354, 3498469.7025 370828.4455, 3498585.4564 370964.724, 3498723.4611 371020.8166, 3498780.213 371186.7194, 3498940.2184 371227.4061, 3498981.4698 371268.249, 3499029.7215 371297.4047, 3499069.9728 371305.717, 3499083.9733 371334.7477, 3499132.9749 371361.1222, 3499191.4769 371372.2157, 3499224.728 371384.3405, 3499263.2293 371423.7148, 3499444.7353 371433.8083, 3499485.2367 371442.8706, 3499521.4879 371449.8393, 3499543.2386 371458.4641, 3499568.7395 371467.5889, 3499588.7402 371483.4324, 3499620.4912 371511.4943, 3499668.9928 371523.0878, 3499684.7434 371549.181, 3499717.9945 371562.6183, 3499729.2449 371532.7127, 3499768.2462 371560.4621, 3499790.9969 371651.7415, 3499863.7494 371874.5494, 3500041.2554 371935.2356, 3500089.7571 371967.9225, 3500115.008 371969.5162, 3500116.258 371977.8285, 3500122.7582 372011.0153, 3500149.0091 372114.0757, 3500231.0119 372126.9817, 3500243.5123 372132.8566, 3500252.0126 372148.8875, 3500275.2634 372157.7936, 3500300.7642 372173.7308, 3500359.0162 372199.4803, 3500471.2699 372224.3861, 3500568.0232 372227.7298, 3500582.0236 372253.6668, 3500691.7773 372280.0101, 3500806.0311 372308.1346, 3500913.0347 372305.4471, 3500925.5351 372295.4474, 3500949.2859 372261.4481, 3501033.7886 372239.8237, 3501176.5434 372228.4177, 3501251.7958 372213.3244, 3501347.299 372210.8244, 3501369.0497 372208.9807, 3501390.8004 372209.0432, 3501411.8011 372209.0433, 3501432.5518 372209.6058, 3501448.8024 372213.6682, 3501473.3032 372219.7931, 3501502.5542 372226.7304, 3501527.805 372264.3547, 3501613.8079 372305.5414, 3501712.0612 372327.666, 3501765.5629 372334.4471, 3501781.5635 372335.1971, 3501783.3135 372355.5717, 3501821.3148 372375.79, 3501859.0661 372437.1638, 3501975.32 372452.4447, 3501995.8207 372524.6933, 3502058.5728 372561.63, 3502094.074 372582.4733, 3502130.8252 372602.0042, 3502174.3267 372607.9728, 3502192.0773 372584.2233, 3502203.3276 372592.7232, 3502239.5788 372596.7544, 3502258.5795 372611.8166, 3502319.3315 372639.1598, 3502413.5846 372650.3784, 3502454.086 372666.3781, 3502503.5876 372672.5654, 3502520.8382 372861.4364, 3502468.8366 372930.0287, 3502448.836 372975.9027, 3502435.3356 372992.5899, 3502436.5856 373005.4333, 3502429.5854 373055.8385, 3502416.835 373199.398, 3502383.584 373236.9909, 3502374.8338 373950.632, 3502209.5787 374125.2533, 3502165.5774 374147.2688, 3502157.8691";
//			}
			
	    	Segment seg;
	    	
	    	route = (String)routes.get(c);
	    	String [] st = route.split(" ");
	    	
	    	if(true)
	    	{
		    	System.out.println();
		    	Coord c1 = null;
		    	Coord c2 = null;
		    	for(int i =0; i < st.length -3; i += 2)
		    	{
		    		//System.out.println(st[i]);
		    		String[] t = st[i].split(",");
		    		c1 = new Coord(Double.parseDouble(t[0]), Double.parseDouble(t[1]));
		    		String[] t2 = st[i+1].split(",");
		    		c2 = new Coord(Double.parseDouble(t2[0]), Double.parseDouble(t2[1]));
		    		if(i == 0)
		    			System.out.println(c1.toString() + c2.toString());
		    		//long timeout =  1000;
		    		
		    		seg = new Segment(1,10,c1,c2);
		        	seg.setName("segment"+i);
		        	segmentPool[c].add(seg);
		    	}
		    	System.out.println(c1.toString() + c2.toString());
		    	System.out.println("Length:" + st.length);
	    	}
	    	else 
	    	{
		    	
		    	System.out.println();
		    	Coord c1 = null;
		    	Coord c2 = null;
		    	for(int i =0; i < st.length -3; i += 2)
		    	{
		    		//System.out.println(st[i]);
		    		String[] t = st[i].split(",");
		    		c1 = new Coord(Double.parseDouble(t[0]), Double.parseDouble(st[i+1]));
		    		String[] t2 = st[i+2].split(",");
		    		c2 = new Coord(Double.parseDouble(t2[0]), Double.parseDouble(st[i+3]));
		    		if(i == 0)
		    			System.out.println(c1.toString() + c2.toString());
		    		//long timeout =  1000;
		    		
		    		seg = new Segment(1,10,c1,c2);
		        	seg.setName("segment"+i);
		        	segmentPool[c].add(seg);
		    	}
		    	System.out.println(c1.toString() + c2.toString());
		    	System.out.println("Length:" + st.length);
	    	}
		}
    }
	/** 
	 * prints the status of all pools
	 */
	public void printPoolStatus()
    {
    	 //driverPool, truckPool, segmentPool, shipperPool, receiverPool
    	if(true)
    	{
    		System.out.println("@@Pool Status Report@@start");
    		
    		System.out.println("tripPool");
    		System.out.println("size:" + trips.size());
    		
    		Iterator iter = trips.iterator();
    		while(iter.hasNext())
	    	{
	    		Trip tempS = (Trip)iter.next();
	    		System.out.println(tempS.toString());
	    	}
    	
    		System.out.println("@@Pool Status Report@@end");
    		System.out.println("@@Segment Pool Status Report@@");
    		System.out.println("size:" + segmentPool.length);
    		
    		for(int i =0; i < segmentPool.length; i++)
    		{
    			System.out.println(i+"th Route Size:" + segmentPool[i].size());
    		}
    	}
    }

}
