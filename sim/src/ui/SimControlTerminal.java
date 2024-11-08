package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.traffic.EventLog;

import control.*;
import java.awt.Image;
/**
 * This is the console terminal application that runs the simulator, it allows the user to run the simulator, send trips and check the output.
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class SimControlTerminal {

	/**
	 * Simple constructor, creates a new Simulator
	 */
	public SimControlTerminal() throws IOException
	{
		sim = new Simulator();//init simulator
	}
	/**
	 * The same as the previous one, but sets the args to the value, basically this construction is used whenever 
	 * there are options to the simulator.s
	 * @param a
	 * @throws IOException
	 */
	/*
	public SimControlTerminal(int a) throws IOException
	{
		String[] args1 = {"a"};
		sim = new Simulator();//init simulator
	}
	*/
	/**
	 * 
	 * @return "Stop Called Terminal"
	 */
	public String stop()
	{
		return "Stop Called Terminal";
	}
	/**
	 * 
	 * @return "Simulator initialized"
	 */
	public String play()
	{
		return "Simulator initialized";
	}
	/**
	 * 
	 * @return "Pause Called Terminal"
	 */
	public String pause()
	{
		return "Pause Called Terminal";
	}
	/**
	 * 
	 * @return "Step Called Terminal"
	 */
	public String step()
	{
		return "Step Called Terminal";
	}
	
	
	
	String output;
	String command = "";
	Simulator sim;
	
	boolean running = false;
	
	public int getTime()
	{
		return sim.getTime();
	}
	public int getSid()
	{
		return sim.getSid();
	}
	/**
	 * creates a new simulator and starts the main loop of the simulator
	 * @throws IOException
	 */
	public void run() throws IOException
	{
		System.out.println("[Starting Simulator]");
		sim = new Simulator();
		mainLoop();
	}
	/**
	 * this method gets the all the logs from the simulator to be printed in the main loop
	 * @return a String concatenation  of all the logs of the simulator
	 */
	public String getLogs()
	{
		String tr ="";
		
		tr += "+Simulator Output" + '\n';
		EventLog log1 = sim.getLog();
		tr += log1.toString() + '\n';
		tr+="+";
		
		tr += "*Trips Queued" + '\n';
		EventLog log2 = sim.getQueueStatus();
		tr += log2.toString() + '\n';
		tr+="*";
		
		tr += "^Truck Positions" + '\n';
		try
		{
			EventLog log4 = sim.getPositions();
			tr += log4.toString() + '\n';
		}
		catch(NullPointerException e)
		{
			System.out.println("_");
		}
		tr+="^Blocks" + '\n';
		tr+= sim.blockListToString();
		
		return tr;
	}
	
	//only for terminal
	public void clear()
	{
		output = toString();
		
		System.out.println(output);
	}
	/**
	 * gets the simulator output log in a String
	 * @return A String version of the output log
	 */
	public String getSimOutput()
	{
		EventLog log1 = sim.getLog();
		return log1.toString();
	}
	/**
	 * 
	 * @return gets a String version of the Queued Trips log from the simulator
	 */
	public String getTripsQueued()
	{
		EventLog log2 = sim.getQueueStatus();
		return log2.toString();
	}
	/**
	 * 
	 * @return The String version of the Trucks and their positions in the simulator
	 */
	public String getTruckPositions()
	{
		String tr = "";
		try
		{
			EventLog log4 = sim.getPositions();
			tr += log4.toString() + '\n';
		}
		catch(NullPointerException e)
		{
			
		}
		
		return tr;
	}
	/**
	 * This is the main loop of the simulator, at every iteration it prompts for input handles 
	 * the input and updates the simulator accordingly or prints output
	 * @throws IOException on input errors on the terminal
	 */
	public void mainLoop() throws IOException
	{
		//clear();
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		try
		{
		while(true)
		{
			System.out.print(">");
			command = in.readLine();
			System.out.println();
			String [] sp = command.split(" ");
			if(command.equalsIgnoreCase("s"))
			{
//				run();
//				clear();
				
				sim.loop(2000, 100);
			}
			else if(command.equalsIgnoreCase("t"))
			{
				sim.run();
				//clear();
			}
			else if(command.equalsIgnoreCase(""))
			{
				sim.run();
				//clear();
			}
			else if(sp[0].equalsIgnoreCase("x"))
			{
				if(sp.length > 0)
					for(int i =0; i < Integer.parseInt(sp[1]); i++)
					{
						sim.run();
						//clear();
					}
			}
			else if(sp[0].equalsIgnoreCase("in"))
			{
//				if(sp.length > 0)
//					sim.sendIn(Integer.parseInt(sp[1]));
			}
			else if(sp[0].equalsIgnoreCase("per"))
			{
				if(sp.length > 0)
					sim.changePer(Integer.parseInt(sp[1]));
			}
			else if(command.equalsIgnoreCase("c"))
			{
				//clear();
			}
			else if(command.equalsIgnoreCase("q"))
			{
				quit();
			}
			else if(command.equalsIgnoreCase("play"))
			{
				sim.loop(1000, 200);
				//clear();
			}
			else if(command.equalsIgnoreCase("reset"))
			{
				System.out.print("New scenario: ");
				command = in.readLine();
				sim.reset(Integer.parseInt(command));
				//clear();
			}
			else if(command.equalsIgnoreCase("send"))
			{
				sim.sendTripRandom();
				//clear();
			}
			else
				System.out.println("[Invallid Command]");
			//System.out.println(output);
			clear();
		}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace(System.out);
		}
	}
	/**
	 * Stops the simulator and exits the application
	 */
	public void quit()
	{
		System.out.println("[Exiting Simulator]");
		Runtime runt = java.lang.Runtime.getRuntime();
		runt.exit(0);
	}
	/**
	 * Empty Method
	 */
	public void exit()
	{
		
	}	
	/**
	 * Initializes the TErminal
	 * @return STerminal toString
	 */
	public String terminalInit()
	{
		running = true;
		return toString();
	}
	/**
	 * Steps one time unit in the future, and reacts to the command given in the simulator
	 * @param comm the command for the simulator to react to
	 * @return a string containing the output of this method
	 * @throws IOException
	 */
	public String terminalStep(String comm) throws IOException
	{
		String [] sp = comm.split(" ");
		if(!running)
		{
			return "Simulator not Running" + '\n' + "Start Simulator";
		}
		if(comm.equalsIgnoreCase("t"))
		{
			sim.run();
		}
		else if(sp[0].equalsIgnoreCase("x"))
		{
			for(int i =0; i < Integer.parseInt(sp[1]); i++)
			{
				sim.run();
			}
		}
		else if(comm.equalsIgnoreCase("c"))
		{
			return toString();
		}
		else if(comm.equalsIgnoreCase("q"))
		{
			running = false;
			return "Simulator Stopped";
		}
		else
			return "[Invalid Command]" + '\n' + output;
		return toString();
	}
	/**
	 * Returns a String containing the output of the simulator, this includes, the commands that can be used and their description
	 * and the different logs the simulator keeps
	 */
	public String toString()
	{
		if(true)
			output = "Time-" + sim.getTime() + '\n';
		else
			output = "Time-Not Started" + '\n';
		output += "--------------Simulator Control Terminal--------------" + '\n';
		output +="Commands: s=restart simulator,  t=step in time, c=show commands" + '\n' +"Commands: x=x steps in time, q=quit simulator" + '\n';
		output += "------------------------------------------------------"+ '\n';
		if(true)
			output += getLogs();
		
		return output;
	}
	/**
	 * simple testing main function
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		SimControlTerminal term =  new SimControlTerminal();
		term.run();
	}

}
