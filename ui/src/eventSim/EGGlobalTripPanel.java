package eventSim;

import javax.swing.*;

import java.awt.*;

public class EGGlobalTripPanel extends Panel{

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton next, back;

	//Radio Button Group
	ButtonGroup RBG;

	//JLabels
	JLabel desc, globalDesc, tripDesc;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	public EGGlobalTripPanel(){
		initGUI();
	}

	public void initGUI(){

		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		next = new JButton(" Next >");
		back = new JButton("< Back ");

		String strip = "Select 'Trip' to have the Event take effect only on one trip chosen.";
		String sglob = "Select 'Global' to have the Event take effect on all currently on-route trips.";

		desc = new JLabel();
		desc.setText(tab + "Choose the scope of the Event.");

		Font descFont = desc.getFont();
		desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		RBG = new ButtonGroup();
		JRadioButton global = new JRadioButton(sglob);
		global.setMnemonic(1);

		JRadioButton trip = new JRadioButton(strip);
		trip.setMnemonic(0);

		global.setSelected(true);
		RBG.add(global);
		RBG.add(trip);

		workP.add(desc);
		workP.add(new JLabel(" "));
		workP.add(global);
		workP.add(trip);
		workP.add(new JLabel(" "));

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

	private void genInformation(){
		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();
		if(index == 1)
			EventGen.instance.appendEvtType("GLOBAL");
		else if(index == 0)
			EventGen.instance.appendEvtType("TRIP");
	}

	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();
		String type = EventGen.instance.getEvtType();
		if(type.startsWith("ASYNC") || type.endsWith("TRIP"))
			EGC.ChangePanel(3);
		else
			EGC.ChangePanel(6);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(1);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}

}
