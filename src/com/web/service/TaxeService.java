/**
 * TaxeService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.web.service;

public interface TaxeService extends javax.xml.rpc.Service {
    public java.lang.String getTaxeAddress();

    public com.web.service.Taxe getTaxe() throws javax.xml.rpc.ServiceException;

    public com.web.service.Taxe getTaxe(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
