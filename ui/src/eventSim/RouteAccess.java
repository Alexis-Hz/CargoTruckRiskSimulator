package eventSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eventSim.Protocol.EventGenProtocol;
import eventSim.Protocol.EventGenProtocolService;
import eventSim.Protocol.EventGenProtocolServiceLocator;
import probeIt.ui.workers.SwingWorker;

public class RouteAccess extends SwingWorker implements ActionListener {

	String RouteCoor, from, to;
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
			
			RouteCoor = port.getRoute(from, to);

			while(RouteCoor == null){
				for(int i=0; i<1000; i++);
			}
			
			EventGen.instance.setRoute(RouteCoor);

		} catch (Exception e)
		{e.printStackTrace();}

		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
