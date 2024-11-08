package tds.prolog;
import java.util.Hashtable;

import jpl.Atom;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import jpl.Util;
import jpl.Compound;
import jpl.Variable;
import java.util.ArrayList;
import java.util.ListIterator;
 
public class PrologInteraction {
	
	private static final String PLFile_TDS = "C:\\eclipse-ws\\workspace-ci\\dhs\\tds\\cargoThreatDetection-working.pl";
	private static final String PLFile_PMLX = "C:\\eclipse-ws\\workspace-ci\\dhs\\tds\\pml2x-working.pl";
	
	public PrologInteraction()
	{
		init();
	}
	
	/**
	 * Initializes interaction with datalog.
	 *
	 */
	private void init()
	{
		consultFile();
	}
	
	/**
	 * Deletes all facts from datalog
	 *
	 */
	public void abolish()
	{
		Term[] rem = new Term [30];
		rem[0] = Util.textToTerm("proofName/1");
		rem[1] = Util.textToTerm("monitor/1");
		rem[2] = Util.textToTerm("assignedRoute/2");
		rem[3] = Util.textToTerm("actualRoute/2");
		rem[4] = Util.textToTerm("expectedArrivalTime/2");
		rem[5] = Util.textToTerm("actualArrivalTime/2");
		rem[6] = Util.textToTerm("alarmOn/3");
		rem[7] = Util.textToTerm("trafficJam/3");
		rem[8] = Util.textToTerm("trafficAccident/3");
		rem[9] = Util.textToTerm("weatherDelay/3");
		rem[10] = Util.textToTerm("timeDeviation/3");
		rem[11] = Util.textToTerm("usualPort/2");
		rem[12] = Util.textToTerm("thisPort/1");
		rem[13] = Util.textToTerm("usualShipper/2");
		rem[14] = Util.textToTerm("thisShipper/2");
		rem[15] = Util.textToTerm("usualBroker/2");
		rem[16] = Util.textToTerm("thisBroker/2");
		rem[17] = Util.textToTerm("usualCargo/2");
		rem[18] = Util.textToTerm("thisCargo/2");
		rem[19] = Util.textToTerm("usualHours/2");
		rem[20] = Util.textToTerm("thisHours/2");
		rem[21] = Util.textToTerm("usualDriver/2");
		rem[22] = Util.textToTerm("thisDriver/2");
		rem[23] = Util.textToTerm("usualOrigin/2");
		rem[24] = Util.textToTerm("thisOrigin/2");
		rem[25] = Util.textToTerm("usualDestination/2");
		rem[26] = Util.textToTerm("thisDestination/2");
		rem[27] = Util.textToTerm("usualTransportCo/2");
		rem[28] = Util.textToTerm("thisTransportCo/2");
		rem[29] = Util.textToTerm("hasPatternViolation/2");
		 
		 for (int i = 0; i < rem.length; i++){
			 System.out.println("iteration: " + i);
			 Query query = new Query("abolish", rem[i]);
	         query.oneSolution();
		 }
	}

	/**
	 * Loads contents of PLFile into Datalog
	 */
	private void consultFile(){
		consultFile(PrologInteraction.PLFile_TDS);
		consultFile(PrologInteraction.PLFile_PMLX);
	}
	
	/**
	 * Load PLfile into Datalog
	 * @param sourcePL .pl File name
	 */
	private void consultFile(String sourcePL){
		try{
		
	      JPL.init(); 
	      
	        Term consult_arg[] = { 
	            new Atom( sourcePL ) 
	        };
	        Query consult_query = 
	            new Query( 
	                "consult", 
	                consult_arg );

	        boolean consulted = consult_query.hasSolution();
	        
	        if ( !consulted ){
	            System.err.println( "Consult failed" );
	            System.exit( 1 );
	        }

		}
		catch(Exception e){	
			System.out.println("Error ocurred while creating Datalog EXE: " + e);
		}
	}	

	/**
	 * Send query to Datalog and query
	 * @param Query
	 */
	public boolean queryDatalogNoVar(String Query){	 
		 init();
		 Term arg = Util.textToTerm(Query);
		 Query q = new Query( arg );
		 System.out.println("Executing Query: " + q.goal().toString());
		 ArrayList variablesUsed = getVariablesFromQuery(Query);
		 System.out.println("Binding variables: " + variablesUsed.toString() + "\n");
		 if(q.hasSolution())
			 return true;
		 return false;
		
	}
	
	/**
	 * Send query to Datalog and query
	 * @param Query
	 */
	public String queryDatalog(String Query){	 
		 Term arg = Util.textToTerm(Query);
		 Query q = new Query( arg );
		 System.out.println("Executing Query: " + q.goal().toString());
		 ArrayList variablesUsed = getVariablesFromQuery(Query);
		 System.out.println("Binding variables: " + variablesUsed.toString() + "\n");
		 
		Hashtable ht;		 
		int i =0;
		String ans = "";
		while ( q.hasMoreSolutions())
		{
			System.out.println("has solutions...");
			ht = (Hashtable) q.nextSolution();
			ans += getStringForSingleSolution(ht,variablesUsed) + "\n";
		}
		q.close();
		return ans;
		
	}
	
	/**
	 * Prints the contents of query solution (ht), by requesting each key's value as contained in
	 * varsList
	 * @param htAnswer Hashtable containing prolog answer
	 * @param varsList List of variables (keys) whose values we have to retrieve.
	 */
	private static String getStringForSingleSolution(Hashtable htAnswer, ArrayList varsList){
		String ans = "";
		if(!htAnswer.isEmpty() && !varsList.isEmpty()){
			for( ListIterator varIterator = varsList.listIterator(); varIterator.hasNext();){
				String temp = (String)varIterator.next();
				if(htAnswer.containsKey(temp)){
					ans += htAnswer.get(temp).toString() +",";					
				}
			}
			ans = ans.substring(0,ans.length()-1);			
		}
		else{
			return "Dunno!";
		}
		return ans;
	}
	
	/**
	 * Return all variables from a given query given as a String
	 * @param query String representation of datalog query
	 * @return A list of variables.
	 */
	private static ArrayList getVariablesFromQuery(String query){
		 Term arg = Util.textToTerm(query);
		 Query q = new Query( arg );
		 Compound compoundQuery = q.goal();
		 return getVariablesFromQuery(compoundQuery);
	}
	
	/**
	 * returns a list of all Variables contained by a Compound query
	 * @param query a Compound query
	 * @return A list containing all variables used in query
	 */
	private static ArrayList getVariablesFromQuery(Compound query){
		ArrayList varNames = new ArrayList();
		getVariablesFromQuery(query,varNames);
		return varNames;
	}
	
	/**
	 * Returns inserts variable names to a given list, based on the Compound
	 * query. Uses pass by reference. 
	 * @param query Compound query
	 * @param varNamesList List were to store results
	 */
	private static void getVariablesFromQuery(Compound query, ArrayList varNamesList){
		int size = query.arity();
		for(int i = 1; i<= size; i++){
			Term temp = query.arg(i);
			if(temp.isCompound()){
				getVariablesFromQuery((Compound) temp,varNamesList);
			}
			else if(temp.isVariable()){
				Variable var = (Variable) temp;
				addToList( var.name(), varNamesList);
			}			
		}
	}
	
	/**
	 * Adds variable name to list in order to avoid repetitions =).
	 * This method uses pass by reference.
	 * @param variable Variable being added to a list
	 * @param varNamesList list containing variable names to which variable 
	 * 		will be added to.
	 */
	private static void addToList(String variable, ArrayList varNamesList){
		if( !varNamesList.contains(variable)){
			varNamesList.add(variable);
		}		
	}
}
