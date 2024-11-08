package model.tds;

import model.tds.threatDetectionService.ThreatDetectionSystem;
import model.tds.threatDetectionService.ThreatDetectionSystemService;
import model.tds.threatDetectionService.ThreatDetectionSystemServiceLocator;
/**
 * Creates and manages calls to the threat detection service. Gets recommendations (Lights) and the uri for their justifications
 * @author Tempest
 *
 */
public class TdsCall {
	
	ThreatDetectionSystem port;
	/**
	 * Tries to create a connection with the service, if it fails it prints the Stacktrace for the errors, if it succeeds it initializes tthe TDSCall
	 */
	public TdsCall()
	{
		port=null;
		try
		{
			// Get a handle to a GrdContourService
			ThreatDetectionSystemService service = new ThreatDetectionSystemServiceLocator();

			// Now use the service to get a stub which implements the SDI.
			port = service.getThreatDetectionService();

			// set timeout for allmost infinite
			org.apache.axis.client.Stub s = (org.apache.axis.client.Stub) port;
			s.setTimeout(999999999);
			
		} catch (Exception e)
		{e.printStackTrace();}
	}
	/**
	 * fetches the color of the light given a trip id
	 * @param vehicleID id for the vehicle in question
	 * @param eRoute Expected route
	 * @param aRoute Actual route
	 * @param eArrivalTime expected Arrival time
	 * @param aArrivalTime Actual Arrival time
	 * @return the light obtained by the threat detection system Red, Green or Yellow
	 */
	public String getLight(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		try
		{
			//fetch the color of the light
			return port.getRecomenation(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}
	/**
	 * returns the uri of the pml for a given trip Id
	 * @param vehicleID id for the vehicle in question
	 * @param eRoute Expected route
	 * @param aRoute Actual route
	 * @param eArrivalTime expected Arrival time
	 * @param aArrivalTime Actual Arrival time
	 * @return The uri to the PML justification for the given trip
	 */
	public String getUri(String vehicleID, String eRoute, String aRoute, String eArrivalTime, String aArrivalTime)
	{
		try
		{
			//fetch the pml explaining the decision
			return port.getPMLExplanation(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}
}
