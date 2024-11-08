package model.traffic;


import java.io.*;
import java.util.*;

/**
 * used to test EventLog, not necessary in any other class. delete if needed
 * 
 * @author David Pruitt
 * @author Jorge Alexis Hernandez
 */

public class EventLogTest
{
    public static void main(String args[])
    {
        EventLog e1 = new EventLog();
        EventLog e2 = new EventLog();
        LinkedList l = new LinkedList();
        
        e1.addEntry(new Entry(0, "Starting", "starting"));
        e1.addEntry(new Entry(1, "a12", "starting"));
        e1.addEntry(new Entry(3, "a12", "moving"));
        e1.addEntry(new Entry(3, "a13", "starting"));
        System.out.println(e1);
        System.out.println("Number of entrys " + e1.getNumEntrys());
        
        e2 = e1.getCopy();
        System.out.println(e2);
        System.out.println("Number of entrys " + e2.getNumEntrys());   
        
        l =(LinkedList) e1.getEntry(3);
        System.out.println(l);
        
        l =(LinkedList) e1.getEntry("a12");
        System.out.println(l);
        
    }
}
