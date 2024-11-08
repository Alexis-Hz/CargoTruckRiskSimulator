package model.poem;

import java.sql.*;
import model.cargo.*;
import model.tds.*;
import java.util.Date;
import java.text.DateFormat;
/**
 * <b>Trip Queue Connection</b><p>
 * This class is the connection with the cargo database that handles the interaction between the Officer view and the actual simulator.
 * It loads what happens in the simulator, be it arrivals or events that affect the officer view, and uploads the relevant data to the truck queue database
 * under the session id for each session
 * @author Jorge Alexis Hernandez
 * @version April 2010
 *
 */
public class TQConnection {
	/**
	 * Empty constructor, it is required to initialize
	 */
	boolean mock = true;
	public TQConnection()
	{
		
	}
	Connection conexion;
	int sid = 0;
	boolean test;
	/**
	 * This method initializes the connection with the truck queue database it obtains a session id and 
	 * sets all required data.  
	 * @param testx is this a test or not
	 * @return the session id.
	 */
	public int initConnection(boolean testx)
	{
		test = testx;
		if(mock)
		{
			return 1337;
		}
		else
		{
			//int sessionId = 0;
			try
			{
			   
				DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
				System.out.println("$$$$Driver Loaded$$$$");
	
				conexion = DriverManager.getConnection (
				"jdbc:mysql://iw.cs.university.edu,:3306/truck_queue","simUser", "simUser2008");
				System.out.println("$$$$Connection Created$$$$");
				
				sid = createSession();
				resetPOE();
				clearTrips();
				resetPOE();
				
			} catch (Exception e)
			{
			   e.printStackTrace();
			} 
			
			return sid;
		}
	}
	int lineCounter = 1;
	int poeCounter = 1;
	/**
	 * This method inserts a trip that has arrived, or completed its route into the Truck Queue 
	 * @param sidx session id
	 * @param t Trip that has arrived
	 * @param time time at which the trip arrived, or completed its route
	 */
	public void insert(int sidx, Trip t, int time)
	{
		//sid = 1;
		if(false)
		{
			System.out.println("Fake Insert for Test");
		}
		else
		{
			//sid = 1;
			
			int POE = 1;
			int laneID = 1;
			String PMLREF = "dummyref";
			TdsCall tds =  new TdsCall();;
			PMLREF = tds.getUri("0", "route1", "route1", time+"", time+"");
			System.out.println("$$$$$$$$$$$$$$" + PMLREF + "$$$$$$$$$$$$$$");
			System.out.println(PMLREF);
			String truckInfo = t.info();
			char status = 't';
			String TimeStamp = time+"t";
			String Recommendation = "dummyrec";
			Recommendation = tds.getLight(t.getTruckvin(), "route1", "route1", time+"", time+"");
			System.out.println("$$$$$$$$$$$$$$" + Recommendation + "$$$$$$$$$$$$$$");
			
			laneID = lineCounter;
			if(lineCounter > 6)
				lineCounter = 1;
			else
				lineCounter++;
			POE = poeCounter;
			if(poeCounter ==1)
				poeCounter = 2;
			else
				poeCounter = 1;
			
			Recommendation = "green";
			
			String todo = ("INSERT into trip " + "VALUES(" + sid + ", " +
				POE + ", " + laneID  + ", " + PMLREF + ", \"" + truckInfo + "\", \'" + status + "\', \"" +
				TimeStamp + "\", \"" + Recommendation + "\")");
			
			int r = -1;
			try
			{
				java.sql.Statement s = conexion.createStatement();
	            r = s.executeUpdate (todo);
			}
			catch(Exception e)
			{
				System.out.println("$$$$Error Adding trip with "+ r +"$$$$");
				System.out.println("$$$$"+ todo +"$$$$");
				e.printStackTrace();
			}
		}
	}
	/**
	 * this method creates a new session, by querieng the last session and calculating the next session id
	 * @return session id
	 */
	public int createSession()
	{
        Date date = new Date();
        int d = date.getYear() + 1900;
		
		String todo = "INSERT INTO session (timestamp) VALUES(" + d +")";
		String todo2 = "SELECT MAX(sessionID) AS sessionID FROM session";
		int sid = 0;
		int r = -1;
		try
		{
			java.sql.Statement s = conexion.createStatement();
            r = s.executeUpdate (todo);
            java.sql.Statement z = conexion.createStatement();
            ResultSet res = z.executeQuery(todo2);
            res.next();
            sid = (Integer)res.getInt(1);
//            for(int j =0; j < 150; j++)
//            	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Session ID: " + sid);
		}
		catch(Exception e)
		{
			System.out.println("$$$$Error Creating a Session with "+ r +"$$$$");
			e.printStackTrace();
		}
		return sid;
	}
	/**
	 * This method is called on a reset, it resets all data for a session
	 */
	public void resetPOE()
	{
		String todo = "update POE set currAvail =1";
		
		int r = -1;
		try
		{
			java.sql.Statement s = conexion.createStatement();
            r = s.executeUpdate (todo);
		}
		catch(Exception e)
		{
			System.out.println("$$$$Error setting POE table to 1 with "+ r +"$$$$");
			e.printStackTrace();
		}
	}
	public void clearTrips()
	{

	String todo = "TRUNCATE TABLE trip";
	
	int r = -1;
	try
	{
		java.sql.Statement s = conexion.createStatement();
        r = s.executeUpdate (todo);
	}
	catch(Exception e)
	{
		System.out.println("$$$$Error Clearing trip table with "+ r +"$$$$");
		e.printStackTrace();
	}
	}
	/**
	 * A simple main to test this Conection, if you are unsure the connection works this would be the place to start debugging
	 * @param args
	 */
	public static void main(String [] args)
	{
		try
		{
		   
			DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
			System.out.println("Driver Loaded");

			Connection conexion = DriverManager.getConnection (
			"jdbc:mysql://iw.cs.university.edu,:3306/truck_queue ","simUser", "simUser2008");
			System.out.println("Connection Created");
			
			
			
		} catch (Exception e)
		{
		   e.printStackTrace();
		} 
	}
}
