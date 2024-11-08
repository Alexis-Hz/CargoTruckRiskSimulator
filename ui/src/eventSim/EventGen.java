package eventSim;

public class EventGen {

	public static EventGen instance;
	private EGController Controller;


	//Variables
	private int sessionID;
	
	private String TYPE_EVT;
	private int start_time, duration, interval;
	private boolean Reported;
	private String[] iaOrigin;
	private String[] iaDestination;
	private String sRoute, sOrigin, sDestination, sCoor, reason;
	
	public EventGen(){
		instance = this;
		interval = -1;
		Controller = new EGController();
	}

	public void setSessionID(int ID){
		sessionID = ID;
	}
	public void setOriginArray(String[] tmp_trip){
		iaOrigin = tmp_trip;
		Controller.NotifyGUI();
	}
	public void setDestinationArray(String[] tmp_trip){
		iaDestination = tmp_trip;
		Controller.NotifyGUI();
	}
	public void setRoute(String tmp_route){
		sRoute = tmp_route;
		Controller.NotifyGUI();
	}
	public void setOriginPoint(String orig){
		sOrigin = orig;
	}
	public void setDestPoint(String dest){
		sDestination = dest;
	}
	public void setCoor(String coor){
		sCoor = coor;
	}
	public void setEvtType(String type){
		TYPE_EVT = type;
	}
	public void appendEvtType(String type){
		TYPE_EVT = TYPE_EVT + " - " + type;
	}
	public void setReported(int b){
		if(b == 1)
			Reported = true;
		else
			Reported = false;
	}
	public void setStart_time(int st){
		start_time = st;
	}
	public void setStart_time(String st){
		start_time = Integer.parseInt(st);
	}
	public void setDuration(int dur){
		duration = dur;
	}
	public void setDuration(String dur){
		duration = Integer.parseInt(dur);
	}
	public void setInterval(int intl){
		interval = intl;
	}
	public void setInterval(String intl){
		interval = Integer.parseInt(intl);
	}
	public void setReason(String rsn){
		reason = rsn;
	}
	
	public int getSessionID(){
		return sessionID;
	}
	public String[] getOriginArray(){
		return iaOrigin;
	}
	public String[] getDestinationArray(){
		return iaDestination;
	}
	public String getRoute(){
		return sRoute;
	}
	public String getOriginPoint(){
		return sOrigin;
	}
	public String getDestPoint(){
		return sDestination;
	}
	public String getCoor(){
		return sCoor;
	}
	public String getEvtType(){
		return TYPE_EVT;
	}
	public boolean isReported(){
		return Reported;
	}
	public int getStart_time(){
		return start_time;
	}
	public int getDuration(){
		return duration;
	}
	public int getInterval(){
		return interval;
	}
	public String getReason(){
		return reason;
	}

	public String[] getParsedRoute(){
		String[] routeString = sRoute.split(" ");
		String[] coords = new String[routeString.length/2];

		int index = 0;
		for(int i=0; i<routeString.length; i+=2){
			if(routeString[i].length() > 1)
				coords[index] = routeString[i] + routeString[i + 1];
			index++;
		}

		return coords;  
	}
	public double[] getparseCoords(){
		double[] coordsXY = new double[2];
		String[] temp = sCoor.split(",");
		coordsXY[0] = Double.parseDouble(temp[0]);
		coordsXY[1] = Double.parseDouble(temp[1]);
		return coordsXY;
	}
	public void genEventString(){
		String InsertString;
		if(reason.length() <= 0)
			reason = "N/A";
		
//		String[] type = TYPE_EVT.split(" - ");
//		if(type[0].equalsIgnoreCase("ASYNC"))
//			InsertString = genASYNCEventString();
//		else if(type[1].equalsIgnoreCase("GLOBAL"))
//			InsertString = genSYNCGlobalEventString();
//		else
//			InsertString = genSYNCTripEventString();
		
		InsertString = getInsertString();
		
		EventManager EM = new EventManager();
		EM.setQuery(InsertString);
		System.out.println(sOrigin + " .. "+sDestination);
		EM.setOrig(sOrigin);
		EM.setDest(sDestination);
		EM.start();
		System.out.println(InsertString);
	}
	
	private String getInsertString(){
		double[] coords = {0.0, 0.0};
		if(getCoor() != null)
			coords = getparseCoords();
		String InsertStr = "INSERT INTO Event (Event_duration, Reported, Reason, sessionID, Coordinate, start_time, " +
							"TripID, Ev_interval, Event_type) " +
							"Values(" +
							duration + ", " +
							isReported() + ", '" +
							reason + "', " +
							sessionID + ", '" +
							coords[0] + "," + coords[1] + "', " +
							start_time + ", " + 
							"Trip_ID, " + 
							interval + ", '" +
							TYPE_EVT + "')";
		return InsertStr;
	}
	
	public String genSYNCGlobalEventString(){
		String InsertString = "INSERT INTO Event (Event_duration, Event_type, Reported, Reason, sessionID, Ev_interval, start_time)" +
								" Values (" + 
								duration + ", '" +
								TYPE_EVT + "', " + isReported() + ", '" +
								reason + "', " +
								sessionID + ", " +
								interval + ", " +
								start_time + ")";
		
		return InsertString;
	}
	
	public String genSYNCTripEventString(){
		String InsertString = "INSERT INTO Event (TripID, Event_duration, Event_type, Reported, Reason, sessionID, Ev_interval, start_time)" +
								" Values (" + 
								"Trip_ID, " + 
								duration + ", '" +
								TYPE_EVT + "', " + isReported() + ", '" +
								reason + "', " +
								sessionID + ", " +
								interval + ", " +
								start_time + ")";
		return InsertString;
	}
	
	public String genASYNCEventString(){
		double[] coords = getparseCoords();
		String InsertString = "INSERT INTO Event (TripID, Coordinate, Event_duration, Event_type, Reported, Reason, sessionID, Ev_interval)" +
								" Values (" + 
								"Trip_ID, '" + 
								coords[0] + "," + coords[1] + "', " +
								duration + ", '" +
								TYPE_EVT + "', " + isReported() + ", '" +
								reason + "', " +
								sessionID + ", " +
								interval + ")";
		return InsertString;
	}
}
