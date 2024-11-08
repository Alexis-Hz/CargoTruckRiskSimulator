package eventSim;

public class EGController {
	
	private final int MAIN = 0;					//Main Panel
	private final int ADD_TIME_LOC = 1;
	private final int ADD_GORT = 2;				//Add, choose global or trip
	private final int ADD_SEL_TRIP = 3;			//Add, select trip
	private final int ADD_SEL_COOR = 4;			//Add, select Coordinate
	private final int ADD_LOC_SEL_TYPE = 5;		//Add, select type of event for location
	private final int ADD_TIME_SEL_TYPE = 6;	//Add, select type of event for time
	private final int ADD_SET_INFO = 7;			//Add, set all required info
	private final int ADD_CONFIRM = 8;			//Add, confirm event
	
	private final int VIEW_EVT_SESSION = 9;		//view events, select session ID
	private final int VIEW_EVT_INFO = 10;		//view events, view events per sessionID
	
	public String trip_startPoint, trip_endPoint;
	public int sessionID = -1;
	
	public EGController(){}
	
	public void NotifyGUI(){
		EventGenGUI.instance.update();
	}
	
	public void ChangePanel(int P){
		switch(P){
		case MAIN:
			eventGenMainPanel EGMP = new eventGenMainPanel();
			EventGenGUI.instance.updatePanel(EGMP);
			break;
		case ADD_TIME_LOC:
			EGTimeLocPanel EGTLP = new EGTimeLocPanel();
			EventGenGUI.instance.updatePanel(EGTLP);
			break;
		case ADD_GORT:
			EGGlobalTripPanel EGGTP = new EGGlobalTripPanel();
			EventGenGUI.instance.updatePanel(EGGTP);
			break;
		case ADD_SEL_TRIP:
			EGSelectTripPanel EGSTP = new EGSelectTripPanel();
			EventGenGUI.instance.updatePanel(EGSTP);
			break;
		case ADD_SEL_COOR:
			EGSelectCoordPanel EGSCP = new EGSelectCoordPanel(trip_startPoint, trip_endPoint);
			EventGenGUI.instance.updatePanel(EGSCP);
			break;
		case ADD_LOC_SEL_TYPE:
			EGLocTypePanel EGLTP = new EGLocTypePanel();
			EventGenGUI.instance.updatePanel(EGLTP);
			break;
		case ADD_TIME_SEL_TYPE:
			EGTimeTypePanel EGTTP = new EGTimeTypePanel();
			EventGenGUI.instance.updatePanel(EGTTP);
			break;
		case ADD_SET_INFO:
			EGReqPanel EGRP = new EGReqPanel();
			EventGenGUI.instance.updatePanel(EGRP);
			break;
		case ADD_CONFIRM:
			EGConfirm EGC = new EGConfirm();
			EventGenGUI.instance.updatePanel(EGC);
			break;
		case VIEW_EVT_SESSION:
			EMViewSIDPanel EMVSIDP = new EMViewSIDPanel();
			EventGenGUI.instance.updatePanel(EMVSIDP);
			break;
		case VIEW_EVT_INFO:
			EMViewInfoPanel EMVIP = new EMViewInfoPanel(sessionID);
			EventGenGUI.instance.updatePanel(EMVIP);
			break;
			
		default: break;
		}
	}
	
	public void setStartAndEndPoints(String start, String end){
		trip_startPoint = start;
		trip_endPoint = end;
	}
	
	public void setSessionID(int SID){
		sessionID = SID;
	}

}
