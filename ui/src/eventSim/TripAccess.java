package eventSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import eventSim.Protocol.*;
import probeIt.ui.workers.SwingWorker;


public class TripAccess extends SwingWorker implements ActionListener {

	String[] trip_orig, trip_dest;
	String[][] trip_info;

	public TripAccess()
	{
		super();
	}

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

			trip_info = port.getTrip();

			while(trip_info == null)
				for(int i=0; i<1000; i++);

			convert();
			EventGen.instance.setOriginArray(trip_orig);
			EventGen.instance.setDestinationArray(trip_dest);

		} catch (Exception e)
		{e.printStackTrace();}

		return null;
	}

	private void convert(){

		int index = 0;
		while(trip_info[0][index] != null )
			index++;

		trip_orig = new String[index];
		trip_dest = new String[index];

		for (index = 0; index < trip_orig.length; index++){
				trip_orig[index] = trip_info[0][index];
				trip_dest[index] = trip_info[1][index];
		}
	}


	public void actionPerformed(ActionEvent e) {

	}

}
