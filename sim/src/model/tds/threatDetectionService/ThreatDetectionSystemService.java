/**
 * ThreatDetectionSystemService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model.tds.threatDetectionService;

public interface ThreatDetectionSystemService extends javax.xml.rpc.Service {
    public java.lang.String getThreatDetectionServiceAddress();

    public model.tds.threatDetectionService.ThreatDetectionSystem getThreatDetectionService() throws javax.xml.rpc.ServiceException;

    public model.tds.threatDetectionService.ThreatDetectionSystem getThreatDetectionService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
