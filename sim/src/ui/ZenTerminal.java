package ui;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.traffic.EventLog;
import control.Simulator;

/**
 * Zen Terminal is the HttpServlet for the Simulator application. It is the connection between the jsp front end and the java servlet back end.<p>
 * It uses the doGet to return Positions of trips and doPost to handle input from the jsp front end
 * 
 * The other methods in the class are actions to be relayed to the simulator from the front end
 * 
 * There can only be one simulator running no matter the ammount of front ends using it.
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class ZenTerminal extends HttpServlet
{

	public static boolean running =false;
	public static boolean paused = false;
	public static int factor = 1;//minute per minute
	public static Simulator sim = null;
/**
 * doGet is used to obtain the positions from the simulator relayed to the front end. <p>
 * it generates a PrintWriter object and it associates it to the HttpSerlvletResponse and writes the positions to it, this way the positions are relayed to the requesting http front end
 * @param request the request from the front end
 * @param response the response to be relayed back to the front end
 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{   
		PrintWriter out = response.getWriter();
	    getPositions(out);
	}
	/**
	 * doPost is called from the http or in this case ajax front end to interface with the simulator<p>
	 * This communication is regulated by a set of functions, the method checks the input in the
	 *  request and calls the appropriate functions to handle the following requests.
	 *  <ul>
	 *  <li>stop
	 *  <li>play
	 *  <li>pause
	 *  <li> step
	 *  <li>refresh
	 *  <li>per
	 *  </ul>
	 *  it responds to these requests by calling the similarly named methods
	 * @param request the request from the front end
	 * @param response the response to be relayed back to the front end
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		String action = request.getParameter("act");
		if(action.equalsIgnoreCase("stop"))
		{
			stop(out);
		}
		else if(action.equalsIgnoreCase("play"))
		{
			play(out);
		}
		else if(action.equalsIgnoreCase("pause"))
		{
			pause(out);
		}
		else if(action.equalsIgnoreCase("step"))
		{
			step(out);
		}
		else if(action.equalsIgnoreCase("refresh"))
		{
			refresh(out);
		}
		else if(action.equalsIgnoreCase("per"))
		{
			per(out);
		}
		else
		{
			out.println("Unknown Ajax Call(At POST)!");
		}
  	}
	public static boolean started = false;
	/**
	 * calls the stop method in the simulator and writes the output of the simulator to the PrintWriter object
	 * @param o PrintWriter associated with the response from the Http fron end
	 */
	public void stop(PrintWriter o)
	{
		if(started)
		{
			sim.stop();
			o.println(sim.output());
		}
	}
	/**
	 * calls the play method in the simulator and writes the output of the simulator to the PrintWriter object
	 * if the simulator is not started already it creates a new simulator and changes the status to started
	 * @param o PrintWriter associated with the response from the Http fron end
	 */
	public void play(PrintWriter o) throws ServletException, IOException
	{
		if(started)
		{
			//o.println("Started and Play Call");
			sim.play();
		}
		else
		{
			try
			{
				sim = new Simulator();
			}
			catch(Exception e)
			{
				e.printStackTrace(o);
			}
			o.println(sim.output());
			started = true;
		}
	}
	/**
	 * calls the pause method in the simulator and writes the output of the simulator to the PrintWriter object
	 * @param o PrintWriter associated with the response from the Http front end
	 */
	public void pause(PrintWriter o)
	{
		if(started)
		{
			sim.pause();
			o.println(sim.output());
		}
	}
	/**
	 * calls the step method in the simulator and writes the output of the simulator to the PrintWriter object
	 * @param o PrintWriter associated with the response from the Http front end
	 */
	public void step(PrintWriter o)
	{
		if(started)
		{
			sim.step();
			o.println(sim.output());
		}
	}
	/**
	 * calls the refresh method in the simulator and writes the output of the simulator to the PrintWriter object
	 * @param o PrintWriter associated with the response from the Http front end
	 */
	public void refresh(PrintWriter o)
	{
		if(started)
		{
			o.println(sim.output());
		}
	}
	/**
	 * calls the per method in the simulator and writes the output of the simulator to the PrintWriter object
	 * @param o PrintWriter associated with the response from the Http front end
	 */
	public void per(PrintWriter o)
	{
		if(started)
		{
			sim.changePer(1);
			o.println(sim.output());
		}
	}
	/**
	 * Gets the positions from the simulator and writes a digest of them to the PrintWriter associated with the response
	 * @param o PrinterWriter object where all the positions are written<p>
	 * prints none if the simulkator is not started
	 */
	public void getPositions(PrintWriter o)
	{
		if(started)
		{
			o.println(sim.digest());
		}
		else
		{
			o.println("none");
		}
	}
}
