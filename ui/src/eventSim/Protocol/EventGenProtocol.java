/**
 * EventGenProtocol.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eventSim.Protocol;

public interface EventGenProtocol extends java.rmi.Remote {
    public void main(java.lang.String[] args) throws java.rmi.RemoteException;
    public java.lang.String[][] getTrip() throws java.rmi.RemoteException;
    public java.lang.String getRoute(java.lang.String trip_orig, java.lang.String trip_dest) throws java.rmi.RemoteException;
    public boolean insertEvent(java.lang.String query, java.lang.String orig, java.lang.String end) throws java.rmi.RemoteException;
}
