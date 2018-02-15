package com.web.service;

public class TaxeProxy implements com.web.service.Taxe {
  private String _endpoint = null;
  private com.web.service.Taxe taxe = null;
  
  public TaxeProxy() {
    _initTaxeProxy();
  }
  
  public TaxeProxy(String endpoint) {
    _endpoint = endpoint;
    _initTaxeProxy();
  }
  
  private void _initTaxeProxy() {
    try {
      taxe = (new com.web.service.TaxeServiceLocator()).getTaxe();
      if (taxe != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)taxe)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)taxe)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (taxe != null)
      ((javax.xml.rpc.Stub)taxe)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.web.service.Taxe getTaxe() {
    if (taxe == null)
      _initTaxeProxy();
    return taxe;
  }
  
  public double getTVA() throws java.rmi.RemoteException{
    if (taxe == null)
      _initTaxeProxy();
    return taxe.getTVA();
  }
  
  
}