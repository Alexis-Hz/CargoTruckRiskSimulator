package org.tempuri;

public class ServiceSoapProxy implements org.tempuri.ServiceSoap {
  private String _endpoint = null;
  private org.tempuri.ServiceSoap serviceSoap = null;
  boolean mock = true;
  
  public ServiceSoapProxy() {
	  //System.out.println("About to init service proxy");
	  if(!mock)
		  _initServiceSoapProxy();
  }
  
  public ServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceSoapProxy();
  }
  
  private void _initServiceSoapProxy() {
    try {
      serviceSoap = (new org.tempuri.ServiceLocator()).getServiceSoap();
      if (serviceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceSoap != null)
      ((javax.xml.rpc.Stub)serviceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.ServiceSoap getServiceSoap() {
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap;
  }
  
  public java.lang.String helloWorld() throws java.rmi.RemoteException{
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap.helloWorld();
  }
  
  public java.lang.String getShortestRoute(java.lang.String sx, java.lang.String sy, java.lang.String ex, java.lang.String ey) throws java.rmi.RemoteException{
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap.getShortestRoute(sx, sy, ex, ey);
  }
  
  public java.lang.String getAlternateShortestRoute(java.lang.String sx, java.lang.String sy, java.lang.String ex, java.lang.String ey, java.lang.String bx, java.lang.String by) throws java.rmi.RemoteException{
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap.getAlternateShortestRoute(sx, sy, ex, ey, bx, by);
  }
  
  
}