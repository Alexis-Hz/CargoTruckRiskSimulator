package eventSim;

import javax.swing.*;
import java.awt.*;

public class eventGenMainPanel extends Panel {

	//JPanels
	JPanel workP, buttonP;
	JPanel tempB, tempD;

	//JLabels
	JLabel Title;
	JLabel addL, modL, delL, monL, viewL;

	//JButtons
	JButton addB, modB, delB, monB, viewB;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	public eventGenMainPanel(){
		initGUI();
	}

	public void initGUI(){

		workP = new JPanel(new BorderLayout());
		
		tempB = new JPanel(new GridLayout(12,1));
		tempD = new JPanel(new GridLayout(12,1));
		
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);


		Title = new JLabel();
		addL = new JLabel();
		modL = new JLabel();
		delL = new JLabel();
		monL = new JLabel();
		viewL = new JLabel();

		addB = new JButton("Add Event");
		modB = new JButton("Modify Event");
		delB = new JButton("Delete Event");
		monB = new JButton("Monitor Events");
		viewB = new JButton("View Scheduled Events");

		Title.setText(tab + "Welcome to the Cargo Trust Event Manager.");
		addL.setText(tab + "Generate a new event for the Cargo Trust Simulator.");
		modL.setText(tab + "Modify an existing event in the queue.");
		delL.setText(tab + "Delete an event from the queue.");
		monL.setText(tab + "Monitor all executting events in the Cargo Trust Simulator.");
		viewL.setText(tab + "View events scheduled for future sessions.");

		workP.add(Title, BorderLayout.NORTH);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		tempB.add(addB);
		tempD.add(addL);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		tempB.add(modB);
		tempD.add(modL);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		tempB.add(delB);
		tempD.add(delL);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		tempB.add(monB);
		tempD.add(monL);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		tempB.add(viewB);
		tempD.add(viewL);
		
		tempB.add(new JLabel(" "));
		tempD.add(new JLabel(" "));
		
		workP.add(new JLabel(tab), BorderLayout.WEST);
		workP.add(tempB, BorderLayout.CENTER);
		workP.add(tempD, BorderLayout.EAST);

		addB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addBAction(evt);
			}
		});

		modB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modBAction(evt);
			}
		});

		delB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				delBAction(evt);
			}
		});

		monB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monBAction(evt);
			}
		});
		
		viewB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewBAction(evt);
			}
		});

	}

	public void addBAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(1);
	}

	public void modBAction(java.awt.event.ActionEvent evt){

	}

	public void delBAction(java.awt.event.ActionEvent evt){

	}

	public void monBAction(java.awt.event.ActionEvent evt){

	}
	
	public void viewBAction(java.awt.event.ActionEvent evt){
		//EGC.ChangePanel(9);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
}
