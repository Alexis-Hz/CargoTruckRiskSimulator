package model.traffic;
/**
 * Helper class for event log
 * Records an event as a time and and Event
 * @author David Pruitt
 *
 */
public class Entry
{
	private int time;		// time event occured
	private String desc;  	// event description
	private String ident;	// event type identifier


	/**
	 * Constructor that creates new empty event
	 */
	public Entry()
	{
		time = 0;
		desc = "";
	}
	
	/**
	 * Constructor that creates event with a time and description
	 */
	public Entry(int time,String ident, String desc)
	{
		this.time = time;
		this.desc = desc;
		this.ident = ident;
	}
	/**
	 * @return String representation of the entry <p>
	 * time + " " + ident + " " + desc
	 */
	public String toString()
	{
		return time + " " + ident + " " + desc;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}	
	
	

}
