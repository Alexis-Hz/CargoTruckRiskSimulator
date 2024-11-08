package model.cargo;

import java.util.Random;

/**
 * This class represents a driver associated with a trip, a driver is identified	
 * by name and license number. 
 *
 * Licenses are unique, names may be duplicated
 * 
 * @author Jorge Alexis Hernandez	 
 * @version April 2010
 */

public class Driver implements Comparable{
	
	private String name;
	private String license;
	public Driver(String namex, String licensex)
	{
		name = namex;
		license =  licensex;
	}
	String [] names = {"Jose Luis Rodriguez", 
						"Roberto Sanchez Ocampo", 
						"Jose Luis Perales",
						"Jose Romulo Sosa",
						"Alberto Aguilera Valadez",
						"Roberto Carlos Braga"};
	String [] licenses = {"SD7AS5ASDB327", 
						  "BNAS8U1EINU1B", 
						  "JAHSD87AHDAS9",
						  "ASND781DN19UN",
						  "8HD9UNDS9DJS9",
						  "ASNDM918JD9SA"};
	public Driver()
	{
		Random rand = new Random();
		int  n = rand.nextInt(6);
		name = names[n];
		license =  licenses[n];
	}
	public String getName()
	{
		return name;
	}
	public String getLicense()
	{
		return license;
	}
	/**
	 * Compares the driver to another driver in the parameter
	 * @param arg0  Driver object, type not checked explicitly
	 * @return 0: if the licenses match
	 * 1: otherwise
	 */
	public int compareTo(Object arg0) {
		Driver other = (Driver)arg0;
		if(other.getLicense().equals(license))
			return 0;
		else 
			return 1;
	}
	/**
	 * toString: prints out: name_license
	 */
	public String toString()
	{
		return name +":"+license;
	}
}
