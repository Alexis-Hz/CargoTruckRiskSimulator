package model.traffic;




import java.io.*;
import java.util.*;
/**
 * The Eventlog is a generic class to store and address sets of related events or logable output
 * 
 * @author David Pruitt
 * @author Jorge Alexis Hernandez
 */

public class EventLog
{
    private List events;
    private int numEntrys;
    
    /**
     * creates a new empty log
     */
    public EventLog()
    {
        events = new LinkedList();
        numEntrys = 0;
    }
    
    /**
     * adds an entry to the log
     * @param e the entry to add
     */
    public void addEntry(Entry e)
    {
        numEntrys++;
        events.add(e);
    }
    
    /**
     * Returns a list of all of the events that occurred at a specific time
     * @param time the time to match
     * @return a list of events that occurred at this time
     */
    public List getEntry(int time)
    {
        LinkedList entrys = new LinkedList();
        Iterator i = events.iterator();
        Entry e = new Entry();
        
        while (i.hasNext())
        {
            e = (Entry) i.next();
            
            if (e.getTime() == time)
            {
                entrys.add(e);
            }
        }
        return entrys;
    }
    
    /**
     * Returns a list of all of the events that occurred at a specific identifier
     * @param ident the identifier to match
     * @return a list of events that occurred at this time
     */
    public List getEntry(String ident)
    {
        LinkedList entrys = new LinkedList();
        Iterator i = events.iterator();
        Entry e = new Entry();
        
        while (i.hasNext())
        {
            e = (Entry) i.next();
            
            if (ident.equalsIgnoreCase(e.getIdent()))
            {
                entrys.add(e);
            }
        }
        return entrys;
    }
    
    /**
     * Creates a copy of the log
     * @return a copy of the event log
     */
    public EventLog getCopy()
    {
        EventLog copy = new EventLog();
        Iterator i = events.iterator();
        Entry e = new Entry();
        
        while (i.hasNext())
        {
            e = (Entry) i.next();
            copy.AddEvent(e);
        }
        
        return copy;
    }
    
    /**
     * Returns a string representation of the event log
     * includes end of line
     * @return a string representation of the event log
     */
    public String toString()
    {
        String working = "";    // String to return since we need to buil it
        EventLog copy = new EventLog();
        Iterator i = events.iterator();
        Entry e = new Entry();
        
        while (i.hasNext())
        {
            e = (Entry) i.next();
            working = working + e.toString() + '\n';
        }
        
        return working;
    }
    
    /**
     * Returns a list of all of the events
     * @return a list of all events
     */
    public List getAll()
    {
        LinkedList copy = new LinkedList();
        Iterator i = events.iterator();
        Entry e = new Entry();
        
        while (i.hasNext())
        {
            e = (Entry) i.next();
            copy.add(e);
        }
        
        return copy;
    }
           
            
    /**
     * Adds an event 
     * @param i event to Add
     */
    public void AddEvent(Entry i)
    {
        numEntrys++;
        events.add(i);
    }


    /**
     * gets the number of entries in the EventLog
     * @return number of entries
     */
    public int getNumEntrys() {
        return numEntrys;
    }
    
        
}