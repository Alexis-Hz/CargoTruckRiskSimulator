package model.cargo;

import model.traffic.Coord;
import model.traffic.Route;
import model.traffic.Segment;
import model.traffic.*;

/**
 * This class represents a truck, being the front part of a trailer and not the trailer itself.
 * The trailer is represented in the Trailer class
 * A truck used to be able to be moved disregarding the rest of the trip information
 * but now trips are considered, so there are a couple of legacy methods from the previous implementation
 * 
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Truck extends MovableObject
{
	
    String ID = "";
    Driver driver;
    Route route;
    /** The current segment in the route */
    public Segment current;
    /** Segment position in the segment array */
    public int segPos;
    int startTime;
    int arrivalTime;
    int currentSegment;
    int numSegments;
    int status; // status of the truck
    boolean arrived;
    
    boolean test = false;
    boolean running = true;
    boolean slowed = false;
    
    /**
     * Default constructor, the other one is advised 
     * @param truckId the Id of the truck
     */
    public Truck(String truckId)
    {
    	ID = truckId;
    	this.driver = new Driver();
    }
    /**
     * This constructor is usually used whenever there are some unknown data in the truck, 
     * which will be later filled in. In cases where you have the data the next one is advised
     * @param ID truck ID
     * @param routex the route the truck is in
     */
    public Truck(String ID, Route routex)
    {
        this.ID = ID;
        this.driver = new Driver();
        this.route = routex;
        int segPos = 0;
        int status = 0;
        numSegments = routex.getSize();
        arrived = false;
    }
    /**
     * This is the most complete of the constructor, this is the constructor advised in most of the 
     * circumstances.
     * @param ID truck id
     * @param driver driver reference for this truck
     * @param routex route the truck is on
     * @param startTime time at which the truck starts moving
     * @param numSegments number of segments in the route
     */
    public Truck(String ID, Driver driverx, Route routex, int startTime, int numSegments)
    {
        this.ID = ID;
        this.route = routex;
        this.startTime = startTime;
        this.numSegments = numSegments;
        //this.driver = new Driver();
        this.driver = driverx;
        Coord decoy1 = new Coord(1337, 1337);
        current = route.array[0];
        int segPos = 0;
    }
    
    /**
     * This method checks if the truck is moving
     * @return true: if it is moving
     * false: otherwise
     */
    public boolean isMoving()
    {
    	if(status == 1)
    		return true;
    	else
    		return false;
    }
    /**
     * sets the current segment
     * @param current segment to be set as the current one
     */
    public void setCurrent(Segment current)
    {
        this.current = current;
    }
    /**
     * sets the current segment to the first one in the route and 
     * sets the Truck moving
     * if testing: prints the route
     */
    public void start()
    {
    	if(test)
    		System.out.println(this.toString() + " START!");
        currentSegment = 0;
        current = route.array[0];
        segPos = 0;
        if(test)
        	System.out.println(route.toString());
    }
    /**
     * checks if the truck is on the last segment of the associated route
     * @return true: if the current segment is the last segment
     * false: otherwise
     */
    public boolean onLastSegment()
    {
        return (currentSegment >= (route.getSize() - 1));
    }
    boolean toogle = false;
    /**
     * Calling this method advances the truck to the next segment, 
     * it's the abstraction of movement. It moves the truck to the following segment
     * and signals arrival if the truck was on the last segment and just arrived
     * if stoped no movement, if slowed movement every two calls
     */
    public void advanceSegment()
    {
    	if(running)
		{
    		if(slowed && toogle)
    		{
    			toogle = false;
    		}
    		else
    		{
		    	segPos += current.currentSpeed;
		    	if(test)
		    		System.out.println(this.toString() +" @ Pos: " + segPos +"/" + current.getLength()+ " in Seg[" + current.getName()+ 
		    				"]: "	+ currentSegment +"/" +route.getSize());
		    	//if(segPos >= current.getLength())//finished current segment
		    	//{
	    		
	    		if (currentSegment < route.getSize())//the truck was not at the last segment
	            {
	                currentSegment++;
	                segPos = 0;
	                if(test)
	                	System.out.println("Advanced!");
	                if(currentSegment >= route.getSize() - 1)//size -1 since the array numeral is one less starts at 0
	                {
	                	if(test)
	                		System.out.println("Arrived!");
	        			arrived = true;
	                }
	                else
	                {
	                	System.out.println("current:" +currentSegment + " from: "+ route.getSize());
	                	current = route.getSegment(currentSegment);
	                	
	                }
	            }
	    		toogle = true;
    		}
    	}
    	//}
        
    }
    /**
     * stops the truck
     */
    public void stop()
    {
    	//System.out.println(toString() + " stopped");
    	running = false;
    }
    /**
     * resumes movement if the truck was stopped
     */
    public void resume()
    {
    	//System.out.println(toString() + " resumed");
    	running = true;
    }
    /**
     * slows the truck, a slowed truck moves once every two calls to the movement
     */
    public void slow()
    {
    	slowed = true;
    	toogle = true;
    }
    /**
     * un-slows the truck, returning the speed to the original one
     */
    public void pace()
    {
    	slowed = false;
    	toogle = false;
    }
    /**
     * 
     * @return true: if the truck arrived finished last segment.
     * false: otherwise.
     */
    public boolean hasArrived()
    {
    	return arrived;
    }
    /**
     * 
     * @return reference to the current segment
     */
    public Segment getSegment()
    {
        return route.array[currentSegment];
    }
    /**
     * 
     * @return A reference to the last segment in the truck's route
     */
    public Segment getLastSegment()
    {
    	return route.array[route.array.length -1];
    }
    /**
     * 
     * @return the next segment if the current one is not the last one, if it is returns null
     */
    public Segment getNextSegment()
    {
    	if(!onLastSegment())
    		return route.array[currentSegment+1];
    	else
    	{
    		System.out.println(currentSegment +":" +route.getSize());
    		//System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
    		return null;
    	}
    }
    
    public String getID()
    {
        return ID;
    }
    
    /*public Segment getCurrent()
    {
        return current;
    }*/
    
    public void setSegPos(int segPos)
    {
        this.segPos = segPos;
    }
    
    public int getSegPos()
    {
        return segPos;
    }

    public void setRoute(Route routex)
    {
        this.route = routex;
    }
    
    public Route getRoute()
    {
        return route;
    }
    
    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }
    public int getStartTime()
    {
        return startTime;
    }
    
    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }
    
    public int getArrivalTime()
    {
        return arrivalTime;
    }
    
    
    /** 
     * sets the current status of the truck
     * valid values are
     * 1: truck is waiting to run
     * 2: truck is running
     * 3: truck is finished
     * @param status to set
     */
    public void setStatus(int status)
    {
        if ((status < 1) || (status > 3))
        {
            throw (new IllegalArgumentException("Status code outside valid range"));
        }
        else
        {
            this.status = status;
        }
    }
    
    
    /** returns the status of the truck
     * values are
     * 1: truck is waiting to run
     * 2: truck is running
     * 3: truck is finished
     */
    public int getStatus()
    {
        return status;
    }
    /**
     * 
     * @return String version of the status of the truck
     */
    public String getStat()
    {
    	if(!running)
    		return "STOPPED";
    	else if(slowed)
    		return "SLOWED";
    	else
    		return "RUNNING";
    }
    
    /** @return the drivers name
     * 
     */
    public String toSegment()
    {
        return currentSegment +"/"+ route.getSize();
    }
    
    /** @return the drivers name
     * 
     */
    public Driver getDriver()
    {
        return driver;
    }
    
    /**
     * sets the drivers name
     * 
     */
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    
    public int getCurrentSegment()
    {
        return currentSegment;
    }
    /**
     * 
     * @return reference to current segment
     */
    public Segment on()
    {
    	return current;
    }
    /**
     * sets the current segment
     * @param currentSegment by position in the route
     */
    public void setCurrentSegment(int currentSegment)
    {
        this.currentSegment = currentSegment;
    }
    
    /**
     * @return A copy of the truck, not a reference but an actual copy of the Truck
     */
    public Truck getCopy()
    {
        Truck copy = new Truck(ID, driver, route, startTime, numSegments);
        try 
        {
            copy.setStatus(getStatus());
        }
        catch (Exception e)
        {
        }
        copy.setArrivalTime(getArrivalTime());
        copy.setCurrent(current);
        copy.setSegPos(segPos);
        copy.setCurrentSegment(currentSegment);
        
        return copy;
    }
    /**
     * @return ID_driver_status
     */
    public String toString()
    {
    	if(driver == null)
    		return ID + " " + "NO DRIVER" + " " + status;
        return ID + " " + driver.toString() + " " + status;
    }
    /**
     * @return ID:driver:status
     */
    public String info()
    {
    	return  ID + ":" + driver + ":" + status;
    }
    
    
    
    
}

