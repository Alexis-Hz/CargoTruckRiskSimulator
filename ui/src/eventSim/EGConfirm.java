package eventSim;

import java.awt.*;
import javax.swing.*;

public class EGConfirm extends Panel {

	//JPanels
	JPanel workP, buttonP;

	//JButtons
	JButton confirm, back;

	//JLabels
	JLabel desc;

	//Special Variables
	private String tab = "    ";
	EGController EGC = new EGController();

	public EGConfirm(){
		initGUI();
	}

	private void initGUI(){
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		confirm = new JButton(" Confirm ");
		back = new JButton("< Back ");

		buttonP.add(back);
		buttonP.add(confirm);

		desc = new JLabel();
		desc.setText(tab + "Confirm the event settings.");
		Font descFont = desc.getFont();
		desc.setFont(new Font(descFont.getFontName(), descFont.getStyle(), 14));

		workP.add(desc);
		workP.add(new JLabel(" "));
		genConfirmationPage();

		confirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConfirmAction(evt);
			}
		});

		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backAction(evt);
			}
		});
	}

	private void genConfirmationPage(){

		String[] EvType = EventGen.instance.getEvtType().split(" - ");

		String session = "Current Session";
		if(EventGen.instance.getSessionID() == -1)
			session = "Next Session";
		workP.add(new JLabel(tab + "Session ID: " + session));
		
		workP.add(new JLabel(tab + "Type of event: " + EvType[0] + " " + EvType[2]));
		workP.add(new JLabel(tab + "Scope: " + EvType[1]));
		
		String Dur = EventGen.instance.getDuration() + " minutes.";
		if(EventGen.instance.getDuration() == -1)
			Dur = "Forever";
		workP.add(new JLabel(tab + "Duration of the event: " + Dur));

		if(EventGen.instance.getInterval() != -1)
			workP.add(new JLabel(tab + "Repeated every " + EventGen.instance.getInterval() + " minutes."));

		String repo = "No";
		if(EventGen.instance.isReported())
			repo = "Yes";
		workP.add(new JLabel(tab + "Event reported by a trustworthy agent: " + repo));

		String reason = EventGen.instance.getReason();
		if(reason.length() <= 0)
			reason = "N/A";
		workP.add(new JLabel(tab + "Reason of the event: " + reason));

		if(EventGen.instance.getOriginPoint() != null){
			//if(EvType[0].equalsIgnoreCase("ASYNC") || EvType[1].equalsIgnoreCase("TRIP")){
			workP.add(new JLabel(tab + "From Maquila: " + EventGen.instance.getOriginPoint()));
			workP.add(new JLabel(tab + "To Port of Entry: " + EventGen.instance.getDestPoint()));
		}

		if(EventGen.instance.getCoor() != null){
			//if(EvType[0].equalsIgnoreCase("ASYNC")){
			double[] coords = EventGen.instance.getparseCoords();
			workP.add(new JLabel(tab + "Event in coordinate: [" + coords[0] + " , " + coords[1] + "]"));
		}
		else
			workP.add(new JLabel(tab + "Event start time: Minute " + EventGen.instance.getStart_time()));

	} 

	public void ConfirmAction(java.awt.event.ActionEvent evt){
		EventGen.instance.genEventString();
		EGC.setSessionID(-1);
		EGC.ChangePanel(10);
	}

	public void backAction(java.awt.event.ActionEvent evt){
		EGC.ChangePanel(7);
	}

	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}

}
