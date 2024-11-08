package eventSim;

import java.awt.*;
import javax.swing.*;

public class EGTimeLocPanel extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JLables
	JLabel SessionIDDesc, TypeCond;

	//Radio Button Group
	ButtonGroup SessionRBG, TypeRBG;

	//JButtons
	JButton next, back;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	public EGTimeLocPanel(){
		initGUI();
	}

	public void initGUI(){

		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		SessionIDDesc = new JLabel();
		SessionIDDesc.setText(tab + "Set the Cargo Trust Simulator Session ID to which the event will affect.");

		TypeCond = new JLabel();
		TypeCond.setText(tab + "Choose the type of condition of the Event");

		Font descFont = SessionIDDesc.getFont();
		SessionIDDesc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
		TypeCond.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		//SessionID Radio Button Group
		String snext = "Next Session.";
		String sCurr = "Current Session. (currently not supported)";

		SessionRBG = new ButtonGroup();
		JRadioButton nextSession = new JRadioButton(snext);
		nextSession.setMnemonic(-1);

		JRadioButton CurrSession = new JRadioButton(sCurr);
		CurrSession.setMnemonic(0);

		nextSession.setSelected(true);
		SessionRBG.add(nextSession);
		SessionRBG.add(CurrSession);

		//Type Radio Button Group
		String sSync = "Time Event: At time T, trigger Event.";
		String sAsync = "Location Event: For Trip R at Location (X,Y).";

		TypeRBG = new ButtonGroup();
		JRadioButton sync = new JRadioButton(sSync);
		sync.setMnemonic(1);

		JRadioButton async = new JRadioButton(sAsync);
		async.setMnemonic(0);

		sync.setSelected(true);
		TypeRBG.add(sync);
		TypeRBG.add(async);

		workP.add(new JLabel(" "));
		workP.add(SessionIDDesc);
		workP.add(new JLabel(" "));
		workP.add(nextSession);
		workP.add(CurrSession);

		workP.add(new JLabel(" "));
		workP.add(TypeCond);
		workP.add(new JLabel(" "));
		workP.add(sync);
		workP.add(async);

		next = new JButton(" Next >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(next);

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

	private void getInformation(){
		ButtonModel selectedButton = SessionRBG.getSelection();
		int index = selectedButton.getMnemonic();
		EventGen.instance.setSessionID(index);

		selectedButton = TypeRBG.getSelection();
		index = selectedButton.getMnemonic();
		if(index == 1)
			EventGen.instance.setEvtType("SYNC");
		else if(index == 0)
			EventGen.instance.setEvtType("ASYNC");
	}

	public void nextAction(java.awt.event.ActionEvent evt){
		getInformation();
		EGC.ChangePanel(2);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(0);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}

}
