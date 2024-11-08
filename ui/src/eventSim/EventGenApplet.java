package eventSim;

import javax.swing.*;

public class EventGenApplet extends JApplet {
	
	public void init()
	{
		EventGenGUI inst = new EventGenGUI();
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
	}

}
