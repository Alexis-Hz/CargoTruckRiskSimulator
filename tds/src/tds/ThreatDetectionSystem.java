package tds;
import tds.prolog.PrologInteraction;
public class ThreatDetectionSystem {
	
	private PrologInteraction prolog = new PrologInteraction();
	
	public String getRecomenation(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{	
		//assert vehicles
		assertVehicleFacts(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
		
		if(hasThreat(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime))
			return "red";
		else if(hasJustifiedThreat(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime))
			return "yellow";
		else
			return "green";
	}
	
	private boolean hasJustifiedThreat(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		//query if there is a justified threat
		boolean result = prolog.queryDatalogNoVar("hasJustifiedThreat("+vehicleID+").");
		return result;
		
	}
	
	private boolean hasThreat(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		//query if there is a threat
		boolean result = prolog.queryDatalogNoVar("hasThreat("+vehicleID+").");
		return result;
	}
	
	private boolean hasJustification(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		//query if the truck has a justification
		boolean result = prolog.queryDatalogNoVar("hasJustification("+vehicleID+").");
		return result;
		
	}
	
	private void assertVehicleFacts(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		//remove old facts
		prolog.abolish();
		
		//assert facts associated with vehicle creation clauses
		prolog.queryDatalog("assert(monitor("+vehicleID+")).");
		prolog.queryDatalog("assert(assignedRoute("+vehicleID+","+aRoute+")).");
		prolog.queryDatalog("assert(actualRoute("+vehicleID+","+eRoute+")).");
		prolog.queryDatalog("assert(expectedArrivalTime("+vehicleID+","+eArrivalTime+")).");
		prolog.queryDatalog("assert(actualArrivalTime("+vehicleID+","+aArrivalTime+")).");	
	}
	
	public String getPMLExplanation(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		String pmlURI;
		String name = "'tds-pml-" + tds.prolog.FileUtils.getRandomString()+"'";
		
		//assert vehicles
		assertVehicleFacts(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
		
		prolog.queryDatalog("asserta(proofName("+name+")).");
		
		if(hasThreat(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime))
			pmlURI = prolog.queryDatalog("why(hasThreat("+vehicleID+"), URI).");
		else if(hasJustifiedThreat(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime))
			pmlURI = prolog.queryDatalog("why(hasJustifiedThreat("+vehicleID+"), URI).");
		else
			pmlURI = prolog.queryDatalog("why(hasNoThreat("+vehicleID+"), URI).");
		
		return pmlURI;
	}

	
	public static void main(String[] args)
	{
		ThreatDetectionSystem tds = new ThreatDetectionSystem();
		String recomendation = tds.getRecomenation("v2", "r2", "r1", "14", "14");
		String pmlURI = tds.getPMLExplanation("v2", "r2", "r1", "14", "14");
		
		System.out.println("Recommenation: " + recomendation + ", PML: " + pmlURI);
	}
}
