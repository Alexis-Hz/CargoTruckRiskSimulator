package dhs.oi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import javax.swing.*;

import dhs.oi.protocol.*;

import java.net.*;

public class OfficerView extends javax.swing.JFrame
{
	public static OfficerView instance;

	JPanel contentPane;
	JPanel info;
	JPanel trafficLightImage;
	ImageIcon nolight, green, yellow, red;
	JLabel image;
	JPanel toolbar;
	JPanel center;
	JComponent probePanel;
	JPanel laneStatusP;
	JLabel laneStatusL;


	JButton start;
	JButton inspect;
	JButton dontInspect;

	JPanel iPanel;

	JLabel waitingOnTruckL;
	JLabel idL;
	JLabel driverL;
	JLabel driverIDL;
	JLabel expectedL;
	JLabel arrivedL;
	JLabel maquilaL;

	JTextField idF;
	JTextField driverF;
	JTextField driverIDF;
	JTextField expectedF;
	JTextField arrivedF;
	JTextField maquilaF;

	JPanel infoPicPanel;


	JPanel officerInfo;
	JPanel poeP, laneP, sessionP;
	JLabel poeL;
	JTextField poeF;
	JLabel laneL;
	JTextField laneF;
	JLabel sessionL;
	JTextField sessionF;

	JPanel queueInfo;
	JLabel queueLenghtL;
	JTextField queueLenghtF;
	JLabel waitingTimeL;
	JTextField waitingTimeF;
	JPanel queueLenghtP;
	JPanel waitingTimeP;

	JPanel right;
	JPanel rightButtons;
	JButton whyButton;
	JLabel fakeMap;
	ImageIcon map;

	String tidMock;
	String nameMock;
	int eTOAMock;
	int aTOAMock;
	boolean mock = false;
	boolean noLane = false;
	JDialog jd;

	int poe, lane, session;
	String prevTruck = null;
	String pmlURI = null;
	OfficerSimProtocol  port;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				OfficerView inst = new OfficerView("#######", "#######", 1000,1000);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public OfficerView(String tid, String dName, int expTOA, int actTOA) {
		super();
		instance = this;
		tidMock = tid;
		nameMock = dName;
		eTOAMock = expTOA;
		aTOAMock = actTOA;
		initGUI();
	}

	private void initGUI() 
	{	
		this.setTitle("Officer View");
		//save images.

		JarResourceLoader jarLoader = new JarResourceLoader();
		green = jarLoader.getImageIcon("green_light_full_color.JPG");
		yellow = jarLoader.getImageIcon("yellow_light_full_color.JPG");
		red = jarLoader.getImageIcon("red_light_full_color.JPG");
		nolight = jarLoader.getImageIcon("no_light_full_color.JPG");
		
		try 
		{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(750, 520);

			//setting content pane
			contentPane = new JPanel(new BorderLayout());
			this.setContentPane(contentPane);
			//contentPane.setBackground(Color.green);

			//setting toolbar 
			toolbar = new JPanel();
			toolbar.setLayout(new FlowLayout());
			toolbar.setBackground(Color.black);
			start = new JButton("Request Lane");
			start.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					startAction(evt);
				}
			});

			toolbar.add(start);
			contentPane.add(toolbar, BorderLayout.PAGE_START);
			//Request Lane Button
			/*start = new JButton("Request Lane");
			start.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	startAction(evt);
	            }
	        });
			//WaitingLabel 
			waitingOnTruckL = new JLabel("Waiting for Next Truck...");
			waitingOnTruckL.setBackground(Color.yellow);
			info.add(start);
			info.add(waitingOnTruckL);
			waitingOnTruckL.setVisible(false);*/

			//Left Panel
			iPanel = new JPanel();
			iPanel.setLayout(new BorderLayout());

			//Truck Info
			info = new JPanel();
			info.setLayout(new GridLayout(8, 2));
			
			infoPicPanel = new JPanel();
			infoPicPanel.setLayout(new BorderLayout());
			
			
			//this code is save for adding the pciture of either the license plates of the truck
			//or the picture of the driver.
			JTextArea temp = new JTextArea("Picture");
			temp.setSize(150, 500);
			temp.setAlignmentX(50);
			temp.setAlignmentY(50);
			infoPicPanel.add(temp, BorderLayout.CENTER);
			infoPicPanel.add(new JLabel("    "), BorderLayout.WEST);
			infoPicPanel.add(new JLabel("    "), BorderLayout.EAST);
//			infoPicPanel.setLocation(10, 10);
			infoPicPanel.setSize(200, 200);
			
			
			
			
			//setting info labels and text fields
			info.add(new JLabel (""));
			info.add(new JLabel (""));
			
			idL = new JLabel(" Truck Id: ");
			idF = new JTextField("--------");
			info.add(idL);
			info.add(idF);
		
			driverL = new JLabel(" Driver Name: ");
			driverF = new JTextField("--------");
			info.add(driverL);
			info.add(driverF);

			driverIDL = new JLabel(" Driver ID #: ");
			driverIDF = new JTextField("--------");
			info.add(driverIDL);
			info.add(driverIDF);
			
			maquilaL = new JLabel(" Maquila: ");
			maquilaF = new JTextField("--------");
			info.add(maquilaL);
			info.add(maquilaF);

			expectedL = new JLabel(" Expected TOA: ");
			expectedF = new JTextField("--------");
			info.add(expectedL);
			info.add(expectedF);

			arrivedL = new JLabel(" Actual TOA: ");
			arrivedF = new JTextField("--------");
			info.add(arrivedL);
			info.add(arrivedF);
			
			info.add(new JLabel (""));
			info.add(new JLabel (""));

			

			//Officer Information.
			officerInfo = new JPanel();
			officerInfo.setLayout(new GridLayout(5,2));

			poeL = new JLabel(" POE ID: ");
			poeF = new JTextField("--------");

			laneL = new JLabel(" Lane #: ");
			laneF = new JTextField("--------");

			sessionL = new JLabel(" Session ID: ");
			sessionF = new JTextField("--------");

			officerInfo.add(new JLabel (""));
			officerInfo.add(new JLabel (""));
			officerInfo.add(poeL);
			officerInfo.add(poeF);
			officerInfo.add(laneL);
			officerInfo.add(laneF);
			officerInfo.add(sessionL);
			officerInfo.add(sessionF);

			
			iPanel.add(info, BorderLayout.PAGE_START);
			iPanel.add(infoPicPanel, BorderLayout.CENTER);
			iPanel.add(officerInfo, BorderLayout.PAGE_END);
			
			//Truck Queue Information.
			/*queueInfo = new JPanel();
			queueInfo.setLayout(new BoxLayout(queueInfo, BoxLayout.PAGE_AXIS));
			queueInfo.setBackground(Color.CYAN);

			queueLenghtP = new JPanel();
			queueLenghtL = new JLabel("Cars in line");
			queueLenghtF = new JTextField("##");
			queueLenghtP.add(queueLenghtL);
			queueLenghtP.add(queueLenghtF);
			queueInfo.add(queueLenghtP);

			waitingTimeP = new JPanel();
			waitingTimeL = new JLabel("Waiting time");
			waitingTimeF = new JTextField("##:##");
			waitingTimeP.add(waitingTimeL);			
			waitingTimeP.add(waitingTimeF);	
			queueInfo.add(waitingTimeP);			

			iPanel.add(queueInfo, BorderLayout.PAGE_END);*/

			contentPane.add(iPanel, BorderLayout.LINE_START);

			right =  new JPanel();
			right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));


			image = new JLabel(nolight);
			right.add(image);


			rightButtons = new JPanel();
			rightButtons.setLayout(new BoxLayout(rightButtons, BoxLayout.Y_AXIS));
			whyButton = new JButton("Generate Explanation");
			whyButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					whyAction(evt);
				}
			});

			inspect = new JButton("Inspect");
			dontInspect =  new JButton("Do Not Inspect");

			inspect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					resetAction(evt);
				}
			});
			dontInspect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					resetAction(evt);
				}
			});


			rightButtons.add(whyButton);
			rightButtons.add(inspect);
			rightButtons.add(dontInspect);
			inspect.setEnabled(false);
			dontInspect.setEnabled(false);
			whyButton.setEnabled(false);
			right.add(rightButtons);

			contentPane.add(right, BorderLayout.LINE_END);
			
			center = new JPanel();
			
			//adding probe it holder
			probePanel =  new JTabbedPane();
			probePanel.setBackground(Color.GRAY);
			probePanel.setVisible(false);
			
			if(!mock)
				contentPane.add(probePanel, BorderLayout.CENTER);
			else
			{
				map = new ImageIcon("ui/images/not_existent.JPG");
				fakeMap = new JLabel(map);
				fakeMap.setBackground(Color.GRAY);
				contentPane.add(fakeMap, BorderLayout.CENTER);
			}
			
			center.setBorder(BorderFactory.createLineBorder(Color.black));
			contentPane.add(center, BorderLayout.CENTER);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void whyAction(java.awt.event.ActionEvent evt){   
		center.setVisible(false);
		if(mock)
		{
			map = new ImageIcon("ui/images/routeMap.JPG");
			fakeMap.setIcon(map);
		}
		else 
		{
			contentPane.remove(probePanel);
			probePanel = Manager.getProbeItPane(this, pmlURI);
			contentPane.add(probePanel, BorderLayout.CENTER);
		}
		
		//repaint();
	}

	/** Places the "No Lane Available!" message on the center panel. (called by setLaneInfo)*/
	private void noLaneAvail()
	{
		noLane=true;
		laneStatusL = new JLabel("No Lane Available!");
		laneStatusL.setVisible(true);
		center.add(laneStatusL, BorderLayout.CENTER);
		
		//repaint();
	}
	
	/**Called by LaneLoader (in a new thread).*/
	public void setLaneInfo(int[] laneInfo)
	{
		lane = laneInfo[0];
		
		if((lane == 0 || lane == -1))
		{
			if(!noLane)
				noLaneAvail();
			System.out.println("No Lane Available.");
		}

		else
		{
			System.out.println("Lane found!");
			if(noLane == true)
			{
				center.remove(laneStatusL);
				//repaint();
				noLane = false;
			}
			start.setVisible(false);
			
			poe = laneInfo[1];
			session = laneInfo[2];

			laneF.setText(""+lane);
			poeF.setText(""+poe);
			sessionF.setText(""+session);

			nextTruck();
		}
	}


	private void startAction(java.awt.event.ActionEvent evt)
	{
		//get connection to OfficerSimProtocol Service.
		LaneLoader loader = new LaneLoader(this);
		loader.start();
		System.out.println("startAction done!");
		
		//repaint();
	}

	private void resetAction(java.awt.event.ActionEvent evt)
	{
		image.setIcon(nolight);

		inspect.setEnabled(false);
		dontInspect.setEnabled(false);
		whyButton.setEnabled(false);
		probePanel.setVisible(false);
		center.setVisible(true);
		nextTruck();
		
		//center.repaint();
	}

	private void nextTruck()
	{
		idF.setText("--------");
		driverF.setText("--------");
		driverIDF.setText("--------");
		expectedF.setText("--------");
		arrivedF.setText("--------");
		
		//waiting...
		TruckLoader loader = new TruckLoader(this, poe, lane, session, prevTruck);
		loader.start();
		
	}

	public void setTruckInfo(String truckInfo)
	{
		String rec, truckID, name, License, machilaID;

		if(truckInfo!=null)//if truck info was retrieved successfully...
		{
			truckID = null;
			name = null;
			License = null;
			rec = "nolight";
			
			try
			{
				//separate truckInfo into corresponding values.
				int parseKey1 = truckInfo.indexOf(",");
				rec = truckInfo.substring(0, parseKey1);

				String temp = truckInfo.substring(parseKey1+1);
				int parseKey2 = temp.indexOf(",");
				pmlURI = temp.substring(0, parseKey2);
				truckInfo = temp.substring(parseKey2 +1);

				prevTruck = truckInfo;//save truck info.

				//parse through truckInfo to get ID, name, etc.
				parseKey1 = truckInfo.indexOf("<");
				truckID = truckInfo.substring(0, parseKey1 -1);

				parseKey2 = truckInfo.indexOf("-");
				name = truckInfo.substring(parseKey1 +1, parseKey2);

				parseKey1 = truckInfo.indexOf(">");
				License = truckInfo.substring(parseKey2 +1, parseKey1);

				machilaID = truckInfo.substring(parseKey1);
			}catch(StringIndexOutOfBoundsException e)
			{
				System.out.println("truckInfo from database contained wrong format.");
				e.printStackTrace();
			}catch(Exception e)
			{ e.printStackTrace();}

			//No longer waiting
			//stopWaiting();

			idF.setText(truckID);
			driverF.setText(name);
			driverIDF.setText(License);
			expectedF.setText("--------");
			arrivedF.setText("--------");
			//queueLenghtF.setText("0");

			inspect.setEnabled(true);
			dontInspect.setEnabled(true);
			whyButton.setEnabled(true);


			if("green".equals(rec))
			{
				System.out.println("green!!");
				image.setIcon(green);
			}
			else if("red".equals(rec))
			{
				System.out.println("red!!");
				image.setIcon(red);
			}
			else if("yellow".equals(rec))
			{
				System.out.println("yellow!!");
				image.setIcon(yellow);
			}
			else
				System.out.println("Does not include a recommendation.");
		}
	}

}

