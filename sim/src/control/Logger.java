package control;
import java.util.*;

import model.cargo.Driver;
import model.cargo.Trip;
import model.cargo.Truck;
import model.poem.Bridge;
import model.traffic.Entry;
import model.traffic.EventLog;
import model.poem.QueueSim;
/** 
 *  The Logger keeps all the logs required for the simulator to run, the logs are:
 *  <ul>
 *  <li> EventLog
 *  <li> The available trucks
 *  <li> The available Drivers
 *  <li> The positions of the running trucks
 *  <li> The queue status
 *  </ul>
 * @author Jorge Alexis Hernandez
 *@version April 2010
 */
public class Logger {
	
	EventLog log = new EventLog();
	EventLog availableTrucks;
    EventLog availableDrivers;
    EventLog positions;
    EventLog qStatus;
    /**
     * Creates an eventLog and initializes the available trucks and drivers
     */
	public Logger()
	{
		log = new EventLog();
    	availableTrucks = new EventLog();
    	availableDrivers = new EventLog();
	}
	/**
	 * 
	 * @return a copy of the EventLog as it is at the moment of calling the method
	 */
	public EventLog getLogCopy()
	{
		return log.getCopy();
	}
	/**
	 * gets the Trucks from the provided list and creates an Event Log with them, set the event log and then returns the EventLog
	 * @param notRunning a list of the Trucks not running
	 * @return a copy of the available trucks EventLog
	 */
	public EventLog getAvailableTrucksCopy(List notRunning)
    {
    	availableTrucks = new EventLog();
    	Iterator iter = notRunning.iterator(); 
    	while(iter.hasNext())
    	{
    		Truck tempt = (Truck)iter.next();
			availableTrucks.addEntry(new Entry(0,"ID: " + tempt.getID(),""));

    	}
        return availableTrucks.getCopy();
    }
	/**
	 * gets the Drivers from the provided list and creates an Event Log with them, set the event log and then returns the EventLog
	 * @param drivers a list of the drivers not running
	 * @return a copy of the available Drivers EventLog
	 */
	public EventLog getAvailableDriversCopy(List drivers)
    {
    	availableDrivers = new EventLog();
    	Iterator iter = drivers.iterator(); 
    	while(iter.hasNext())
    	{
    		Driver tempt = (Driver)iter.next();
			availableDrivers.addEntry(new Entry(0,"ID: " + tempt.getName(),""));

    	}
        return availableDrivers.getCopy();
    }  
	/**
	 * gets status queue in list form and creates an event log, sets it up as the current QueueStatus EventLog and returns it
	 * @param queue a list with the status of the Queue
	 * @return a copy of the QueueStatus log
	 */
	public EventLog getQueueStatusCopy(QueueSim queue)
    {
    	qStatus = new EventLog();
    	//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	//System.out.println("$$" + queue.toString());
    	for(int i = 0; i < queue.getNBridges(); i++)
    	{
    		Bridge b = queue.getNBridge(i);
    		Iterator iter = b.getIter();
    		//System.out.println("$$" + b.toString());
    		qStatus.addEntry(new Entry(0,"@Bridge: " + queue.getBridgeName(i),""));
    		while(iter.hasNext())
        	{
        		Trip tempt = (Trip)iter.next();//changed trip id
    			qStatus.addEntry(new Entry(0,"Trip ID: " + "0" + " Driver: " + tempt.getDriverName() + " VIN: " + tempt.getTruckvin(),""));

        	}
    		
    	}
    	//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	return qStatus.getCopy();
    }
	/**
	 * 
	 * @return a copy of the positions EventLog not a a mere reference
	 */
	public EventLog getPositionsCopy()
	{
		return positions.getCopy();
	}

}
