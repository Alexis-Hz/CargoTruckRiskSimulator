package model.tds.threatDetectionService;

public class ThreatDetectionSystemProxy implements model.tds.threatDetectionService.ThreatDetectionSystem {
  private String _endpoint = null;
  private model.tds.threatDetectionService.ThreatDetectionSystem threatDetectionSystem = null;
  
  public ThreatDetectionSystemProxy() {
    _initThreatDetectionSystemProxy();
  }
  
  public ThreatDetectionSystemProxy(String endpoint) {
    _endpoint = endpoint;
    _initThreatDetectionSystemProxy();
  }
  
  private void _initThreatDetectionSystemProxy() {
    try {
      threatDetectionSystem = (new model.tds.threatDetectionService.ThreatDetectionSystemServiceLocator()).getThreatDetectionService();
      if (threatDetectionSystem != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)threatDetectionSystem)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)threatDetectionSystem)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (threatDetectionSystem != null)
      ((javax.xml.rpc.Stub)threatDetectionSystem)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public model.tds.threatDetectionService.ThreatDetectionSystem getThreatDetectionSystem() {
    if (threatDetectionSystem == null)
      _initThreatDetectionSystemProxy();
    return threatDetectionSystem;
  }
  
  public java.lang.String getRecomenation(java.lang.String vehicleID, java.lang.String eRoute, java.lang.String aRoute, java.lang.String eArrivalTime, java.lang.String aArrivalTime) throws java.rmi.RemoteException{
    if (threatDetectionSystem == null)
      _initThreatDetectionSystemProxy();
    return threatDetectionSystem.getRecomenation(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
  }
  
  public java.lang.String getPMLExplanation(java.lang.String vehicleID, java.lang.String eRoute, java.lang.String aRoute, java.lang.String eArrivalTime, java.lang.String aArrivalTime) throws java.rmi.RemoteException{
    if (threatDetectionSystem == null)
      _initThreatDetectionSystemProxy();
    return threatDetectionSystem.getPMLExplanation(vehicleID, eRoute, aRoute, eArrivalTime, aArrivalTime);
  }
  
  
}