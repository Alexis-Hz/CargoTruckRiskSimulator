package dhs.oi.protocol.service;

import java.sql.*;

public class OfficerSimProtocol
{

	private String host ="iw.cs.university.edu";
	private String port = "3306";
	private String database = "truck_queue";
	private String username = "simUser";
	private String pwd = "simUser2008";
	private DBConfiguration mySQLConfig;
	private Connection mySQLConn;
	private Statement stmt;
	private ResultSet rs;


	public static void main(String[] args)
	{
		OfficerSimProtocol protocol = new OfficerSimProtocol();
		int[] info = protocol.getLane();
		System.out.println(info[0]);
	}

	private void init()
	{
		mySQLConfig = new DBConfiguration(host,port,database, username,pwd);
		mySQLConn = connect(mySQLConfig);
		try
		{
			stmt = mySQLConn.createStatement();
		}
		catch (Exception e){e.printStackTrace();}
	}

	private Connection connect(DBConfiguration dbConf)
	{
		if( dbConf !=null){
			Connection conn = null;

			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://"+dbConf.getHost()+":"+dbConf.getPort()+"/"+dbConf.getDatabase();
				String user = dbConf.getUserName();
				String pwd = dbConf.getPwd();
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url,user,pwd);
				System.out.println("got db connnection");

			} 
			catch (SQLException e) {
				System.out.println("Could not get a JDBC connection");
				e.printStackTrace();		
			}
			catch (Exception ex) {
				System.out.println("JDBC is not OK. Make sure you have included JDBC library.\n"+ex.getMessage());
				ex.printStackTrace();
			}
			return conn;
		}
		else{
			throw new IllegalStateException("DB Configuration not stablished. Please provide a configuration before proceeding");
		}
	}

	/*
	private void getConnection(){
		try{
			DriverManager.registerDriver("org.gjt.mm.mysql.Driver");
			cConn = DriverManager.getConnection(
					"jdbc:mysql://iw.cs.university.edu,:3306/truck_queue","simUser", "simUser2008"); 
			stmt = cConn.createStatement();
		}
		catch (Exception e){e.printStackTrace();}
	}*/

	private void closeConnection(){
		try{
			this.mySQLConn.close();
		}catch (Exception e){}
	}

	/** Queries the database, gets an empty lane, and assigns it to the officer requesting a lane by returning the lane information.
	 * 
	 * @return int[] laneInfo-- which contains (in order) the laneID, POEID, and SessionID.
	 */
	public int[] getLane()
	{
		int[] laneInfo = new int[3];

		int tempCurrAvail=-1, tempPOE=-1, tempSession=-1;
		init();

		try{


			rs = stmt.executeQuery("select * from POE where numLanes >= currAvail order by currAvail"); //query

			System.out.println("next row: " + rs.next());

			tempCurrAvail  = rs.getInt(3);//currAvail

			tempPOE = rs.getInt("POEID");//POEID

			rs = stmt.executeQuery("SELECT sessionID FROM session ORDER BY sessionID DESC");
			rs.next();
			tempSession = rs.getInt(1);//sessionID


			//laneInfo = LaneID, POEID, SessionID.
			laneInfo[0] = tempCurrAvail;//laneID
			laneInfo[1] = tempPOE;
			laneInfo[2] = tempSession;

			//before update
			tempCurrAvail++;
			stmt.executeUpdate("UPDATE POE set currAvail = " + tempCurrAvail + " WHERE POEID= " + tempPOE); //update currAvail

			closeConnection();

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		return laneInfo;
	}


	/**Gets the next Truck in line at the poe.
	 * 
	 * @param lane -- LaneID
	 * @param poe -- POEID
	 * @param sid -- SessionID
	 * 
	 * @return String truckInfo-- which contains (in order) "the recommendation, PMLRef, and TruckInfo".
	 */
	public String getNextTruck(int lane, int poe, int sid, String truck)
	{

		init();
		String tmpRecommendation = null, tmpTruckInfo=null, tmpPMLREF=null, truckInfo=null;

		try{


			//remove last truck, change status into D (Done)
			if(truck != null)
				stmt.executeUpdate("UPDATE trip set status = 'D' WHERE status = 'I' AND truckInfo = \"" + truck + "\""); //deploy previous truck

			boolean looping = false;
			do
			{
				if(looping)//will sleep if looping.
				{
					try
					{
						Thread.sleep(1000);        
					}catch (InterruptedException ie)
					{System.out.println(ie.getMessage());}

				}
				
				//Query
				rs = stmt.executeQuery("SELECT Recommendation, PMLREF, truckInfo FROM trip" +
						" WHERE status = 'T' AND POE = "+ poe +" AND sessionID = " + sid +
						" ORDER BY timeStamp");

				looping = true;
				
			}while(!rs.first());

			//parse rs object here into recommmendation, truckinfo and pmlref
			tmpRecommendation = rs.getString(1);//Recommendation
			tmpPMLREF = rs.getString(2);//pmlRef
			tmpTruckInfo = rs.getString(3);//truckInfo

			truckInfo = tmpRecommendation+","+tmpPMLREF+","+tmpTruckInfo;
			
			//assign laneID to truck
			stmt.executeUpdate("UPDATE trip set laneID = " + lane + " WHERE status = 'T' AND " +
								"truckInfo = \"" + tmpTruckInfo + "\"");

			//update status of truck
			stmt.executeUpdate("UPDATE trip set status = 'I' WHERE status = 'T' AND " +
								"truckInfo = \"" + tmpTruckInfo + "\"");

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		closeConnection();
		return truckInfo;
	}


//	=======================================================

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