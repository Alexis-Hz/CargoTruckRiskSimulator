package dhs.oi;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
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

public class LaneLoader extends SwingWorker implements ActionListener
{

	private int poe, lane, session;
	private String prevTruck = null;
	private JPanel progressP;
	private JLabel lb;
	private JButton cancelButton = new JButton("Cancel");
	private boolean forcedEnd = false;
	private int[] laneInfo;

	public LaneLoader(JFrame ov)
	{
		super();
		laneInfo = new int[3];
		init(ov);
	}

	private void init(JFrame ov)
	{
		System.out.println("Getting Lane...");

		
		//progressP = new JPanel();

		/*progressP.setLayout(new GridLayout(0,1));
		
		lb = new JLabel("No Lane Available."); //Waiting for next available Lane...");
		lb.setVisible(true);
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
		OfficerView.instance.center.repaint();
		progressP.setVisible(true);*/
		//OfficerView.instance.probePanel.setLayout(new BorderLayout());
		
		//cancelButton = new JButton("Cancel");

		//cancelButton.addActionListener(this);

		//OfficerView.instance.probePanel.add(cancelButton);
	}

	public Object construct()
	{

		//get connection to OfficerSimProtocol Service.
		try
		{
			OfficerSimProtocolService  service = new OfficerSimProtocolServiceLocator();

			// Now use the service to get a stub which implements the SDI.
			OfficerSimProtocol port = service.getOfficerSimProtocol();

			// set timeout for allmost infinite
			org.apache.axis.client.Stub s = (org.apache.axis.client.Stub) port;
			s.setTimeout(999999999);

			laneInfo = port.getLane();

		} catch (Exception e)
		{e.printStackTrace();}

		System.out.println("Setting Lane Info...");
		OfficerView.instance.setLaneInfo(laneInfo);
		System.out.println("Finished Constructing Lane.");
		
		return null;
	}

	/*public void finished()
	{
		//remove
		System.out.println("Lane loader done");
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