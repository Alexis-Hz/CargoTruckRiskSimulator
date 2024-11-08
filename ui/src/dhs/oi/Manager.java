package dhs.oi;

import javax.swing.*;
import probeIt.ProbeIt;
import probeIt.ui.ProbeItViewsConfiguration;

public class Manager {

	private static boolean test = false;
	//gets the tabbed panel from probe it and returns it, must give parent frame
	public static JComponent getProbeItPane(JFrame daddy, String uri)
	{
		ProbeIt probeIt;
		ProbeItViewsConfiguration views;
		probeIt = new ProbeIt(daddy);
		probeIt.setRemote();
		views = probeIt.getEmbeddedProbeIt();
        
		views.enableFeature_R();
		views.enableFeature_J();
		views.setURI(uri);
		JComponent probeItTab = views.getViewPane();
		System.out.println("setting tabbed pane...");
		return probeItTab;
	}
}
