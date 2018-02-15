/**
 * TaxeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.web.service;

public class TaxeServiceLocator extends org.apache.axis.client.Service implements com.web.service.TaxeService {

    public TaxeServiceLocator() {
    }


    public TaxeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TaxeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Taxe
    private java.lang.String Taxe_address = "http://localhost:8081/ServiceWebSOAP/services/Taxe";

    public java.lang.String getTaxeAddress() {
        return Taxe_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TaxeWSDDServiceName = "Taxe";

    public java.lang.String getTaxeWSDDServiceName() {
        return TaxeWSDDServiceName;
    }

    public void setTaxeWSDDServiceName(java.lang.String name) {
        TaxeWSDDServiceName = name;
    }

    public com.web.service.Taxe getTaxe() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Taxe_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTaxe(endpoint);
    }

    public com.web.service.Taxe getTaxe(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.web.service.TaxeSoapBindingStub _stub = new com.web.service.TaxeSoapBindingStub(portAddress, this);
            _stub.setPortName(getTaxeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTaxeEndpointAddress(java.lang.String address) {
        Taxe_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.web.service.Taxe.class.isAssignableFrom(serviceEndpointInterface)) {
                com.web.service.TaxeSoapBindingStub _stub = new com.web.service.TaxeSoapBindingStub(new java.net.URL(Taxe_address), this);
                _stub.setPortName(getTaxeWSDDServiceName());
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
        if ("Taxe".equals(inputPortName)) {
            return getTaxe();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.web.com", "TaxeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.web.com", "Taxe"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Taxe".equals(portName)) {
            setTaxeEndpointAddress(address);
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
