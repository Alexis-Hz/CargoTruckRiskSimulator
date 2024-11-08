package eventSim.Protocol.Service;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import model.traffic.Event;

/**
 * Event Generator
 * @author agarza6
 *
 *Work Log
 *Created initial class, inserted DB connection class.			March 03, 2009
 *Created methods to query the db								March 05, 2009
 *Changed getTrip method's query string							March 12, 2009
 *Created getRoute method 										March 19, 2009
 *
 */
public class EventGenProtocol {

	private String host ="iw.cs.university.edu";
	private String port = "3306";
	private String database = "cargo";
	private String username = "simUser";
	private String pwd = "simUser2008";
	private DBConfiguration mySQLConfig;
	private Connection mySQLConn;
	private Statement query_string;
	private ResultSet result_set;

	public static void main(String[] args)
	{
		EventGenProtocol protocol = new EventGenProtocol();
		String[][] info = protocol.getTrip();
		System.out.println(info[0]);
	}

	/**
	 * Init(). initialize a database connection.
	 */
	private void init(){
		mySQLConfig = new DBConfiguration(host,port,database, username,pwd);
		mySQLConn = connect(mySQLConfig);
		try
		{
			query_string = mySQLConn.createStatement();
		}
		catch (Exception e){e.printStackTrace();}
	}

	/**
	 * Connect. Make initial connection to a database given the DB configuration.
	 * @param dbConf
	 * @return
	 */
	private Connection connect(DBConfiguration dbConf){
		if( dbConf !=null){
			Connection conn = null;

			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://"+dbConf.getHost()+":"+dbConf.getPort()+"/"+dbConf.getDatabase();
				String user = dbConf.getUserName();
				String pwd = dbConf.getPwd();
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url,user,pwd);
				System.out.println("DB Connection: Succesful");

			} 
			catch (SQLException e) {
				System.out.println("DB Connection: Could not get a JDBC connection");
				e.printStackTrace();		
			}
			catch (Exception ex) {
				System.out.println("DB Connection: JDBC is not OK. Make sure you have included JDBC library.\n"+ex.getMessage());
				ex.printStackTrace();
			}
			return conn;
		}
		else{
			throw new IllegalStateException("DB Connection: DB Configuration not stablished. Please provide a configuration before proceeding");
		}
	}

	/**
	 * Close Connection
	 */
	private void closeConnection(){
		try{
			this.mySQLConn.close();
		}catch (Exception e){}
	}

	/**
	 * 
	 * @return
	 */
	public String[][] getTrip(){

		String[][] trip_info;
		int count, index = 0;
		
		init();

		try{
			result_set = query_string.executeQuery("SELECT count(TripID) from Trip");

			result_set.next();
			count = result_set.getInt(1);
			trip_info = new String[2][count];

			result_set = query_string.executeQuery("SELECT Trip.TripID, Maquila.Maquila_Name, POE.Bridge" + 
					" FROM Trip, Trip_Origination, Maquila, Trip_Destination, POE" +
					" WHERE (Trip.TripID = Trip_Origination.Trip_ID" + 
					" AND Trip_Origination.Maquila_ID = Maquila.Maquila_ID)" +
					" AND (Trip.TripID = Trip_Destination.TripID" + 
					" AND Trip_Destination.POE_ID = POE.POE_ID)");

			while(result_set.next() && index < count){
				//trip_info[0][index] = result_set.getString(1);
				trip_info[0][index] = result_set.getString(2);
				trip_info[1][index] = result_set.getString(3);
				index++;
			}

			closeConnection();

			return trip_info;

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}
	
	public String getRoute(String trip_orig, String trip_dest){

		String route_coor;
		int tripID;
		init();

		try{
			
			result_set = query_string.executeQuery("SELECT Trip.TripID" +
					" FROM Trip, Maquila, POE, Trip_Destination, Trip_Origination" +
					" WHERE (POE.Bridge='"+trip_dest+"' AND Maquila.Maquila_Name='"+trip_orig+"')" +
					" AND (Maquila.Maquila_ID=Trip_Origination.Maquila_ID AND POE.POE_ID=Trip_Destination.POE_ID)" +
					" AND (Trip_Destination.TripID=Trip.TripID AND Trip_Origination.Trip_ID=Trip.TripID)");
			
			result_set.next();
			tripID = result_set.getInt(1);
			
			result_set = query_string.executeQuery("SELECT RouteCoordinates FROM Planned_Route WHERE TripID=" + tripID+""); 

			result_set.next();
			route_coor = result_set.getString(1);
			
			closeConnection();
			
			return route_coor;

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	public boolean insertEvent(String query, String Orig, String End){
		int tripID;
		
		init();
		
		try{
			
			result_set = query_string.executeQuery("SELECT Trip.TripID" +
					" FROM Trip, Maquila, POE, Trip_Destination, Trip_Origination" +
					" WHERE (POE.Bridge='"+End+"' AND Maquila.Maquila_Name='"+Orig+"')" +
					" AND (Maquila.Maquila_ID=Trip_Origination.Maquila_ID AND POE.POE_ID=Trip_Destination.POE_ID)" +
					" AND (Trip_Destination.TripID=Trip.TripID AND Trip_Origination.Trip_ID=Trip.TripID)");
			
			result_set.next();
			tripID = result_set.getInt(1);

			query = query.replace("Trip_ID", tripID+"");
			query_string.executeUpdate(query);		
			
			closeConnection();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public int[] getEventIDs(){
		int index;
		int[] sessionIDs;
		
		init();
		
		try{
			result_set = query_string.executeQuery("SELECT COUNT(DISTICT sessionID) FROM Event");
			
			result_set.next();
			index = result_set.getInt(1);
			sessionIDs = new int[index];
			
			result_set = query_string.executeQuery("SELECT DISTICT sessionID FROM Event");
			
			int count = 0;
			while(result_set.next() && count < index){
				sessionIDs[count] = result_set.getInt(1);
				count++;
			}
			
			closeConnection();
			return sessionIDs;
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Event> getEvents(int SessionID){
		List<Event> Events = new LinkedList<Event>();

		init();
		try{
			result_set = query_string.executeQuery("SELECT * FROM Event WHERE sessionID = " + SessionID + "ORDER BY start_time");
			
			while(result_set.next())
				Events.add(new Event(result_set.getInt(1)+"", result_set.getInt(2)+"",
						result_set.getString(3),result_set.getInt(4)+"",result_set.getInt(5)+"",
						result_set.getString(6),result_set.getInt(7)+""));

			closeConnection();
			return Events;
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DB Configuration embedded Class
	 * necessary to realize connection to the database.
	 * @author agarza6
	 */
	private class DBConfiguration {

		private String confName;
		private String host;
		private String port;
		private String database;
		private String userName;
		private String pwd;

		/** Dummy constructor used for XML serialization **/
		public DBConfiguration(){}

		/**
		 * @param host
		 * @param port
		 * @param database
		 * @param userName
		 * @param pwd
		 */
		public DBConfiguration(String host, String port, String database, String userName, String pwd) {
			super();
			this.host = host;
			this.port = port;
			this.database = database;
			this.userName = userName;
			this.pwd = pwd;
			setConfName(database+"@"+host);
		}

		/**
		 * @return the database
		 */
		public String getDatabase() {
			return database;
		}
		/**
		 * @param database the database to set
		 */
		public void setDatabase(String database) {
			this.database = database;
		}
		/**
		 * @return the host
		 */
		public String getHost() {
			return host;
		}
		/**
		 * @param host the host to set
		 */
		public void setHost(String host) {
			this.host = host;
		}
		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}
		/**
		 * @param port the port to set
		 */
		public void setPort(String port) {
			this.port = port;
		}
		/**
		 * @return the pwd
		 */
		public String getPwd() {
			return pwd;
		}
		/**
		 * @param pwd the pwd to set
		 */
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		/**
		 * @return the confName
		 */
		public String getConfName() {
			return confName;
		}

		/**
		 * @param confName the confName to set
		 */
		public void setConfName(String confName) {
			this.confName = confName;
		}

		public String toString(){
			return confName;
		}


	}

}

