/**
 * ThreatDetectionSystemServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model.tds.threatDetectionService;

public class ThreatDetectionSystemServiceLocator extends org.apache.axis.client.Service implements model.tds.threatDetectionService.ThreatDetectionSystemService {

    public ThreatDetectionSystemServiceLocator() {
    }


    public ThreatDetectionSystemServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ThreatDetectionSystemServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ThreatDetectionService
    private java.lang.String ThreatDetectionService_address = "http://129.108.4.237:8080/axis/services/ThreatDetectionService";

    public java.lang.String getThreatDetectionServiceAddress() {
        return ThreatDetectionService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ThreatDetectionServiceWSDDServiceName = "ThreatDetectionService";

    public java.lang.String getThreatDetectionServiceWSDDServiceName() {
        return ThreatDetectionServiceWSDDServiceName;
    }

    public void setThreatDetectionServiceWSDDServiceName(java.lang.String name) {
        ThreatDetectionServiceWSDDServiceName = name;
    }

    public model.tds.threatDetectionService.ThreatDetectionSystem getThreatDetectionService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ThreatDetectionService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getThreatDetectionService(endpoint);
    }

    public model.tds.threatDetectionService.ThreatDetectionSystem getThreatDetectionService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            model.tds.threatDetectionService.ThreatDetectionServiceSoapBindingStub _stub = new model.tds.threatDetectionService.ThreatDetectionServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getThreatDetectionServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setThreatDetectionServiceEndpointAddress(java.lang.String address) {
        ThreatDetectionService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (model.tds.threatDetectionService.ThreatDetectionSystem.class.isAssignableFrom(serviceEndpointInterface)) {
                model.tds.threatDetectionService.ThreatDetectionServiceSoapBindingStub _stub = new model.tds.threatDetectionService.ThreatDetectionServiceSoapBindingStub(new java.net.URL(ThreatDetectionService_address), this);
                _stub.setPortName(getThreatDetectionServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ThreatDetectionService".equals(inputPortName)) {
            return getThreatDetectionService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://129.108.4.237:8080/axis/services/ThreatDetectionService", "ThreatDetectionSystemService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://129.108.4.237:8080/axis/services/ThreatDetectionService", "ThreatDetectionService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ThreatDetectionService".equals(portName)) {
            setThreatDetectionServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
