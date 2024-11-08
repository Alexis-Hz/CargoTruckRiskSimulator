/**
 * CDBService5ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.university.cs.iw.axis.CDBService5_jws;

public class CDBService5ServiceLocator extends org.apache.axis.client.Service implements edu.university.cs.iw.axis.CDBService5_jws.CDBService5Service {

    public CDBService5ServiceLocator() {
    }


    public CDBService5ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CDBService5ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CDBService5
    private java.lang.String CDBService5_address = "http://iw.cs.university.edu:8080/axis/CDBService5.jws";

    public java.lang.String getCDBService5Address() {
        return CDBService5_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CDBService5WSDDServiceName = "CDBService5";

    public java.lang.String getCDBService5WSDDServiceName() {
        return CDBService5WSDDServiceName;
    }

    public void setCDBService5WSDDServiceName(java.lang.String name) {
        CDBService5WSDDServiceName = name;
    }

    public edu.university.cs.iw.axis.CDBService5_jws.CDBService5 getCDBService5() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CDBService5_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCDBService5(endpoint);
    }

    public edu.university.cs.iw.axis.CDBService5_jws.CDBService5 getCDBService5(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.university.cs.iw.axis.CDBService5_jws.CDBService5SoapBindingStub _stub = new edu.university.cs.iw.axis.CDBService5_jws.CDBService5SoapBindingStub(portAddress, this);
            _stub.setPortName(getCDBService5WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCDBService5EndpointAddress(java.lang.String address) {
        CDBService5_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.university.cs.iw.axis.CDBService5_jws.CDBService5.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.university.cs.iw.axis.CDBService5_jws.CDBService5SoapBindingStub _stub = new edu.university.cs.iw.axis.CDBService5_jws.CDBService5SoapBindingStub(new java.net.URL(CDBService5_address), this);
                _stub.setPortName(getCDBService5WSDDServiceName());
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
        if ("CDBService5".equals(inputPortName)) {
            return getCDBService5();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://iw.cs.university.edu:8080/axis/CDBService5.jws", "CDBService5Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://iw.cs.university.edu:8080/axis/CDBService5.jws", "CDBService5"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CDBService5".equals(portName)) {
            setCDBService5EndpointAddress(address);
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
