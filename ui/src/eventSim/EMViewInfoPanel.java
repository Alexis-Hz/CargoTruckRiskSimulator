package eventSim;

import java.awt.*;

import javax.swing.*;

public class EMViewInfoPanel extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton main, back;

	//JLabels
	JLabel desc;

	//Radio Button Group
	ButtonGroup RBG;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();
	int sessionID;

	public EMViewInfoPanel(int SID){
		sessionID = SID;
		initGUI();
	}

	private void initGUI(){
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		main = new JButton(" Main Menu >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(main);

		desc = new JLabel();
		if(sessionID == -1)
			desc.setText(tab + "Events scheduled for the next available session.");
		else
			desc.setText(tab + "Events scheduled for session ID " + sessionID + ".");
		Font descFont = desc.getFont();
		desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		workP.add(desc);
		workP.add(new JLabel(" "));

		main.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mainAction(evt);
			}
		});

		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
	}

	public void mainAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(0);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(9);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}

}
