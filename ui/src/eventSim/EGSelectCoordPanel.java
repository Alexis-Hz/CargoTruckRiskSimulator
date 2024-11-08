package eventSim;

import java.awt.*;

import javax.swing.*;

public class EGSelectCoordPanel extends Panel {

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

	private String[] route_coords, labels;
	private String sTrip_StartPoint, sTrip_EndPoint;


	public EGSelectCoordPanel(){
		initGUI();
	}

	public EGSelectCoordPanel(String start, String end){
		sTrip_StartPoint = start;
		sTrip_EndPoint = end;
		initGUI();
	}

	private void initGUI(){
		workP = new JPanel();
		workP.setLayout(new BoxLayout(workP, BoxLayout.Y_AXIS));
		buttonP = new JPanel(new FlowLayout());
		buttonP.setBackground(Color.DARK_GRAY);

		Desc = new JLabel();
		Desc.setText(tab + "Select the Coordinates in the route of the Event.");
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
		GetCoordAction();

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

	private synchronized void GetCoordAction(){

		try{
			RouteAccess loader = new RouteAccess();
			loader.from = sTrip_StartPoint;
			loader.to = sTrip_EndPoint;
			loader.start();
			GetCoordActionWork();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void GetCoordActionWork() throws InterruptedException{
		while (EventGen.instance.getRoute() == null)
			wait(999);
		route_coords = EventGen.instance.getParsedRoute();

		genRouteLabels();
		RBG = new ButtonGroup();

		if(route_coords != null)
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

	private void genRouteLabels(){
		labels = new String[route_coords.length];
		for(int index = 0; index < route_coords.length; index++){
			labels[index] = tab + "[" + route_coords[index] + "]";
		}
	}

	private void genInformation(){
		ButtonModel selectedButton = RBG.getSelection();
		int index = selectedButton.getMnemonic();
		String sevCoord = route_coords[index];
		EventGen.instance.setCoor(sevCoord);
	}

	public void nextAction(java.awt.event.ActionEvent evt){
		genInformation();

		String type = EventGen.instance.getEvtType();
		if(type.contains("CANCEL") || type.contains("BLOCK"))
			EGC.ChangePanel(7);
		else		
			EGC.ChangePanel(5);
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
