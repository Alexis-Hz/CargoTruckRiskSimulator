/**
 * OfficerSimProtocol.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package dhs.oi.protocol;

public interface OfficerSimProtocol extends java.rmi.Remote {
    public void main(java.lang.String[] args) throws java.rmi.RemoteException;
    public int[] getLane() throws java.rmi.RemoteException;
    public java.lang.String getNextTruck(int lane, int poe, int sid, java.lang.String truck) throws java.rmi.RemoteException;
}
