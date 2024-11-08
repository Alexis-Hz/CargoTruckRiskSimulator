package dhs.oi;

import javax.swing.*;

public class OfficerApplet extends JApplet 
{
	
	public void init()
	{
		OfficerView inst = new OfficerView("#######", "#######", 1000,1000);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
	}
}
