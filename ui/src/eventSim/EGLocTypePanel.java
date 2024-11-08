package eventSim;

import java.awt.*;

import javax.swing.*;

public class EGLocTypePanel extends Panel {

	//JPanels
	JPanel workP, buttonP;
	
	//JButtons
	JButton next, back;
	
	//JLabels
	JLabel Desc;
	
	//RadioButtons
	JRadioButton cancelD, blockD, slowD, stopD;
	
	//Radio Button Group
	ButtonGroup RBG;
	
	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();
	
	public EGLocTypePanel(){
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

		Desc = new JLabel();
		Desc.setText(tab + "Select the type of Event.");
		Font descFont = Desc.getFont();
		Desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
				
		next = new JButton(" Next >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(next);
		
		cancelD = new JRadioButton("Cancel Trip");
		cancelD.setMnemonic(1);
		cancelD.setSelected(true);
		
		slowD = new JRadioButton("Slow Down Segment");
		slowD.setMnemonic(2);
		
		stopD = new JRadioButton("Stop Trip");
		stopD.setMnemonic(3);
		
		blockD = new JRadioButton("Block Route");
		blockD.setMnemonic(4);
		
		cancelD.setToolTipText("Re-route the trip back to the Maquila.");
		slowD.setToolTipText("Slow down a segment where the truck is located.");
		stopD.setToolTipText("Stop a trip at the given location.");
		blockD.setToolTipText("Block route, making the trip to re-route.");
		
		RBG = new ButtonGroup();
		RBG.add(cancelD);
		RBG.add(slowD);
		RBG.add(stopD);
		RBG.add(blockD);
		
		workP.add(Desc);
		workP.add(new JLabel(""));
		workP.add(cancelD);
		workP.add(slowD);
		workP.add(stopD);
		workP.add(blockD);
		
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
	}
	
	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();
		EGC.ChangePanel(7);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(3);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
}
