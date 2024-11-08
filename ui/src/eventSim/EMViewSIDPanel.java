package eventSim;

import java.awt.*;

import javax.swing.*;

public class EMViewSIDPanel extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton next, back;

	//JLabels
	JLabel desc;
	
	//Radio Button Group
	ButtonGroup RBG;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();
	int[] sessionIDs;

	public EMViewSIDPanel(){
		initGUI();
	}

	private void initGUI(){
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		next = new JButton(" Next >");
		back = new JButton("< Main Menu ");

		buttonP.add(back);
		buttonP.add(next);

		desc = new JLabel();
		desc.setText(tab + "Select the session ID of the events you wish to view.");
		Font descFont = desc.getFont();
		desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		workP.add(desc);
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
	
	private synchronized void GetSessionIDs(){

		try{
		//	RouteAccess loader = new RouteAccess();
		//	loader.start();
			GetSessionIDsActionWork();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void GetSessionIDsActionWork() throws InterruptedException{
		while (EventGen.instance.getRoute() == null)
			wait(999);

		RBG = new ButtonGroup();

		if(sessionIDs != null)
			for(int index = 0; index < sessionIDs.length; index++){

				JPanel tpane = new JPanel();
				tpane.setLayout(new BorderLayout());
				JRadioButton trb = new JRadioButton(sessionIDs[index]+"");
				trb.setMnemonic(index);
				RBG.add(trb);
				tpane.add(trb, BorderLayout.WEST);
				workP.add(tpane);
			}

	}

	private void genInformation(){
		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();
		EGC.setSessionID(sessionIDs[index]);
	}

	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();
		EGC.ChangePanel(10);
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
