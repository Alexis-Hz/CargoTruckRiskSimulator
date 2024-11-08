package model.traffic;

/**
 * Events that occur during the running of the simulator
 * A generic Event Log used to pass information or store events
 * 
 * @author Antonio Garza
 * @version 0.90 
 */

public class Event {

	//Event Variables
	private String sEventName;
	private String sEvent_ID;
	private String sTripID;
	private String reason;
	private String[] aType;

	private Boolean bReported;

	private Coord Coordinate;
	private int iDuration;
	private int iStart;
	private int iSessionID;
	private int iInterval;
	
	private boolean on = false; 

	/*From Previous version of Event, not fully implemented in DB yet*/
	private int severity = 0;
	private String affectedSegments[] = new String[10]; // affected segments
	private int numAffected = 0;                        // the number of affected segments

	/*Constructors*/
	/**
	 * succinct constructor, defaults are:
	 * <ul>
	 * <li> sEvent_ID, sTripID, aType = null
	 * <li> bReported = false
	 * <li> Coordinate = (0,0)
	 * <li>iDuration = 0
	 * <li> iStart = 0
	 * </ul>
	 */
	public Event(){
		sEvent_ID = null;
		sTripID = null;
		aType = null;
		bReported = false;
		Coordinate = new Coord(0.0, 0.0);
		iDuration = 0;
		iStart = 0;
	}
	/**
	 * Complete Constructor
	 * @param EID event id
	 * @param TID trip id
	 * @param sessionID session id
	 * @param Coordinates coordinates of event location
	 * @param ST start time of event
	 * @param Duration duration of event
	 * @param type type of event
	 * @param rep reported?
	 * @param rea Reason for event
	 * @param inter interval of event
	 */
	public Event(String EID, String TID, String sessionID, String Coordinates, String ST, String Duration, String type, String rep, String rea, String inter){
		
		sEvent_ID = EID;
		
		iSessionID = Integer.parseInt(sessionID);
		iInterval = Integer.parseInt(inter);
		
		//System.out.println("|"+EID +"|"+TID+"|"+iSessionID+"|"+iInterval);
		sTripID = TID;
		System.out.println("IN EVENT CREATION");
		setEventType(type);
		//System.out.println("1");
		setCoordinates(Coordinates);
		System.out.println("COORDINATES "+ Coordinates);
		
		//System.out.println("2");
		setReported(rep);
		//System.out.println("3");
		setStartTime(ST);
		//System.out.println("4");
		setDuration(Duration);
		//System.out.println("5");
		setReason(rea);
	}

	/*Setters*/
	public void setEventName(String name){
		sEventName = name;
	}
	public void setEventID(String EID){
		sEvent_ID = EID;
	}
	public void setTripID(String TID){
		sTripID = TID;
	}
	public void setSessionID(int SID){
		iSessionID = SID;
	}
	public void setInterval(int inter){
		iInterval = inter;
	}
	public void setEventType(String type){
		System.out.println(type);
		aType = type.split("-");
		/*
		for (int i = 0; i <aType.length; i++)
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+ aType[i] );
			
		}
		*/
	}
	/**
	 * Sets a coordinate, takes string input, parses and sets coordinate
	 * @param XYCoor String representation of coordinate
	 */
	public void setCoordinates(String XYCoor){
		double dCoordinateX;
		double dCoordinateY;
		if(XYCoor.equalsIgnoreCase("null"))
		{
			dCoordinateX = 1337;
			dCoordinateY = 101;
		}
		else
		{
			System.out.println("|"+ XYCoor + "| ");
			String[] q = XYCoor.split(",");
			dCoordinateX = Double.parseDouble(q[0]);
			dCoordinateY = Double.parseDouble(q[1]);
		}
		
		Coordinate = new Coord(dCoordinateX, dCoordinateY);
	}
	public void setCoorX(double X){
		Coordinate.setLatitude(X);
	}
	public void setCoorY(double Y){
		Coordinate.setLongitude(Y);
	}
	public void setReported(String rep){
		if(rep.equals("0"))
			bReported = false;
		if(rep.equals("1"))
			bReported = true;
	}
	public void setStartTime(int ST){
		iStart = ST;
	}
	public void setStartTime(String ST){
		iStart = Integer.parseInt(ST);
	}
	public void setDuration(int dur){
		iDuration = dur;
	}
	public void setDuration(String dur){
		iDuration = Integer.parseInt(dur);
	}
	public void setReason(String rea){
		reason = rea;
	}
	public void setOn(boolean oo)
	{
		on = oo;
	}

	/*Getters*/
	public String getEventName(){return sEventName;}
	public String getEventID(){return sEvent_ID;}
	public String getTripID(){return sTripID;}
	public int getSessionID(){return iSessionID;}
	public int getInterval(){return iInterval;}
	public String[] getEventType(){return aType;}
	public double getCoordX(){return Coordinate.getLatitude();}
	public double getCoordY(){return Coordinate.getLongitude();}
	public int getStartTime(){return iStart;}
	public int getDuration(){return iDuration;}
	public boolean isReported(){return bReported;}
	public String getReason(){return reason;}
	public boolean isOn(){return on;}
	
	
	public int getEndTime(){
		return iStart + iDuration;
	}

	/** Sets the severity of the event
	 *  valid range is from 0 to 10 
	 *  10 being the worst
	 * 
	 */
	public void setSeverity(int severity)
	{
		if ((severity > 10) || (severity < 0))
		{
			throw new IllegalArgumentException("Severity is out of range");
		}
		this.severity = severity;
	}
	public int getSeverity()
	{
		return severity;
	}

	/** Adds an affected segment/area to the list
	 * 
	 */
	public void addAffectedSegment(String segment)
	{
		// if we need to increase the number of affected segments
		// increase the size of the array
		if (affectedSegments.length > (numAffected + 3))
		{
			//create a new array 5 segments larger and copy
			//from the current to the new
			String temp[] = new String[affectedSegments.length + 5];

			for (int i = 0; i < numAffected; i++)
			{
				temp[i] = affectedSegments[i];
			}

			// set the new array to the current
			affectedSegments = temp;
		}

		affectedSegments[numAffected] = segment;
		numAffected++;
	}
	/**Returns the affected Segments in the form of an array
	 * 
	 */
	public String[] getAffectedSegments(){
		//create a new array that contains the exact number of segments
		String temp[] = new String[numAffected];

		//copy the elements over
		for (int i = 0; i < numAffected; i++){
			temp[i] = affectedSegments[i];
		}

		// return the new array
		return temp;
	}
	/**
	 * decays the event, reduces duration, dead on 0 duration
	 */
	public void decay()
	{
		if(iDuration > 0)
			iDuration--;
	}
	/**
	 * @return a digest of all the data int the event
	 */
	public String toString()
	{
		String toRe = "";
		toRe += "EID:" + sEvent_ID + " Eve Type:~"  ;
		for(int i =0; i <aType.length; i++)
		{
			toRe += aType[i] + '~';
		}
		toRe +=" Starts:" + iStart + "Dura: " + iDuration;
		return toRe;
	}
	/**
	 * Simple testing main method
	 * @param args
	 */
	public static void main(String args[]){
		String one = "SYNC - GLOBAL - DELAY - STOP - &";
		
		String[] arg = one.split(" - ");
		
		int i = 0;	
		while(!arg[i].equalsIgnoreCase("&")){
			System.out.println(arg[i]);
			i++;
		}
		
		
	}

}