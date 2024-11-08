package control;

public class Settings {
	
	public boolean mock = false;
	public static Settings mainSettings;
	public Settings()
	{
		mock = false;
		//set up as  default simulator
	}
	public Settings(boolean mockValue)
	{
		mock = mockValue;
		//set up as  default simulator
	}
	public static void setMainSettings()
	{
		mainSettings = new Settings(false);
	}
	public static String settingsDigest()
	{
		String tr = "======Settings=====" + "\n";
		tr += "Mock = " + mainSettings.mock;
		tr += "======Settings=====" + "\n";
		return tr;
	}
}
