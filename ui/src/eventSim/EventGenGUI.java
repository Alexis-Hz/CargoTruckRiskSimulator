package eventSim;

import javax.swing.*;
import java.awt.*;

public class EventGenGUI extends JFrame {

	public static EventGenGUI instance;

	JComponent probePanel;

	//GUI Panels
	private JPanel workPanel;
	private JPanel pwindow;	//content pane
	private JPanel ptoolbar;
	private JPanel pInfo, pLeft;
	private JScrollPane spcenter;

	//GUI Radio Button Group
	ButtonGroup rbgroup;
	ButtonGroup rep;

	//GUI Labels
	private JLabel lTrip_StartPoint, lTrip_EndPoint, levCoord;
	private JLabel tTrip_StartPoint, tTrip_EndPoint, tevCoord;

	//GUI TextFields
	JTextField tfstart_time, tfduration;

	private String space = "    ";
	private String def = "***********";
	private String sTrip_StartPoint = def;
	private String sTrip_EndPoint = def;
	private String sevCoord = def;

	public EventGenGUI(){
		instance = this;
		EventGen EG = new EventGen();
		eventGenMainPanel WT = new eventGenMainPanel();
		workPanel = WT.getWorkPanel();
		ptoolbar = WT.getButtonPanel();
		initGUI();
	}

	private void initGUI(){
		this.setTitle("Event Manager");

		try{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(800, 450);

			//setting content pane
			pwindow = new JPanel(new BorderLayout());
			this.setContentPane(pwindow);

			pwindow.add(ptoolbar, BorderLayout.PAGE_START);

			pLeft = new JPanel();
			pLeft.setLayout(new BorderLayout());

			pInfo = new JPanel();
			pInfo.setLayout(new GridLayout(16,2));

			pInfo.add(new JLabel ("  Trip Information  "));
			pInfo.add(new JLabel (""));

			lTrip_StartPoint = new JLabel(" Start Point: ");
			pInfo.add(lTrip_StartPoint);
			pInfo.add(new JLabel (""));

			tTrip_StartPoint = new JLabel(space + sTrip_StartPoint);
			tTrip_StartPoint.setForeground(Color.GRAY);
			pInfo.add(tTrip_StartPoint);
			pInfo.add(new JLabel (""));

			lTrip_EndPoint = new JLabel(" End Point: ");
			pInfo.add(lTrip_EndPoint);
			pInfo.add(new JLabel (""));

			tTrip_EndPoint = new JLabel(space + sTrip_EndPoint);
			tTrip_EndPoint.setForeground(Color.GRAY);
			pInfo.add(tTrip_EndPoint);
			pInfo.add(new JLabel (""));

			levCoord = new JLabel(" Event Coordinate: ");
			pInfo.add(levCoord);
			pInfo.add(new JLabel (""));

			tevCoord = new JLabel(space + sevCoord);
			tevCoord.setForeground(Color.GRAY);
			pInfo.add(tevCoord);
			pInfo.add(new JLabel (""));

			pInfo.add(new JLabel (""));
			pInfo.add(new JLabel (""));

			pLeft.add(pInfo, BorderLayout.PAGE_START);
			pLeft.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			pwindow.add(pLeft, BorderLayout.WEST);

			//Set work panel contents
			spcenter = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			spcenter.setLayout(new ScrollPaneLayout());

			spcenter.setViewportView(workPanel);
			spcenter.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			pwindow.add(spcenter, BorderLayout.CENTER);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized void update(){
		sTrip_StartPoint = tTrip_StartPoint.getText();
		sTrip_EndPoint = tTrip_EndPoint.getText();
		sevCoord = tevCoord.getText();

		tevCoord.setText(space + sevCoord);
		tTrip_StartPoint.setText(space + sTrip_StartPoint);
		tTrip_EndPoint.setText(space + sTrip_EndPoint);

		repaint();

	}

	public synchronized void updatePanel(Panel panel){
		workPanel.removeAll();
		ptoolbar.removeAll();

		workPanel = panel.getWorkPanel();
		ptoolbar = panel.getButtonPanel();
		initGUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EventGenGUI inst = new EventGenGUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

}
