package control;

import java.util.*;

import model.cargo.*;
import model.traffic.Coord;
import model.traffic.Entry;
import model.traffic.Event;
import model.traffic.Route;

import java.math.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * This class handles all events, it gets the events from the cargo database and stores the event scenario requested.
 *  it then at each time checks if there is an event that should be triggered at this moment, if there is it triggers it
 *  and resolves it using the responder class.
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class EventHandler {
	/*Event List*/
	List<Event> events;// = new LinkedList<Event>();
	List<Event> executingEvents = new LinkedList<Event>();
	List<Event> finishedEvents = new LinkedList<Event>();
	
	Responder responder;

	/*Event Variables*/
	int globalTime, sessionID;

	boolean test;

	/**
	 * creates the event handler and initializes the events list, running not running and the scenario
	 * Precondition: sessionID must not be null.
	 * @param testx
	 */
	public EventHandler(int sid, boolean testx, Responder resp, int scenario)
	{;
		//events = new LinkedList<Event>();
		test = testx;
		if(test)
		{
			//mock event populate
			System.out.println("                                          "+"~mock event populate~");
			events = new LinkedList<Event>();
			executingEvents = new LinkedList<Event>();
			finishedEvents = new LinkedList<Event>();
			
			responder = resp;
			setSessionID(sid);
			
			//read event scenario
			Event ne;
			try
			{
				//BufferedReader reader = new BufferedReader(new FileReader(new File(".").getAbsoluniversityath() +"\\jsp\\scenarioa.txt"));
				BufferedReader reader = new BufferedReader(new FileReader(new File(".").getAbsoluniversityath() +"/webapps/cargo/scenarioa.txt"));
				String line = null;
				while ((line = reader.readLine()) != null) {
				    //System.out.println(line);
				    String [] eventToken = line.split(" ");
				    String [] coordToken = eventToken[3].split(",");
				    String coordPressed = coordToken[0] + ", " + coordToken[1];
				    //for(int i = 0; i < eventToken.length; i++)
				    	//System.out.println(eventToken[i]);
				    ne = new Event(eventToken[0],eventToken[1],sid+eventToken[2],coordPressed,eventToken[4],eventToken[5],eventToken[6],eventToken[7],eventToken[8],eventToken[9]);
					events.add(ne);
				}
			}
			catch(Exception e)
			{
				new File(".").getAbsoluniversityath();
				System.out.println(new File(".").getAbsoluniversityath());
				System.out.println("FILE NOT FOUND, TRYING LOCALHOST LOCATION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				
				//try localhost file
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(new File(".").getAbsoluniversityath() +"\\jsp\\scenarioa.txt"));
					
					String line = null;
					while ((line = reader.readLine()) != null) {
					    //System.out.println(line);
					    String [] eventToken = line.split(" ");
					    String [] coordToken = eventToken[3].split(",");
					    String coordPressed = coordToken[0] + ", " + coordToken[1];
					    //for(int i = 0; i < eventToken.length; i++)
					    	//System.out.println(eventToken[i]);
					    ne = new Event(eventToken[0],eventToken[1],sid+eventToken[2],coordPressed,eventToken[4],eventToken[5],eventToken[6],eventToken[7],eventToken[8],eventToken[9]);
						events.add(ne);
					}
				}
				catch(Exception ee)
				{	
					new File(".").getAbsoluniversityath();
					System.out.println(new File(".").getAbsoluniversityath());
					System.out.println("FILE NOT FOUND, NOTHING ELSE TO TRY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					
					//try localhost file
				}
				
			}
			/*
			if(false)
			{
				ne = new Event("1","1",sid+"","370370.5334, 3497869.6521","6","1","ST-DEPLOY","yes","mock 1","1");
				events.add(ne);
				ne = new Event("2","2",sid+"","370370.5334, 3497869.6521","3","1","ST-DEPLOY","yes","mock 2","1");
				events.add(ne);
				ne = new Event("3","3",sid+"","370370.5334, 3497869.6521","9","1","ST-DEPLOY","yes","mock 1","1");
				events.add(ne);
				ne = new Event("4","4",sid+"","370370.5334, 3497869.6521","12","1","ST-DEPLOY","yes","mock 2","1");
				events.add(ne);
				ne = new Event("5","5",sid+"","370370.5334, 3497869.6521","15","1","ST-DEPLOY","yes","mock 1","1");
				events.add(ne);
				ne = new Event("6","6",sid+"","370370.5334, 3497869.6521","18","1","ST-DEPLOY","yes","mock 2","1");
				events.add(ne);
				ne = new Event("7","7",sid+"","370370.5334, 3497869.6521","21","1","ST-DEPLOY","yes","mock 1","1");
				events.add(ne);
				ne = new Event("8","8",sid+"","370370.5334, 3497869.6521","24","1","ST-DEPLOY","yes","mock 2","1");
				events.add(ne);
				
				ne = new Event("9","9",sid+"","0, 0","30","10","SG-STOP","yes","mock 2","1");
				events.add(ne);
				
				ne = new Event("11","11",sid+"","0, 0","50","10","SG-SLOW","yes","mock 2","1");
				events.add(ne);
				
				ne = new Event("10","10",sid+"","0, 0","140","5","SG-DEPLOY","yes","mock 2","1");
				events.add(ne);
				
				ne = new Event("12","8",sid+"","0, 0","90","10","ST-STOP","yes","mock 2","1");//stop trip 8
				events.add(ne);
				
				ne = new Event("13","8",sid+"","0, 0","105","10","ST-SLOW","yes","mock 2","1");//stop trip 8
				events.add(ne);
			}
			*/
			
		}
		else
		{
			executingEvents = new LinkedList<Event>();
			finishedEvents = new LinkedList<Event>();
			
			test = testx;
			setSessionID(sid);
			createEvents(test);
			updateSessionID();
			responder = resp;
			getEvents(scenario);
		}
//		Iterator iter = events.iterator();
//		System.out.println("EVENTS!!!!!!!");
//		while(iter.hasNext())
//		{
//			Event next = (Event)iter.next();
//			System.out.print(next.toString());
//		}
	}

	public void setGlobalTime(int time){
		globalTime = time;
	}
	public int getGlobalTime(){return globalTime;}

	
	public void setSessionID(int ID){
		sessionID = ID;
	}
	public int getSessionID(){return sessionID;}
	/**
	 * updates the session id by creating a new session and setting the session id
	 */
	private void updateSessionID(){
		CargoConnection conn = new CargoConnection();
		conn.initConnection();
		conn.setEventSessionID(sessionID);
		conn.closeConnection();
	}
	
	/**
	 * creates a new cargo connection and Fetches the list of events from the cargo database
	 */
	public void getEvents(int scenario){
		CargoConnection conn = new CargoConnection();
		conn.initConnection();
		events = conn.getEvents(scenario);//add SID
		try
		{
			while (events == null)
				wait(999);
		}
		catch(InterruptedException e)
		{
			
		}
		conn.closeConnection();
	}

	/**
	 * Iterate through the events list and find which are ready to be executed. and then updates the ones that are already running
	 */
	public void findEventsToExecute(){
		//getEvents();
		findEvents();
		updateExecutingEvents();
	}	
	/**
	 * finds events ready to be executed i the events list and executes them.
	 */
	public void findEvents(){

		try
		{	
			Iterator<Event> EvList = getEventIterator();
	
			while(EvList.hasNext()){
				Event tmpEv = (Event)EvList.next();
				String[] eType = tmpEv.getEventType();
				System.out.println("Event: " + tmpEv.toString());
	
				switch(eType[0].charAt(0)){
				case 'A':
					if(false)										//change for check of location
						executingEvents.add(tmpEv);
					break;
				case 'S':
					if(tmpEv.getStartTime() == globalTime)
					{
						//System.out.println("STAR");
						executingEvents.add(tmpEv);
					}
					break;
				default:
					break;
				}
	
			}
		}
		catch(Exception e)
		{
			//sim.logger.log.addEntry(new Entry(time, "from: " + per + " to: " + p, "PER CHANGED"));
		}
	}
	/**
	 * updates the events currently in execution, decaying them and killing them of needed
	 */
	public void updateExecutingEvents(){
		try
		{
			Iterator<Event> evIt = getExecEventIterator();
			while(evIt.hasNext()){
				Event tmpEv = (Event)evIt.next();
				System.out.println("Exec Event: " + tmpEv.toString());
				String[] eType = tmpEv.getEventType();
	
				if(tmpEv.getDuration() == 0)
				{
					killEvent(tmpEv);
				}
				else
				{
					switch(eType[0].charAt(0)){
					case 'A':
					{
						//System.out.println("ASYNCH");
						switch(eType[1].charAt(0)){
						case 'G':
							asyncGlobal(tmpEv);
							break;
						case 'T':
							asyncTrip(tmpEv);
							break;
						default: break;
						}
						break;
					}
					case 'S':
					{
						System.out.println("SYNCH");
						switch(eType[0].charAt(1)){
						case 'G':
							//System.out.println("G");
							syncGlobal(tmpEv);
							break;
						case 'T':
							//System.out.println("T");
							syncTrip(tmpEv);
							break;
						default:
							//System.out.println("X");
							break;
						
						}
						break;
					}
					default:
						System.out.println("NOT A NOR S");
						break;
					}
	
				}
				tmpEv.decay();
			}
		}
		catch(ConcurrentModificationException e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.toString());
		}
	}
	/**
	 * executes synchronous global events
	 * @param evt event to execute
	 */
	public void syncGlobal(Event evt){
		String[] type = evt.getEventType();
		int op = getEventType(type[1]);
		switch(op){
		case 0:
			if(!evt.isOn())
			{
				System.out.println("SYNCH GLOBAL DEPLOY");
				responder.setDeploy(1);//call deploy method
				evt.setOn(true);
			}
			
			break;
		case 1:
			System.out.println("SYNCH GLOBAL CANCEL");
			;//re-route method to origin
			break;
		case 2:
			if(!evt.isOn())
			{
				System.out.println("SYNCH GLOBAL SLOW");
				responder.slowAll();//slow method
				evt.setOn(true);
			}
			break;
		case 3:
			if(!evt.isOn())
			{
				System.out.println("SYNCH GLOBAL STOP");
				responder.stopAll();//stop method
				evt.setOn(true);
			}
			break;
		case 4:
			if(!evt.isOn())
			{
				System.out.println("SYNCH GLOBAL BLOCK");//re-route method call
				Coord c = new Coord(evt.getCoordY(), evt.getCoordX());
				System.out.println("@ "+c.toString());
				responder.createBlock(Integer.parseInt(evt.getEventID()), c);
				evt.setOn(true);
				System.out.println("block created");
			}
			break;
		default:
			System.out.println("MISSMATCH EVENT");
			break;
		}
			
	}
	/** 
	 * executes synchronous trip events
	 * @param evt event
	 */
	public void syncTrip(Event evt){
		System.out.println("Synch Trip");
		String[] type = evt.getEventType();
		int op = getEventType(type[1]);
		
		switch(op){
		case 0:
			System.out.println("One Truck Sent");
			System.out.println(evt.getTripID());
			responder.sendTruck(Integer.parseInt(evt.getTripID()));//call deploy method
			killEvent(evt);
			System.out.println("Send Event Killed("+evt.getTripID() +")");
			break;
		case 1:
			System.out.println("SYNCH TRIP CANCEL");
			;//re-route method to origin
			break;
		case 2:
			if(!evt.isOn())
			{
				System.out.println("SYNCH TRIP SLOW");
				responder.slowTrip(evt.getTripID());//slow method
				evt.setOn(true);
			}
			break;
		case 3:
			if(!evt.isOn())
			{
				System.out.println("SYNCH TRIP STOP");
				responder.stopTruck(Integer.parseInt(evt.getTripID()));//stop method
				System.out.println("Trip "+ evt.getTripID()+ " Stopped");
				break;
			}
		case 4:
			if(!evt.isOn())
			{
				System.out.println("SYNCH TRIP BLOCK");//re-route method call
				Coord c = new Coord(evt.getCoordX(), evt.getCoordY());
				System.out.println("@ "+c.toString());
				responder.blockTrip(evt.getTripID());
				evt.setOn(true);
			}
			break;
		}
	}
	/** 
	 * executes asynchronous global events
	 * @param evt event to execute
	 */
	public void asyncGlobal(Event evt){
		String[] type = evt.getEventType();
		int op = getEventType(type[1]);
		
		switch(op){
		case 1:
			;//re-route method to origin
			break;
		case 2:
			;//slow method
			break;
		case 3:
			;//stop method
			break;
		case 4:
			;//re-route method call
			break;
		}
	}
	/**
	 * executes asynchronous trip events
	 * @param evt event to execute
	 */
	public void asyncTrip(Event evt){
		String[] type = evt.getEventType();
		int op = getEventType(type[1]);
		
		switch(op){
		case 1:
			;//re-route method to origin
			break;
		case 2:
			;//slow method
			break;
		case 3:
			;//stop method
			break;
		case 4:
			;//re-route method call
			break;
		}
	}

	/**
	 * Gets the event Type<p>
	 * Types:
	 * <ul>
	 * <li>DEPLOY = 0
	 * <li>CANCEL = 1
	 * <li>SLOW = 2
	 * <li>STOP = 3
	 * <li>BLOCK = 4
	 * </ul>
	 * @param type String representation of the type of event
	 * @return the type of event that it is
	 */
	private int getEventType(String type){
		if(type.equalsIgnoreCase("DEPLOY"))
			return 0;
		if(type.equalsIgnoreCase("CANCEL"))
			return 1;
		if(type.equalsIgnoreCase("SLOW"))
			return 2;
		if(type.equalsIgnoreCase("STOP"))
			return 3;
		if(type.equalsIgnoreCase("BLOCK"))
			return 4;
		return -1;
	}
	/**
	 * Kills an event, removing and retiring it
	 * @param evt event to be killed
	 */
	public void killEvent(Event evt){
		String[] type = evt.getEventType();
		int op = getEventType(type[1]);//originally 2
		String gt = type[0]; //originally 1
		System.out.println(type[0]+":"+type[1]+":"+op+":"+gt);
		switch(op){
		case 0://DEPLOY
			if(gt.charAt(1)== 'G')
			{
				System.out.println("Deploy Killed(-1)");
				responder.setDeploy(-1);//call deploy method
			}
			else
			{
				
			}
			
			break;
		case 1://CANCEL
			;//re-route method to origin
			break;
		case 2://SLOW
			if(gt.charAt(1)== 'G')
			{
				System.out.println("Global Stop Killed");
				responder.paceAll();
				evt.setOn(true);
			}
			else//Trip
			{
				System.out.println("Trip Stop Killed");
				responder.paceTruck(Integer.parseInt(evt.getTripID()));
				evt.setOn(true);
			}
			;//slow method
			break;
		case 3://STOP
			if(gt.charAt(1)== 'G')
			{
				System.out.println("Global Stop Killed");
				responder.resumeAll();
				evt.setOn(true);
			}
			else//Trip
			{
				System.out.println("Trip Stop Killed");
				responder.resumeTruck(Integer.parseInt(evt.getTripID()));
				evt.setOn(true);
			}
			;//stop method
			break;
		case 4://BLOCK
			System.out.println("Trip Block Killed");
			responder.deleteBlock(Integer.parseInt(evt.getTripID()));
			evt.setOn(true);
			;//re-route method call
			break;
		}
		finishedEvents.add(evt);
		executingEvents.remove(evt);
	}
	/**
	 * gets an iiterator for the events list
	 * @return and Iterator object for the event list
	 */
	public Iterator<Event> getEventIterator()
	{
		return events.iterator();
	}
	/**
	 * gets and Iterator for the executing events list
	 * @return Iterator object for the executing events list
	 */
	public Iterator<Event> getExecEventIterator()
	{
		return executingEvents.iterator();
	}
	/**
	 * removes an event from the events list
	 * @param i event to be remove
	 */
	public void remove(int i)
	{
		events.remove(i);
	}

//	public void nextEvent(int time, Logger logger, PoolBoy poolboy, List trips, List onRoute, Coord poe)
//	{
//		System.out.println(">>>>>>>>>>>>>NextEvent Call");
//		Random rr = new Random();
//		int r = rr.nextInt(100);
//		Iterator iter = trips.iterator();
//		int c = 0;
//		if(iter.hasNext())
//		{
//			Trip t = (Trip)iter.next();
//			if(c == r)
//			{
//				sendTripOff(t, poolboy, logger, onRoute, poe, time);
//			}
//			c++;
//		}
//	}
//	public int checkSendEvents(int time, Logger logger, PoolBoy poolboy, List trips, List onRoute, Coord poe)
//	{
//		Iterator iter = events.iterator();
//		int ts = 0;
//		while(iter.hasNext())
//		{
//			Event eve = (Event)iter.next();
//			if((eve.getEventName().equalsIgnoreCase("send")) && (eve.getStartTime() == time))
//			{
//				ts++;
//			}
//			
//		}
//		return ts;
//	}
//	public void createSendEvent(int i)
//	{
//		Event e = new Event();
//		e.setEventName("send");
//		e.setStartTime(i);
//		events.add(e);
//	}

//	public void sendTripOff(Trip tripx, PoolBoy poolboy, Logger logger, List onRoute, Coord poe, int time)
//	{
//		Route routet = poolboy.findRoute(new Coord(0,0), poe);//to change for a method that looks up routes in RL
//		tripx.setRoute(routet);//fake route set
//		onRoute.add(tripx);//putting the trip onRoute
//		tripx.getTruck().start();
//		logger.log.addEntry(new Entry(time, "Truck Sent", "Truck: " + tripx.getTruckvin() + "=Driver: " + tripx.getDriverName()));
//
//
//	}
	/**
	 * Empty Method
	 * @param test
	 */
	public void createEvents(boolean test)
	{
		
	}

}
