/**
 * ThreatDetectionSystem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model.tds.threatDetectionService;

public interface ThreatDetectionSystem extends java.rmi.Remote {
    public java.lang.String getRecomenation(java.lang.String vehicleID, java.lang.String eRoute, java.lang.String aRoute, java.lang.String eArrivalTime, java.lang.String aArrivalTime) throws java.rmi.RemoteException;
    public java.lang.String getPMLExplanation(java.lang.String vehicleID, java.lang.String eRoute, java.lang.String aRoute, java.lang.String eArrivalTime, java.lang.String aArrivalTime) throws java.rmi.RemoteException;
}
