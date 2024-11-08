/**
 * ServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface ServiceSoap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String getShortestRoute(java.lang.String sx, java.lang.String sy, java.lang.String ex, java.lang.String ey) throws java.rmi.RemoteException;
    public java.lang.String getAlternateShortestRoute(java.lang.String sx, java.lang.String sy, java.lang.String ex, java.lang.String ey, java.lang.String bx, java.lang.String by) throws java.rmi.RemoteException;
}
