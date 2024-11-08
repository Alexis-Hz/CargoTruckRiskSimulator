package edu.university.cs.iw.axis.CDBService5_jws;

public class CDBService5Proxy implements edu.university.cs.iw.axis.CDBService5_jws.CDBService5 {
  private String _endpoint = null;
  private edu.university.cs.iw.axis.CDBService5_jws.CDBService5 cDBService5 = null;
  
  public CDBService5Proxy() {
    _initCDBService5Proxy();
  }
  
  public CDBService5Proxy(String endpoint) {
    _endpoint = endpoint;
    _initCDBService5Proxy();
  }
  
  private void _initCDBService5Proxy() {
    try {
      cDBService5 = (new edu.university.cs.iw.axis.CDBService5_jws.CDBService5ServiceLocator()).getCDBService5();
      if (cDBService5 != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cDBService5)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cDBService5)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cDBService5 != null)
      ((javax.xml.rpc.Stub)cDBService5)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public edu.university.cs.iw.axis.CDBService5_jws.CDBService5 getCDBService5() {
    if (cDBService5 == null)
      _initCDBService5Proxy();
    return cDBService5;
  }
  
  public java.lang.String tripInfo(int trip_ID) throws java.rmi.RemoteException{
    if (cDBService5 == null)
      _initCDBService5Proxy();
    return cDBService5.tripInfo(trip_ID);
  }
  
  
}