package eventSim;

import java.awt.*;

import javax.swing.*;

public class EGTimeTypePanel extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton next, back;

	//JLabels
	JLabel LDesc, LstartTime;

	//JTextFields
	JTextField TFstartTime;

	//RadioButtons
	JRadioButton deployD, cancelD, blockD, slowD, stopD;
	JRadioButton now, set;

	//Radio Button Group
	ButtonGroup RBG, RBGTime;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	public EGTimeTypePanel(){
		String type = EventGen.instance.getEvtType();
		String[] atype = type.split(" - ");
		if(atype.length > 2)
			EventGen.instance.setEvtType(atype[0] + " - " + atype[1]);
		initGUI();
	}

	private void initGUI(){
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		LDesc = new JLabel();
		LDesc.setText(tab + "Select the type of Event.");
		Font descFont = LDesc.getFont();
		LDesc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		LstartTime = new JLabel();
		LstartTime.setText(tab + "Set the start time of the Event.");
		LstartTime.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		TFstartTime = new JTextField();
		TFstartTime.setColumns(15);
		JPanel tempP = new JPanel(new BorderLayout());
		tempP.add(TFstartTime, BorderLayout.WEST);
		tempP.add(new JLabel(" "), BorderLayout.SOUTH);

		next = new JButton(" Next >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(next);

		deployD = new JRadioButton("Deploy Trip");
		deployD.setMnemonic(0);
		deployD.setSelected(true);

		cancelD = new JRadioButton("Cancel Trip");
		cancelD.setMnemonic(1);

		slowD = new JRadioButton("Slow Down Segment");
		slowD.setMnemonic(2);

		stopD = new JRadioButton("Stop Trip");
		stopD.setMnemonic(3);

		blockD = new JRadioButton("Block Route");
		blockD.setMnemonic(4);

		deployD.setToolTipText("Deploy Trip at given time.");
		cancelD.setToolTipText("Re-route the trip back to the Maquila.");
		slowD.setToolTipText("Slow down a segment where the truck is located.");
		stopD.setToolTipText("Stop a trip at the given location.");
		blockD.setToolTipText("Block route, making the trip to re-route.");

		RBG = new ButtonGroup();
		RBG.add(deployD);
		RBG.add(cancelD);
		RBG.add(slowD);
		RBG.add(stopD);
		RBG.add(blockD);

		now = new JRadioButton("Start Immediatly.");
		now.setMnemonic(10);
		now.setSelected(true);
		set = new JRadioButton("Set start time to: ");
		set.setMnemonic(11);

		RBGTime = new ButtonGroup();
		RBGTime.add(now);
		RBGTime.add(set);

		workP.add(LDesc);
		workP.add(new JLabel(" "));
		workP.add(deployD);
		workP.add(cancelD);
		workP.add(slowD);
		workP.add(stopD);
		workP.add(blockD);

		workP.add(new JLabel(" "));
		workP.add(new JLabel(" "));
		workP.add(LstartTime);
		workP.add(new JLabel(" "));
		workP.add(now);
		workP.add(set);
		workP.add(tempP);
		for(int index = 0; index < 15; index++)
			workP.add(new JLabel(" "));

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

	private void genInformation(){
		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();

		switch(index){
		case 0:
			EventGen.instance.appendEvtType("DEPLOY");
			break;
		case 1:
			EventGen.instance.appendEvtType("CANCEL");
			break;
		case 2:
			EventGen.instance.appendEvtType("SLOW");
			break;
		case 3:
			EventGen.instance.appendEvtType("STOP");
			break;
		case 4:
			EventGen.instance.appendEvtType("BLOCK");
			break;
		default: break;
		}

		EventGen.instance.appendEvtType("&");

		selectedButton = RBGTime.getSelection();
		index = selectedButton.getMnemonic();
		if(index == 10)
			EventGen.instance.setStart_time(-1);
		else{
			String ST = TFstartTime.getText();
			EventGen.instance.setStart_time(ST);
		}

	}

	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();

		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();

		if(EventGen.instance.getOriginPoint() != null && EventGen.instance.getDestPoint() != null)
			EGC.ChangePanel(7);
		else{
			if(index == 1 || index == 4)
				EGC.ChangePanel(3);
			else		
				EGC.ChangePanel(7);
		}
	}

	public void backAction(java.awt.event.ActionEvent evt){
		String type = EventGen.instance.getEvtType();
		if(type.endsWith("TRIP"))
			EGC.ChangePanel(3);
		else
			EGC.ChangePanel(2);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
}
