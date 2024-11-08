package eventSim;

import java.awt.*;
import javax.swing.*;

public class EGSelectTripPanel extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton next, back;

	//JLabels
	JLabel Desc;


	//Radio Button Group
	ButtonGroup RBG;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	String[] labels;
	private String[] trip_orig, trip_dest;

	public EGSelectTripPanel(){
		if(EventGen.instance.getCoor() != null)
			EventGen.instance.setCoor(null);
		if(EventGen.instance.getOriginPoint() != null){
			EventGen.instance.setOriginPoint(null);
			EventGen.instance.setDestPoint(null);
		}
		initGUI();
	}

	public void initGUI(){

		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		Desc = new JLabel();
		Desc.setText(tab + "Select the trip of the Event.");
		Font descFont = Desc.getFont();
		Desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		next = new JButton(" Next >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(next);

		JPanel tpane = new JPanel();
		tpane.setLayout(new BorderLayout());
		tpane.add(Desc, BorderLayout.WEST);
		workP.add(tpane);

		workP.add(new JLabel(""));
		workP.add(new JLabel(""));
		getTripAction();

		next.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextAction(evt);
			}
		});

		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});

	}

	private void getTripAction()
	{
		try{

			TripAccess loader = new TripAccess();
			loader.start();
			getTripActionWork();

		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private synchronized void getTripActionWork() throws InterruptedException{

		while (EventGen.instance.getOriginArray() == null && EventGen.instance.getDestinationArray() == null)
			wait(999);

		trip_orig = EventGen.instance.getOriginArray();
		trip_dest = EventGen.instance.getDestinationArray();

		genTripLabels();
		RBG = new ButtonGroup();

		if(trip_orig != null && trip_dest != null)
			for(int index = 0; index < labels.length; index++){

				JPanel tpane = new JPanel();
				tpane.setLayout(new BorderLayout());
				JRadioButton trb = new JRadioButton(labels[index]);
				trb.setMnemonic(index);
				RBG.add(trb);
				tpane.add(trb, BorderLayout.WEST);
				workP.add(tpane);
			}
	}

	private void genTripLabels(){
		labels = new String[trip_orig.length];
		for(int index = 0; index < trip_orig.length; index++){
			labels[index] = tab + "From  " + trip_orig[index] + "  to  " + trip_dest[index] + ".";
		}
	}

	private void genInformation(){
		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();
		EGC.setStartAndEndPoints(trip_orig[index], trip_dest[index]);

		EventGen.instance.setOriginPoint(trip_orig[index]);
		EventGen.instance.setDestPoint(trip_dest[index]);
	}

	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();
		String type = EventGen.instance.getEvtType();
		if(type.startsWith("ASYNC"))
			EGC.ChangePanel(4);
		else{
			if(type.contains("TRIP"))
				EGC.ChangePanel(6);
			else if(type.contains("CANCEL") || type.contains("BLOCK"))
				EGC.ChangePanel(4);
			else 
				EGC.ChangePanel(6);
		}
	}
	
	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(2);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
}
