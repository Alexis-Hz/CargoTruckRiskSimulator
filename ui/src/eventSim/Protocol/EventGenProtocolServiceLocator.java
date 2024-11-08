/**
 * EventGenProtocolServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eventSim.Protocol;

public class EventGenProtocolServiceLocator extends org.apache.axis.client.Service implements eventSim.Protocol.EventGenProtocolService {

    public EventGenProtocolServiceLocator() {
    }


    public EventGenProtocolServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EventGenProtocolServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EventGenProtocol
    private java.lang.String EventGenProtocol_address = "http://iw.cs.university.edu:8080/axis/services/EventGenProtocol";

    public java.lang.String getEventGenProtocolAddress() {
        return EventGenProtocol_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EventGenProtocolWSDDServiceName = "EventGenProtocol";

    public java.lang.String getEventGenProtocolWSDDServiceName() {
        return EventGenProtocolWSDDServiceName;
    }

    public void setEventGenProtocolWSDDServiceName(java.lang.String name) {
        EventGenProtocolWSDDServiceName = name;
    }

    public eventSim.Protocol.EventGenProtocol getEventGenProtocol() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EventGenProtocol_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEventGenProtocol(endpoint);
    }

    public eventSim.Protocol.EventGenProtocol getEventGenProtocol(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            eventSim.Protocol.EventGenProtocolSoapBindingStub _stub = new eventSim.Protocol.EventGenProtocolSoapBindingStub(portAddress, this);
            _stub.setPortName(getEventGenProtocolWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEventGenProtocolEndpointAddress(java.lang.String address) {
        EventGenProtocol_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (eventSim.Protocol.EventGenProtocol.class.isAssignableFrom(serviceEndpointInterface)) {
                eventSim.Protocol.EventGenProtocolSoapBindingStub _stub = new eventSim.Protocol.EventGenProtocolSoapBindingStub(new java.net.URL(EventGenProtocol_address), this);
                _stub.setPortName(getEventGenProtocolWSDDServiceName());
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
        if ("EventGenProtocol".equals(inputPortName)) {
            return getEventGenProtocol();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://iw.cs.university.edu:8080/axis/services/EventGenProtocol", "EventGenProtocolService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://iw.cs.university.edu:8080/axis/services/EventGenProtocol", "EventGenProtocol"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EventGenProtocol".equals(portName)) {
            setEventGenProtocolEndpointAddress(address);
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
