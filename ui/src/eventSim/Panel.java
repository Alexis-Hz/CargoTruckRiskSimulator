package eventSim;

import javax.swing.JPanel;

public abstract class Panel {
	
	private JPanel workP;
	private JPanel buttonP;
	
	public JPanel getWorkPanel(){
		return workP;
	}
	public JPanel getButtonPanel(){
		return buttonP;
	}
	
}
