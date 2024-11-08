package eventSim;

import java.awt.*;

import javax.swing.*;

public class EGReqPanel extends Panel{

	//JPanels
	JPanel workP, buttonP;
	
	//JButtons
	JButton next, back;
	
	//JLabels
	JLabel lDuration, lInterval, lReported, lReason;
	
	//ButtonGroup
	ButtonGroup RBGReported, RBGDuration;
	
	//RadioButtons
	JRadioButton reported, notReported;
	
	//JTextArea and TextFields
	JTextField TFDuration, TFInterval, Reason;
	
	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();
	
	public EGReqPanel(){
		initGUI();
	}
	
	private void initGUI(){
		
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		next = new JButton(" Next >");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(next);
		
		lDuration = new JLabel();
		lDuration.setText(tab + "Set Duration of the Event.");
		Font descFont = lDuration.getFont();
		lDuration.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
		
		lInterval = new JLabel();
		lInterval.setText(tab + "Set to repeat the event on intervals. (OPTIONAL)");
		lInterval.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
		
		lReported = new JLabel();
		lReported.setText(tab + "Was the event reported by a trustworthy Agent?");
		lReported.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
		
		lReason = new JLabel();
		lReason.setText(tab + "Enter a reason of the event. (If available)");
		lReason.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));
		
		//duration Radio Button Group
		String sForever = "Set the event to run forever.";
		String sSet = "Set the duration of the event to: ";
		
		RBGDuration = new ButtonGroup();
		JRadioButton forever = new JRadioButton(sForever);
		forever.setMnemonic(1);
		
		JRadioButton set = new JRadioButton(sSet);
		set.setMnemonic(0);
		
		forever.setSelected(true);
		RBGDuration.add(forever);
		RBGDuration.add(set);
		
		TFDuration = new JTextField();
		TFDuration.setColumns(15);
		
		JPanel tempP = new JPanel(new BorderLayout());
		tempP.add(TFDuration, BorderLayout.WEST);
		tempP.add(new JLabel(" "), BorderLayout.SOUTH);
		
		//interval Settings
		
		TFInterval = new JTextField();
		TFInterval.setColumns(15);
		
		JPanel tempP2 = new JPanel(new BorderLayout());
		tempP2.add(TFInterval, BorderLayout.WEST);
		tempP2.add(new JLabel(" "), BorderLayout.SOUTH);
		
		//Reported Settings
		RBGReported = new ButtonGroup();
		reported = new JRadioButton(" Yes ");
		reported.setMnemonic(1);

		notReported = new JRadioButton(" No ");
		notReported.setMnemonic(0);
		
		reported.setSelected(true);
		RBGReported.add(reported);
		RBGReported.add(notReported);
		
		//reason settings
		Reason = new JTextField();
		Reason.setColumns(30);
		JPanel tempPR = new JPanel(new BorderLayout());
		tempPR.add(Reason, BorderLayout.WEST);
		tempPR.add(new JLabel(" "), BorderLayout.SOUTH);
		
		workP.add(new JLabel(" "));
		workP.add(lDuration);
		workP.add(new JLabel(" "));
		workP.add(forever);
		workP.add(set);
		workP.add(tempP);
		
		workP.add(lInterval);
		workP.add(new JLabel(" "));
		workP.add(tempP2);

		workP.add(lReported);
		workP.add(new JLabel(" "));
		workP.add(reported);
		workP.add(notReported);
		
		workP.add(new JLabel(" "));
		workP.add(lReason);
		workP.add(new JLabel(" "));
		workP.add(tempPR);
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
		ButtonModel selectedButton = RBGDuration.getSelection();
		int index = selectedButton.getMnemonic();
		if(index == 1)
			EventGen.instance.setDuration(-1);
		else{
			String ST = TFDuration.getText();
			EventGen.instance.setDuration(ST);
		}
		
		if(TFInterval.getText().length() > 0)
			EventGen.instance.setInterval(TFInterval.getText());
		
		selectedButton = RBGReported.getSelection();
		index = selectedButton.getMnemonic();
		if(index == 0)
			EventGen.instance.setReported(0);
		else{
			EventGen.instance.setReported(1);
		}
		
		EventGen.instance.setReason(Reason.getText());
	}
	
	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();
		EGC.ChangePanel(8);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		String type = EventGen.instance.getEvtType();
		if(type.startsWith("ASYNC"))
			EGC.ChangePanel(5);
		else
			EGC.ChangePanel(6);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
	
}
