package dhs.oi;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import dhs.oi.protocol.*;

import probeIt.ui.workers.SwingWorker;

public class TruckLoader extends SwingWorker implements ActionListener
{

	private int poe, lane, session;
	private String prevTruck = null;
	private JPanel progressP;
	private JLabel lb;
	private JButton cancelButton = new JButton("Cancel");
	private boolean forcedEnd = false;
	private String truckInfo;

	public TruckLoader(JFrame ov, int p, int l, int s, String prev)
	{
		super();
		poe = p;
		lane = l;
		session = s;
		prevTruck = prev;
		truckInfo = null;
		init(ov);
	}

	private void init(JFrame ov)
	{
		//OfficerView.instance.center.setVisible(true);
		//OfficerView.instance.center.repaint();
		System.out.println("Waiting for Truck...");
		progressP = new JPanel();
		progressP.setLayout(new GridLayout(0,1));
		progressP.setVisible(true);
		lb = new JLabel("Waiting for next Truck...");
		//lb.setVisible(true);
		progressP.add(lb);
		//.setVisible(true);
		JProgressBar progress = new JProgressBar();
		progress.setPreferredSize(new Dimension(240, 20));
		progress.setMinimum(0);
		progress.setIndeterminate(true);
		progress.setValue(0);
		progress.setBounds(20, 35, 240, 20);
		progressP.add(progress);
		
		OfficerView.instance.center.add(progressP, BorderLayout.CENTER);
		//OfficerView.instance.repaint();
		System.out.println("REPAINT!!");
		//progressP.repaint();
	}

	public Object construct()
	{

		//get connection to OfficerSimProtocol Service.
		try
		{	
			OfficerSimProtocolService  service = new OfficerSimProtocolServiceLocator();

			// Now use the service to get a stub which implements the SDI.
			OfficerSimProtocol port = service.getOfficerSimProtocol();

			// set timeout for almost infinite
			org.apache.axis.client.Stub s = (org.apache.axis.client.Stub) port;
			s.setTimeout(999999999);

			truckInfo = port.getNextTruck(lane, poe, session, prevTruck);

			System.out.println("TRUCKINFO: "+ truckInfo);

		} catch (Exception e)
		{e.printStackTrace();}

		OfficerView.instance.setTruckInfo(truckInfo);
		
		progressP.setVisible(false);
		System.out.println("progressP removed!");
		
		return null;
	}

	/*public void finished()
	{
		//remove
		System.out.println("truck loader done");
		//OfficerView.instance.center.repaint();
	}*/

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(cancelButton))
		{
			this.interrupt();
			forcedEnd = true;
			//jd.dispose();
		}
		return;
	}
}