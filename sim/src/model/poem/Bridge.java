package model.poem;

import java.util.*;
import model.tds.*;
import model.cargo.*;
/**
 * This class represents a particular bridge, in an abstract way, it creates a bridge in a sense. <p>
 * A bridge is a port of entry, in future versions it should extend port of entry and generalizations should be coded for this purpose<p>
 * In this implementation this class is used instead of the general port of entry for purposes of simplicity.<p>
 * The class Bridge has a queue of trips, in which arrived trucks are held, it also keeps TDS recommendation data for trip at the top of this queue.
 * It also has a number of lines, so that more than one officer may be working in a particular bridge at one time
 * @author Jorge Alexis Hernandez
 * @version April 2011
 */
public class Bridge {

	int numLanes;
	private LinkedList queue;
	int inLine;
	
	String headLight;
	String uri;
	TdsCall tds;
	String name;
	int time = 0;
	boolean mock = true;
	//comment
	/**
	 * basic constructor, creates a bridge with no trips in line and no recommendation data, also instantiates the TdsCall object to be used to acquire recommendation data
	 * @param poeId the port of entry id for this particular bridge
	 * @param lanesx the number of lanes this bridge will have
	 */
	public Bridge(String poeId, int lanesx)
	{
		name = poeId;
		numLanes = lanesx;
		queue = new LinkedList();
		inLine = 0;
		headLight = "nolight";
		if(!mock)
			tds = new TdsCall();
	}
	/**
	 * sets the current time of the bridge to a integer given
	 * @param t time, since the emulator uses a discrete real number representation of time any positive integer is valid
	 */
	public void setTime(int t)
	{
		time = t;
	}
	/**
	 * gets a String representation of the time at the bridge
	 * @return time + ":00" (String representation)
	 */
	public String getTime()
	{
		return time + ":00";
	}
	/**
	 * gets an Iterator for the queue
	 * @return Iterator object for the queue LinkedList
	 */
	public Iterator getIter()
	{
		return queue.iterator();
	}
	/**
	 * inserts a trip object at the end of the queue, no explicit type checking
	 * @param o the Trip object to insert at the end of the queue
	 */
	public void insert(Object o)
	{
		queue.addLast(o);
		inLine++;
	}
	/**
	 * removes a trip from the front of the queue
	 * @param at legacy parameter, sould be used if the Bridge has more that 1 queue
	 * @return The Trip object that used to be at the front of the queue
	 */
	public Object remove(int at)
	{
		inLine--;
		//do the recommendation f
		return queue.getFirst(); 
	}
	/**
	 * requests a recommendation using a tdsCall for the trip at the front of the queue<p>
	 * gets the light for the head (red, green, yellow)<p>
	 * gets the uri for a justification for the light
	 */
	public void headRecom()
	{
		if(mock)
		{
			Trip t = (Trip)queue.getFirst();			
			headLight = "yellow";
			uri = "generic.uri";
		}
		else 
		{
			System.out.println("FUCK!!!!");
			Trip t = (Trip)queue.getFirst();
			
			headLight = tds.getLight(t.getTruckvin(), "route1", "route2", getTime(), getTime());
			uri = tds.getUri(t.getTruckvin(), "route1", "route2", getTime(), getTime());
			
			System.out.println("$$$$$$$$" + headLight + "$$$$$$$$");
			System.out.println("$$$$$$$$" + uri + "$$$$$$$$");
		}
	}
	/**
	 * @return a String containing a pre-formatted digest of the state of the bridge including Trips in line and bridge data 
	 */
	public String toString()
	{
		String tr = "Name: " + name + ", Lanes: " + numLanes;
		tr += '\n';
		
		Iterator iter = queue.iterator();
		while(iter.hasNext())
		{
			Trip t =  (Trip)iter.next();
			tr += t.toString();
			tr += '\n';
		}
		
		return tr;
	}
}
