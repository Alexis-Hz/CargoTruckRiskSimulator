package eventSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eventSim.Protocol.EventGenProtocol;
import eventSim.Protocol.EventGenProtocolService;
import eventSim.Protocol.EventGenProtocolServiceLocator;
import probeIt.ui.workers.SwingWorker;

public class EventManager extends SwingWorker implements ActionListener {

	private String query, orig, dest;
	
	public void setQuery(String str){query = str;}
	public void setDest(String str){dest = str;}
	public void setOrig(String str){orig = str;}
	
	@Override
	public Object construct() {
		try
		{
			EventGenProtocolService  service = new EventGenProtocolServiceLocator();

			// Now use the service to get a stub which implements the SDI.
			EventGenProtocol port = service.getEventGenProtocol();

			// set timeout for almost infinite
			org.apache.axis.client.Stub s = (org.apache.axis.client.Stub) port;
			s.setTimeout(999999999);
			
			System.out.println(orig + " .. " + dest);
			boolean insert = port.insertEvent(query, orig, dest);
			
			while(!insert){}
			
			System.out.println("doneevfnan");

		} catch (Exception e)
		{e.printStackTrace();}

		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
